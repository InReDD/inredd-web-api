package api.webservices.inredd.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;
import javax.validation.Valid;

import api.webservices.inredd.domain.model.dto.UserDTO;
import api.webservices.inredd.domain.model.dto.UserUpdateDTO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.CreateUserDTO;
import api.webservices.inredd.domain.model.dto.UserResponseDTO;
import api.webservices.inredd.repository.UserRepository;
import api.webservices.inredd.service.UserService;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operações relacionadas aos usuários do sistema")
public class UserResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	/**
     * Lista todos os usuários que NÃO pertencem a nenhum grupo.
    */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_SEARCH_USER') and #oauth2.hasScope('read')")
    public ResponseEntity<List<UserDTO>> listAllNoGroup() {
        Specification<User> specNoGroup = (root, query, cb) -> cb.isEmpty(root.get("groups"));

        List<UserDTO> dtos = userRepository.findAll(specNoGroup).stream()
            .map(u -> {
                UserDTO dto = new UserDTO(u);
                dto.setPermissions(Collections.emptyList());  // remove perms
                dto.setGroups(Collections.emptyList());       // remove grupos
                return dto;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping
	@PreAuthorize("hasAuthority('ROLE_SEARCH_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<UserDTO>> listPageNoGroup(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int limit,
		@RequestParam(value = "name", required = false) String name,
		@RequestParam(value = "sort", defaultValue = "firstName") String sort,
		@RequestParam(value = "direction", defaultValue = "asc") String direction
	) {
		Page<UserDTO> users = userService.findUsersNoGroup(page, limit, name, sort, direction);
		return ResponseEntity.ok(users);
	}
	
	@Operation(summary = "Criar um novo usuário (com optional requestToken ou inviteToken)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@Valid @RequestBody CreateUserDTO dto) {
		var user = userService.saveWithAcademic(dto);
		return new UserResponseDTO(user);
    }

	@Operation(
			summary = "Buscar usuário por ID",
			description = "Busca um usuário específico no sistema utilizando o seu ID. Retorna o usuário encontrado ou uma resposta 404 caso o usuário não seja encontrado."
	)
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_SEARCH_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id){
		Optional<User> userOptional = userRepository.findByIdUser(id);

		if (!userOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		User user = userOptional.get();
		UserDTO userDTO = new UserDTO(user);

		return ResponseEntity.ok(userDTO);
	}

	@Operation(
			summary = "Atualizar informações de um usuário",
			description = "Atualiza as informações de um usuário existente no sistema com base no seu ID."
	)
	@PutMapping(value = "/{id}", consumes = {"application/json", "application/json;charset=UTF-8"})
	@PreAuthorize("hasAuthority('ROLE_REGISTER_USER') and #oauth2.hasScope('write')")
	public ResponseEntity<UserDTO> update(@PathVariable Long id,
										  @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
		User userSaved = userService.update(id, userUpdateDTO);
		return ResponseEntity.ok(new UserDTO(userSaved));
	}

	@Operation(
			summary = "Remover um usuário",
			description = "Remove um usuário do sistema com base no seu ID."
	)
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVE_USER') and #oauth2.hasScope('write')")
	public void remove(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

	@Operation(
			summary = "Atualizar a propriedade 'active' de um usuário",
			description = "Permite atualizar a propriedade 'active' de um usuário, ativando ou desativando sua conta."
	)
	@PutMapping("/{id}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REGISTER_USER') and #oauth2.hasScope('write')")
	public void updateActiveProperty(
			@PathVariable Long id,
			@RequestBody Boolean active){
		userService.updateActiveProperty(id, active);
	}
}