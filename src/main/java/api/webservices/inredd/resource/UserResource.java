package api.webservices.inredd.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.CreateUserDTO;
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
	
	@Operation(
        summary = "Listar todos os usuários",
        description = "Retorna uma lista de todos os usuários registrados no sistema. Esta operação requer que o usuário tenha a autoridade 'ROLE_SEARCH_USER' e o escopo 'read'."
    )
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_SEARCH_USER') and #oauth2.hasScope('read')")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// @Operation(
    //     summary = "Criar um novo usuário",
    //     description = "Cria um novo usuário no sistema com as informações fornecidas. Esta operação não exige autenticação específica, mas pode ser protegida conforme a lógica de segurança."
    // )
	// @PostMapping
	// @ResponseStatus(HttpStatus.CREATED)
	// //@PreAuthorize("hasAuthority('ROLE_REGISTER_USER') and #oauth2.hasScope('write')")
	// public User create(@Valid @RequestBody User user, 
	// 		HttpServletResponse response) {
	// 	return userService.save(user);
	// }
	
	@Operation(summary = "Criar um novo usuário com instituição acadêmica")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody CreateUserDTO dto) {
        return userService.saveWithAcademic(dto);
    }

	@Operation(
        summary = "Buscar usuário por ID",
        description = "Busca um usuário específico no sistema utilizando o seu ID. Retorna o usuário encontrado ou uma resposta 404 caso o usuário não seja encontrado."
    )
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_SEARCH_USER') and #oauth2.hasScope('read')")
	public ResponseEntity<User> findById(@PathVariable Long id){
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@Operation(
        summary = "Remover um usuário",
        description = "Remove um usuário do sistema com base no seu ID. Esta operação requer que o usuário tenha a autoridade 'ROLE_REMOVE_USER' e o escopo 'write'."
    )
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVE_USER') and #oauth2.hasScope('write')")
	public void remove(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

	@Operation(
        summary = "Atualizar informações de um usuário",
        description = "Atualiza as informações de um usuário existente no sistema com base no seu ID. Essa operação requer autenticação e autorização apropriadas."
    )
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REGISTER_USER') and #oauth2.hasScope('write')")
	public ResponseEntity<User> update(@PathVariable Long id,
			@Valid @RequestBody User user){
		User userSaved = userService.update(id, user);
		return ResponseEntity.ok(userSaved);
	}
	
	@Operation(
        summary = "Atualizar a propriedade 'active' de um usuário",
        description = "Permite atualizar a propriedade 'active' de um usuário, ativando ou desativando sua conta."
    )
	@PutMapping("/{id}/active")
	@PreAuthorize("hasAuthority('ROLE_REGISTER_USER') and #oauth2.hasScope('write')")
	public void updateActiveProperty(
			@PathVariable Long id,
			@RequestBody Boolean active){
		userService.updateActiveProperty(id, active);
	}
}
