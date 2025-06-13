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
    @Autowired private EmailService emailService;

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
            emailService.sendEmail(
                ar.getEmail(),
                "Sua solicitação está em análise",
                "Olá " + ar.getFirstName() + ",\n\n" +
                "Recebemos sua solicitação de acesso à solução “" +
                ar.getSolution() + "” e ela será avaliada em breve.\n\n" +
                "Obrigado."
            );
        } else {
            emailService.sendEmail(
                ar.getEmail(),
                "Complete seu cadastro",
                "Olá " + ar.getFirstName() + ",\n\n" +
                "Para criar sua conta e ter acesso à “" +
                ar.getSolution() + "”, clique no link abaixo:\n\n" +
                "https://inredd.com.br/create-account?requestToken=" +
                ar.getRequestToken() + "\n\n" +
                "Este link expira em 7 dias.\n\n" +
                "Obrigado."
            );
        }
    }

    public Page<AccessRequestDTO> listRequests(String search,
                                            Boolean completed,
                                            Pageable pageable) {
        // 1) base specs: só term
        Specification<AccessRequest> spec = AccessRequestSpecification.hasTerm(search);

        // 2) filtra por completed, se veio param
        if (completed != null) {
            if (completed) {
                spec = spec.and((root, query, cb) ->
                    cb.isNotNull(root.get("consumedAt"))
                );
            } else {
                spec = spec.and((root, query, cb) ->
                    cb.isNull(root.get("consumedAt"))
                );
            }
        }

        // 3) executa e mapeia DTO
        return repo.findAll(spec, pageable)
        .map(ar -> {
            AccessRequestDTO dto = new AccessRequestDTO(ar);
            userRepo.findByEmail(ar.getEmail()).ifPresent(u -> {
                dto.setUserId(u.getIdUser());
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

    public AccessRequestDTO getRequestById(Long id) {
        AccessRequest ar = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("AccessRequest não encontrada: " + id));
    
        AccessRequestDTO dto = new AccessRequestDTO(ar);
    
        // faz o lookup do usuário a partir do e-mail da AccessRequest
        userRepo.findByEmail(ar.getEmail())
            .ifPresent(u -> {
                dto.setUserId(u.getIdUser());
                dto.setInstitution(
                    u.getAcademic() != null
                        ? u.getAcademic().getInstitution()
                        : null
                );
            });
    
        return dto;
    }

    @Transactional
    public void approveRequest(Long id) {
        AccessRequest ar = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("AccessRequest não encontrada: " + id));

        // só aprova se ainda não consumida e não expirada
        if (ar.getConsumedAt() != null || ar.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Requisição já processada ou expirada");
        }

        // garante que o usuário já exista (opção: poderia usar findByEmail)
        User u = userRepo.findByEmail(ar.getEmail())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + ar.getEmail()));

        // atualiza acesso conforme solução
        if ("D2L".equalsIgnoreCase(ar.getSolution())) {
            u.setUserHasAccessToD2L(true);
            u.setAccessToD2LSince(Instant.now());
        } else if ("OpenData".equalsIgnoreCase(ar.getSolution())) {
            u.setUserHasAccessToOpenData(true);
            u.setAccessToOpenDataSince(Instant.now());
        }

        userRepo.save(u);

        ar.setConsumedAt(Instant.now());
        repo.save(ar);
    }

    @Transactional
    public void rejectRequest(Long id, String reason) {
        AccessRequest ar = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("AccessRequest não encontrada: " + id));

        if (ar.getConsumedAt() != null || ar.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Requisição já processada ou expirada");
        }

        ar.setRejectionReason(reason);
        ar.setConsumedAt(Instant.now());
        repo.save(ar);

        // envia o e-mail de recusa
        emailService.sendEmail(
            ar.getEmail(),
            "Solicitação de acesso rejeitada",
            "Olá " + ar.getFirstName() + ",\n\n" +
            "Sua solicitação de acesso à “" + ar.getSolution() +
            "” foi rejeitada.\n\nMotivo: " + reason + "\n\n"
        );
    }
}