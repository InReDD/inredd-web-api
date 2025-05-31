package api.webservices.inredd.service;

import java.util.*;
import java.time.*;
import java.util.stream.*;
import java.time.format.*;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import api.webservices.inredd.domain.model.*;
import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.repository.*;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	@Autowired PermissionRepository permissionRepository;
	@Autowired GroupRepository groupRepository;
    @Autowired private InviteRequestRepository inviteRepo;
	@Autowired private AcademicRepository academicRepository;
    @Autowired private AccessRequestRepository accessReqRepo;

    @Autowired
    private AddressRepository addressRepository;
    @Transactional
	public User save(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setPermissions(addCommonUserPermissions());
		return userRepository.save(user);
	}

	@Transactional
    public User saveWithAcademic(CreateUserDTO dto) {
        // 1 Monta o usuário
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setActive(true);
        user.setPermissions(addCommonUserPermissions());
        user.setSignedInAt(Instant.now());

        // 2 Monta o Academic
        Academic academic = new Academic(user);
        academic.setInstitution(dto.getInstitution());
        // os outros campos do Academic: title, lattesId, abstractText e address ficam nulos
		user.setAcademic(academic);

        // 3) Salva o usuário
        User saved = userRepository.save(user);

        // 4) Se veio um requestToken válido, consome a AccessRequest
        if (dto.getRequestToken() != null && !dto.getRequestToken().isBlank()) {
            AccessRequest ar = accessReqRepo
                .findByRequestToken(dto.getRequestToken())
                .orElseThrow(() -> new EntityNotFoundException("Token inválido"));
            if (ar.getExpiresAt().isBefore(Instant.now())) {
                throw new IllegalStateException("Token expirado");
            }
            ar.setUser(saved);
            // ar.setConsumedAt(Instant.now());
            accessReqRepo.save(ar);
        }
        
        // 4) Se veio um inviteToken válido, consome a AccessRequest
        if (dto.getInviteToken()!=null && !dto.getInviteToken().isBlank()) {
            InviteRequest ir = inviteRepo.findByInviteToken(dto.getInviteToken())
              .orElseThrow(() -> new EntityNotFoundException("Token de convite inválido"));
            if (ir.getExpiresAt().isBefore(Instant.now())) {
              throw new IllegalStateException("Token de convite expirado");
            }
            // vincula ao grupo
            Group g = groupRepository.findById(ir.getGroupId())
              .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
            g.getUsers().add(saved);
            groupRepository.save(g);
            // marca convite como consumido
            ir.setConsumedAt(Instant.now());
            inviteRepo.save(ir);
          }

        return saved;
    }
	
	public List<Permission> addCommonUserPermissions(){
		List<Permission> permissions = new ArrayList<>();
		permissions.add(permissionRepository.findById(18L).get());
		return permissions;
	}

	@Transactional
    public void assignGroupToUser(Long userId, Long groupId) {
        User  user  = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + userId));
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado: " + groupId));

        // 1) Associa o grupo ao usuário
        user.getGroups().add(group);

        // 2) Calcula quais permissões esse grupo deve dar
        List<Permission> perms = getPermissionsForGroup(groupId);

        // 3) Redefine a lista de permissões do usuário
        user.setPermissions(perms);

        // 4) Salva tudo
        userRepository.save(user);
    }

	/**
     * Se for grupo 1 => retorna *todas* (1 a 6).
     * Senão => retorna apenas as básicas (1,3,4,5,6).
     */
    private List<Permission> getPermissionsForGroup(Long groupId) {
        if (groupId.equals(1L)) {
            List<Permission> permissions = new ArrayList<>();
            return permissionRepository.findAll();
        } else {
            return addCommonUserPermissions();
        }
    }
	
	@Transactional
    public User update(Long id, UserUpdateDTO dto) {
        Optional<User> existingUserOptional = userRepository.findByIdUser(id);

        if (existingUserOptional.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
        }

        User existingUser = existingUserOptional.get();

        // user
        if (dto.getFirstName() != null) existingUser.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) existingUser.setLastName(dto.getLastName());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getContact() != null) existingUser.setContact(dto.getContact());
        if (dto.getActive() != null) existingUser.setActive(dto.getActive());

        // academic
        if (dto.getAcademic() != null) {
            Academic academic = existingUser.getAcademic();

            if (academic == null) {
                academic = new Academic(existingUser);
                existingUser.setAcademic(academic);
            }

            AcademicUpdateDTO academicDTO = dto.getAcademic();
            if (academicDTO.getTitle() != null) academic.setTitle(academicDTO.getTitle());
            if (academicDTO.getInstitution() != null) academic.setInstitution(academicDTO.getInstitution());
            if (academicDTO.getLattesId() != null) academic.setLattesId(academicDTO.getLattesId());
            if (academicDTO.getAbstractText() != null) academic.setAbstractText(academicDTO.getAbstractText());

            // address
            if (academicDTO.getAddressId() != null) {
                Address address = addressRepository.findById(academicDTO.getAddressId())
                        .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + academicDTO.getAddressId()));
                academic.setAddress(address);
            }
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void updateActiveProperty(Long id, Boolean active) {
        Optional<User> userOptional = userRepository.findByIdUser(id);

        if (userOptional.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        User user = userOptional.get();
        user.setActive(active);

        userRepository.save(user);
    }
	
	public User findUserById(Long id) {
		User userSaved = userRepository.findById(id)
				.orElseThrow(
				(() -> new EmptyResultDataAccessException(1)));
		return userSaved;
	}

    public MeDTO getCurrentUserInfo(Long userId) {
        User u = userRepository.findById(userId)
                 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // 1) displayName (nome completo)
        String displayName = u.getFirstName() + " " + u.getLastName();

        // 2) imageBase64
        String imageBase64 = null;
        if (u.getPublicPicture() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(u.getPublicPicture());
        }

        // 3) formata datas de acesso às soluções (ou null se não houver)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String accessToD2LSinceStr = null;
        if (u.getAccessToD2LSince() != null) {
            accessToD2LSinceStr = fmt.format(
                u.getAccessToD2LSince().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }
        String accessToOpenDataSinceStr = null;
        if (u.getAccessToOpenDataSince() != null) {
            accessToOpenDataSinceStr = fmt.format(
                u.getAccessToOpenDataSince().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }

        // 4) flags de acesso (simples boolean)
        boolean userHasAccessToD2L      = Boolean.TRUE.equals(u.getUserHasAccessToD2L());
        boolean userHasAccessToOpenData = Boolean.TRUE.equals(u.getUserHasAccessToOpenData());

        // 5) obtém o conjunto de descrições das permissões,
        //    ex: ["ROLE_LIST_MEMBER", "ROLE_EDIT_GROUP", ...]
        Set<String> perms = u.getPermissions().stream()
                             .map(Permission::getDescription)
                             .collect(Collectors.toSet());

        // 6) constrói o DTO
        MeDTO dto = new MeDTO(
            displayName,
            u.getFirstName(),
            u.getLastName(),
            imageBase64,
            userHasAccessToD2L,
            accessToD2LSinceStr,
            userHasAccessToOpenData,
            accessToOpenDataSinceStr,
            perms
        );

        return dto;
    }
	
}
