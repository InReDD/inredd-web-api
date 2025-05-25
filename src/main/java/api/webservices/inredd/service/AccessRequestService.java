package api.webservices.inredd.service;

import java.time.Instant;
import java.util.UUID;
import java.util.Optional;
import java.time.temporal.ChronoUnit;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.webservices.inredd.domain.model.*;
import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.repository.*;
import api.webservices.inredd.service.*;
import api.webservices.inredd.specification.*;

@Service
public class AccessRequestService {
    @Autowired private AccessRequestRepository repo;
    @Autowired private UserRepository userRepo;
    @Autowired private JavaMailSender mailSender;
    @Autowired private UserService userService;

    public void createRequest(CreateAccessRequestDTO dto) {
        Instant now    = Instant.now();
        Instant cutoff = now.minus(7, ChronoUnit.DAYS);
    
        // 1) Bloqueia spam
        if (repo.existsByEmailAndConsumedAtIsNullAndCreatedAtAfter(
                dto.getEmail().toLowerCase(), cutoff)) {
            throw new IllegalStateException("Já existe uma solicitação ativa para este e-mail.");
        }
    
        // 2) Busca usuário cadastrado
        Optional<User> optUser = userRepo.findByEmail(dto.getEmail().toLowerCase());
    
        // 3) Monta a entidade
        AccessRequest ar = new AccessRequest();
        ar.setEmail(dto.getEmail().toLowerCase());
        ar.setFirstName(optUser
            .map(User::getFirstName)
            .orElse(dto.getFirstName()));
        ar.setSolution(dto.getSolution());
        ar.setReason(dto.getReason());
        ar.setCreatedAt(now);
        ar.setRequestToken(UUID.randomUUID().toString());
        ar.setExpiresAt(now.plus(7, ChronoUnit.DAYS));
    
        // 4) Persiste e dispara e-mail
        repo.save(ar);
        if (optUser.isPresent()) {
            sendAnalysisEmail(ar);
        } else {
            sendSignupEmail(ar, ar.getFirstName());
        }
    }

    private void sendAnalysisEmail(AccessRequest ar) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("no-reply@demomailtrap.co");
        msg.setTo(ar.getEmail());
        msg.setSubject("Sua solicitação está em análise");
        msg.setText(
          "Olá " + ar.getFirstName() + ",\n\n" +
          "Recebemos sua solicitação de acesso à solução “" +
          ar.getSolution() + "” e ela será avaliada em breve.\n\n" +
          "Obrigado."
        );
        mailSender.send(msg);
    }

    private void sendSignupEmail(AccessRequest ar, String firstName) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("no-reply@demomailtrap.co");
        msg.setTo(ar.getEmail());
        msg.setSubject("Complete seu cadastro");
        msg.setText(
          "Olá " + firstName + ",\n\n" +
          "Para criar sua conta e ter acesso à “" +
          ar.getSolution() + "”, clique no link abaixo:\n\n" +
          "https://inredd.com.br/criar-conta?requestToken=" +
          ar.getRequestToken() + "\n\n" +
          "Este link expira em 7 dias.\n\n" +
          "Obrigado."
        );
        mailSender.send(msg);
    }

    public Page<AccessRequestDTO> listRequests(String search, Pageable pageable) {
        Specification<AccessRequest> spec = AccessRequestSpecification.hasTerm(search);

        return repo.findAll(spec, pageable)
            .map(ar -> {
                AccessRequestDTO dto = new AccessRequestDTO(ar);

                // busca o usuário pelo e-mail (caso exista)
                userRepo.findByEmail(ar.getEmail()).ifPresent(u -> {
                    dto.setUserId(u.getIdUser());
                    // pode vir nulo se ainda não tiver academic
                    dto.setInstitution(
                        u.getAcademic() != null
                            ? u.getAcademic().getInstitution()
                            : null
                    );
                });

                return dto;
            });
    }

    public ValidateAccessRequestDTO validateToken(String token) {
        AccessRequest ar = repo.findByRequestToken(token)
          .orElseThrow(() -> new EntityNotFoundException("Token inválido"));
        if (ar.getExpiresAt().isBefore(Instant.now())) {
          throw new IllegalStateException("Token expirado");
        }
        long expiresIn = ar.getExpiresAt().getEpochSecond() - Instant.now().getEpochSecond();
        return new ValidateAccessRequestDTO(
          ar.getEmail(), ar.getFirstName(), ar.getSolution(), ar.getReason(), expiresIn
        );
    }
}