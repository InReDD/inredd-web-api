package api.webservices.inredd.service;

import java.util.*;
import java.time.*;
import java.util.stream.*;
import java.time.format.*;
import java.text.Normalizer;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
    @Autowired private AddressRepository addressRepository;

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
        String imageBase64 = u.getPublicPicture();

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

    public ProfileDTO getProfile(Long userId) {
        User u = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toProfileDTO(u);
    }

    @Transactional
    public ProfileDTO updateProfile(Long userId, ProfileUpdateDTO upd) {
        User u = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    
        // Campos básicos
        if (upd.getFirstName() != null) u.setFirstName(upd.getFirstName());
        if (upd.getLastName()  != null) u.setLastName(upd.getLastName());
        if (upd.getEmail()     != null) u.setEmail(upd.getEmail());
        if (upd.getContact()   != null) u.setContact(upd.getContact());
    
        // Public picture
        if (upd.getPublicPicture() != null) {
            // salva o texto diretamente (data URL)
            u.setPublicPicture(upd.getPublicPicture());
        }
    
        // Academic
        if (upd.getAcademicTitle() != null ||
            upd.getInstitution()    != null ||
            upd.getLattesId()       != null ||
            upd.getAbstractText()   != null) {
    
            if (u.getAcademic() == null) {
                u.setAcademic(new Academic(u));
            }
            Academic ac = u.getAcademic();
            if (upd.getAcademicTitle() != null) ac.setTitle(upd.getAcademicTitle());
            if (upd.getInstitution()    != null) ac.setInstitution(upd.getInstitution());
            if (upd.getLattesId()       != null) ac.setLattesId(upd.getLattesId());
            if (upd.getAbstractText()   != null) ac.setAbstractText(upd.getAbstractText());
        }
    
        // Endereços
        if (upd.getAddresses() != null) {
            u.getAddresses().clear();
            for (AddressDTO aDto : upd.getAddresses()) {
                Address a;
                if (aDto.getIdAddress() != null) {
                    a = addressRepository.findById(aDto.getIdAddress())
                          .orElseThrow(() -> new EntityNotFoundException(
                              "Endereço não encontrado com ID: " + aDto.getIdAddress()));
                } else {
                    a = new Address(u);
                }
                a.setAddress(aDto.getAddress());
                a.setCity(aDto.getCity());
                a.setState(aDto.getState());
                a.setCountry(aDto.getCountry());
                u.getAddresses().add(a);
            }
        }
    
        // Salva e retorna ProfileDTO completo (incluindo picture, abstract, lattesId e grupos)
        userRepository.save(u);
        return toProfileDTO(u);
    }

    private ProfileDTO toProfileDTO(User u) {
        ProfileDTO d = new ProfileDTO();
        d.setId(u.getIdUser());
        d.setFirstName(u.getFirstName());
        d.setLastName(u.getLastName());
        d.setEmail(u.getEmail());
        d.setContact(u.getContact());
    
        // Academic
        if (u.getAcademic() != null) {
            d.setAcademicTitle(u.getAcademic().getTitle());
            d.setInstitution(u.getAcademic().getInstitution());
            d.setLattesId(u.getAcademic().getLattesId());
            d.setAbstractText(u.getAcademic().getAbstractText());
        }
    
        // Public picture em Base64
        if (u.getPublicPicture() != null) {
            d.setPublicPicture(u.getPublicPicture());
        }        
    
        // Endereços
        d.setAddresses(
            u.getAddresses().stream()
             .map(AddressDTO::new)
             .collect(Collectors.toList())
        );
    
        // grupos — apenas os nomes
        if (!u.getGroups().isEmpty()) {
            d.setGroupNames(
                u.getGroups().stream()
                 .map(Group::getName)
                 .collect(Collectors.toList())
            );
        }
    
        d.setUserHasAccessToD2L(u.getUserHasAccessToD2L());
        d.setUserHasAccessToOpenData(u.getUserHasAccessToOpenData());
        return d;
    }
	
    public Page<UserDTO> findUsersNoGroup(int page, int limit, String name, String sort, String direction) {
        // Corrige o campo de sort (id → idUser)
        String sortField = "id".equals(sort) ? "idUser" : sort;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(
                direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortField
        ));

        Specification<User> specNoGroup = (root, query, cb) -> cb.isEmpty(root.get("groups"));
        Page<User> users = userRepository.findAll(specNoGroup, pageable);

        // Filtro de nome insensível a acento/case, multi-palavra
        List<User> filtered = users.getContent();
        if (name != null && !name.trim().isEmpty()) {
            String norm = normalize(name);
            String[] tokens = norm.split("\\s+");
            filtered = filtered.stream().filter(u -> {
                String fn = normalize(u.getFirstName());
                String ln = normalize(u.getLastName());
                String full = (fn + " " + ln).trim();
                return Arrays.stream(tokens).allMatch(token ->
                    fn.contains(token) || ln.contains(token) || full.contains(token)
                );
            }).collect(Collectors.toList());
        }

        // Paginação manual após filtrar em memória
        int start = (int) Math.min(pageable.getOffset(), filtered.size());
        int end   = (int) Math.min(start + pageable.getPageSize(), filtered.size());
        List<UserDTO> dtoList = filtered.subList(start, end).stream()
            .map(u -> {
                UserDTO dto = new UserDTO(u);
                dto.setPermissions(Collections.emptyList());
                dto.setGroups(Collections.emptyList());
                return dto;
            }).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, filtered.size());
    }

	public static String normalize(String input) {
		if (input == null) return "";
		return Normalizer.normalize(input, Normalizer.Form.NFD)
				.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
				.toLowerCase();
	}
}
