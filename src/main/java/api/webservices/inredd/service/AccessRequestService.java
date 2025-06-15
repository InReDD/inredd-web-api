package api.webservices.inredd.service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;
import javax.persistence.EntityNotFoundException;


import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import api.webservices.inredd.domain.model.*;
import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.repository.*;
import api.webservices.inredd.service.*;
import api.webservices.inredd.specification.*;

@Service
public class AccessRequestService {
    @Autowired private AccessRequestRepository repo;
    @Autowired private InviteRequestRepository inviteRequestRepository;
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

    public ValidateAccessRequestDTO validateRequestToken(String token) {
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

    public ValidateAccessRequestDTO validateInviteToken(String token) {
        InviteRequest invite = inviteRequestRepository.findByInviteToken(token)
            .orElseThrow(() -> new EntityNotFoundException("InviteToken inválido"));
        if (invite.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Invite expirado");
        }
        long expiresIn = invite.getExpiresAt().getEpochSecond() - Instant.now().getEpochSecond();
        return new ValidateAccessRequestDTO(
            invite.getEmail(), // Email do convidado
            null,              // firstName (não cadastrado ainda)
            null,              // solution (não se aplica)
            null,              // reason (não se aplica)
            expiresIn
        );
    }

    // deprecated
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

    public AccessRequestDetailDTO getAccessRequestDetailById(Long id) {
        AccessRequest ar = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("AccessRequest não encontrada: " + id));
        
        AccessRequestDetailDTO dto = new AccessRequestDetailDTO();
        dto.setId(ar.getId());
        dto.setEmail(ar.getEmail());
        dto.setFirstName(ar.getFirstName());
        dto.setSolution(ar.getSolution());
        dto.setReason(ar.getReason());
        dto.setCreatedAt(ar.getCreatedAt());
        dto.setExpiresAt(ar.getExpiresAt());
        dto.setCompleted(ar.getConsumedAt() != null);
    
        // Estado da solicitação
        if (ar.getConsumedAt() == null) {
            dto.setState("pending");
        } else if (ar.getRejectionReason() != null) {
            dto.setState("rejected");
            dto.setModerationReason(ar.getRejectionReason());
        } else {
            dto.setState("accepted");
        }
        // Bloco que seta moderador e data, se houver
        if (ar.getModeratedBy() != null) {
            dto.setModeratorName(
                ar.getModeratedBy().getFirstName() + " " + ar.getModeratedBy().getLastName()
            );
            dto.setModerationDate(ar.getConsumedAt());
        }
    
        // Dados do user (incluindo institution do academic, se houver)
        userRepo.findByEmail(ar.getEmail()).ifPresent(u -> {
            dto.setInstitution(
                u.getAcademic() != null ? u.getAcademic().getInstitution() : null
            );
            dto.setFirstName(u.getFirstName());
            // Se quiser trazer mais campos do User, adicione no DTO
        });
    
        // Histórico de solicitações anteriores do usuário
        List<PreviousRequestDTO> history = repo.findAll(
            (root, query, cb) -> cb.equal(root.get("email"), ar.getEmail())
        ).stream()
        .filter(r -> !Objects.equals(r.getId(), id))
        .filter(r -> r.getCreatedAt().isBefore(ar.getCreatedAt())) // Só anteriores!
        .map(r -> new PreviousRequestDTO(
            r.getId(),
            r.getCreatedAt(),
            r.getSolution(),
            r.getConsumedAt() == null ? "pending" : (r.getRejectionReason() != null ? "rejected" : "accepted"),
            r.getRejectionReason(),
            r.getModeratedBy() != null ? r.getModeratedBy().getFirstName() + " " + r.getModeratedBy().getLastName() : null,
            r.getConsumedAt()
        ))
        .collect(Collectors.toList());
        dto.setPreviousRequests(history);
    
        return dto;
    }

    @Transactional
    public void approveRequest(Long id) {
        AccessRequest ar = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("AccessRequest não encontrada: " + id));

        if (ar.getConsumedAt() != null || ar.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Requisição já processada ou expirada");
        }

        // pega o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User moderator = userRepo.findByEmail(username)
            .orElseThrow(() -> new EntityNotFoundException("Moderador não encontrado: " + username));
        ar.setModeratedBy(moderator);

        // garante que o usuário já exista
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

        emailService.sendEmail(
            ar.getEmail(),
            "Solicitação de acesso aprovada",
            "Olá " + ar.getFirstName() + ",\n\n" +
            "Sua solicitação de acesso à “" + ar.getSolution() +
            "” foi aprovada.\n\nMotivo: " + reason + "\n\n"
        );
    }

    @Transactional
    public void rejectRequest(Long id, String reason) {
        AccessRequest ar = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("AccessRequest não encontrada: " + id));
    
        if (ar.getConsumedAt() != null || ar.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Requisição já processada ou expirada");
        }
    
        // pega o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User moderator = userRepo.findByEmail(username)
            .orElseThrow(() -> new EntityNotFoundException("Moderador não encontrado: " + username));
        ar.setModeratedBy(moderator);
    
        ar.setRejectionReason(reason);
        ar.setConsumedAt(Instant.now());
        repo.save(ar);
    
        emailService.sendEmail(
            ar.getEmail(),
            "Solicitação de acesso rejeitada",
            "Olá " + ar.getFirstName() + ",\n\n" +
            "Sua solicitação de acesso à “" + ar.getSolution() +
            "” foi rejeitada.\n\nMotivo: " + reason + "\n\n"
        );
    }
}