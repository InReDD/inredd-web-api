package api.webservices.inredd.service;

import java.time.Instant;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.webservices.inredd.domain.model.*;
import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.specification.UserSpecification;
import api.webservices.inredd.repository.MemberRepository;
import api.webservices.inredd.repository.InviteRequestRepository;
import api.webservices.inredd.repository.GroupRepository;
import api.webservices.inredd.repository.UserRepository;


@Service
public class MemberService {

    @Autowired private MemberRepository memberRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private InviteRequestRepository inviteRepo;
    @Autowired private EmailService emailService;
    

    public List<MemberDTO> listMembersByGroup(Long groupId) {
        return memberRepository.findMembersByGroupId(groupId);
    }

    public Page<MemberViewDTO> listMembersByGroups(
            List<Long> groupIds,
            String name,
            Pageable pageable
    ) {
        Specification<User> spec = Specification
            .where(UserSpecification.inGroups(groupIds))
            .and(UserSpecification.hasName(name));

        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(MemberViewDTO::new);
    }

    /**
     * Retorna todos os MemberDTO para os grupos informados,
     * incluindo dados de Academic.
     */
    public List<MemberDTO> findMembersByGroupIds(List<Long> groupIds) {
        return memberRepository.findMembersByGroupIds(groupIds);
    }

    /**
     * Mesma busca, mas já retorna página conforme Pageable.
     */
    public Page<MemberDTO> listByGroupIds(List<Long> groupIds, Pageable pageable) {
        List<MemberDTO> all = findMembersByGroupIds(groupIds);

        // Remove duplicados
        List<MemberDTO> unique = all.stream()
            .distinct()
            .collect(Collectors.toList());

        // Paginação manual
        int start = (int) Math.min(pageable.getOffset(), unique.size());
        int end   = (int) Math.min(start + pageable.getPageSize(), unique.size());
        List<MemberDTO> slice = unique.subList(start, end);

        return new PageImpl<>(slice, pageable, unique.size());
    }

    public MemberDetailDTO getMemberDetail(Long userId) {
        User u = userRepository.findByIdUser(userId)
                 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return new MemberDetailDTO(u);
    }

    @Transactional
    public void updateMemberAccessAndGroup(Long userId, MemberAccessUpdateDTO dto) {
        User u = userRepository.findByIdUser(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Atualiza flags e datas
        u.setUserHasAccessToD2L(dto.getUserHasAccessToD2L());
        u.setAccessToD2LSince(
            dto.getUserHasAccessToD2L() ? Instant.now() : null
        );

        u.setUserHasAccessToOpenData(dto.getUserHasAccessToOpenData());
        u.setAccessToOpenDataSince(
            dto.getUserHasAccessToOpenData() ? Instant.now() : null
        );

        // Atualiza grupo (ManyToMany)
        Group newGroup = groupRepository.findById(dto.getGroupId())
            .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado: " + dto.getGroupId()));

        // Se for diferente, troca a associação
        if (u.getGroups().isEmpty() ||
            !u.getGroups().get(0).getIdGroups().equals(newGroup.getIdGroups())) {
            u.getGroups().clear();
            u.getGroups().add(newGroup);
        }

        userRepository.save(u);
    }

    @Transactional
    public void removeMemberFromAllGroups(Long userId) {
        User u = userRepository.findByIdUser(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + userId));
        u.getGroups().clear();
        userRepository.save(u);
    }

    @Transactional
    public void inviteMember(InviteMemberDTO dto) {
        Instant now = Instant.now();
        String   email = dto.getEmail().toLowerCase();

        // 1) Se já existe usuário
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            User u = opt.get();
            // — já membro?
            if (!u.getGroups().isEmpty()) {
                throw new IllegalStateException("Usuário já pertence a um grupo");
            }
            // — vincula direto
            Group g = groupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
            g.getUsers().add(u);
            groupRepository.save(g);

            // — notifica
            emailService.sendEmail(
                u.getEmail(),
                "Você foi convidado para o grupo " + g.getName(),
                "Olá " + u.getFirstName() + ",\n\n"
              + "Você agora faz parte do grupo “" + g.getName() + "” em InReDD.\n\nAbraços."
            );
        }
        else {
            // 2) usuário não existe: cria convite
            String token   = UUID.randomUUID().toString();
            Instant expires = now.plus(14, ChronoUnit.DAYS);

            Group g = groupRepository.findById(dto.getGroupId())
            .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));

            InviteRequest ir = new InviteRequest();
            ir.setEmail(email);
            ir.setGroupId(dto.getGroupId());
            ir.setInviteToken(token);
            ir.setCreatedAt(now);
            ir.setExpiresAt(expires);
            inviteRepo.save(ir);

            // — envia link de cadastro com token
            emailService.sendEmail(
                email,
                "Você foi convidado para o InReDD",
                "Olá!\n\n"
              + "Para se cadastrar e entrar no grupo " + g.getName() + ", clique aqui:\n"
              + "https://inredd.com.br/create-account?inviteToken=" + token + "\n\n"
              + "Este link expira em 14 dias."
            );
        }
    }
}
