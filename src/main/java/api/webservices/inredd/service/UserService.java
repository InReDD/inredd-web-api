package api.webservices.inredd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.webservices.inredd.domain.model.Academic;
import api.webservices.inredd.domain.model.Permission;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.domain.model.dto.CreateUserDTO;
import api.webservices.inredd.domain.model.dto.MeDTO;
import api.webservices.inredd.repository.PermissionRepository;
import api.webservices.inredd.repository.GroupRepository;
import api.webservices.inredd.repository.UserRepository;
import api.webservices.inredd.repository.AcademicRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	PermissionRepository permissionRepository;

	@Autowired 
	GroupRepository groupRepository;

	@Autowired
    private AcademicRepository academicRepository;
	
	public User save(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setPermissions(addCommonUserPermissions());
		return userRepository.save(user);
	}

	@Transactional
    public User saveWithAcademic(CreateUserDTO dto) {
		// Monta o Academic
        Academic academic = new Academic();
        academic.setInstitution(dto.getInstitution());
        // os outros campos do Academic: title, lattesId, abstractText e address ficam nulos

        // Monta o usuário
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setActive(dto.getActive());
        user.setPermissions(addCommonUserPermissions());
		user.setAcademic(academic);

        return userRepository.save(user);
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
	
	public User update(Long id, User user) {
		User userSaved = findUserById(id);
		BeanUtils.copyProperties(user, userSaved, "id");
		return userRepository.save(userSaved);
	}

	public void updateActiveProperty(Long id, Boolean active) {
		User userSaved = findUserById(id);
		userSaved.setActive(active);
		userRepository.save(userSaved);
	}
	
	public User findUserById(Long id) {
		User userSaved = userRepository.findById(id)
				.orElseThrow(
				(() -> new EmptyResultDataAccessException(1)));
		return userSaved;
	}

    public MeDTO getCurrentUserInfo(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        // Formata "FirstName L." (inicial do último nome + ponto)
        String first = user.getFirstName();
        String last  = user.getLastName();
        String initial = last != null && !last.isBlank()
            ? last.substring(0,1).toUpperCase() + "."
            : "";
        String displayName = first + " " + initial;

        // Converte o byte[] da imagem para Base64 (ou retorna null se não tiver)
        String imgBase64 = null;
        if (user.getPublicPicture() != null) {
            imgBase64 = Base64.getEncoder()
                              .encodeToString(user.getPublicPicture());
        }

        return new MeDTO(displayName, imgBase64);
    }
	
}
