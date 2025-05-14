package api.webservices.inredd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.webservices.inredd.domain.model.Academic;
import api.webservices.inredd.domain.model.Permission;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.CreateUserDTO;
import api.webservices.inredd.repository.PermissionRepository;
import api.webservices.inredd.repository.UserRepository;
import api.webservices.inredd.repository.AcademicRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	PermissionRepository permissionRepository;

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
		permissions.add(permissionRepository.findById(1L).get());
		permissions.add(permissionRepository.findById(3L).get());
		permissions.add(permissionRepository.findById(4L).get());
		permissions.add(permissionRepository.findById(5L).get());
		permissions.add(permissionRepository.findById(6L).get());
		return permissions;
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
	
}
