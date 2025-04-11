package api.webservices.inredd.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.UserDTO;
import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.domain.model.dto.GroupDTO;
import api.webservices.inredd.repository.GroupRepository;
import api.webservices.inredd.service.GroupService;

@SecurityRequirement(name = "oauth2_scheme")
@RestController
@RequestMapping("/groups")
@Tag(name = "Groups", description = "Operações com grupos de usuários")
public class GroupResource {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    @Operation(summary = "Listar grupos paginados", description = "Retorna todos os grupos com suporte a paginação e ordenação.")
    @GetMapping
    public Page<GroupDTO> list(Pageable pageable) {
        return groupRepository.findAll(pageable).map(GroupDTO::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_REGISTER_ACTIVITY') and #oauth2.hasScope('write')")
    public Group create(@Valid @RequestBody Group group,
                        HttpServletResponse response) {
        return groupService.save(group);
    }

    @Operation(summary = "Buscar grupo por ID", description = "Retorna os dados de um grupo específico.")
    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> findById(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group
            .map(g -> ResponseEntity.ok(new GroupDTO(g)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remover grupo por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVE_ACTIVITY') and #oauth2.hasScope('write')")
    public void remove(@PathVariable Long id) {
        groupRepository.deleteById(id);
    }

    @Operation(summary = "Atualizar grupo por ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_ACTIVITY') and #oauth2.hasScope('write')")
    public ResponseEntity<Group> update(@PathVariable Long id,
                                        @Valid @RequestBody Group group) {
        Group updatedGroup = groupService.update(id, group);
        return ResponseEntity.ok(updatedGroup);
    }

    @Operation(summary = "Listar usuários por grupo", description = "Retorna usuários vinculados ao grupo com suporte à paginação.")
    @GetMapping("/{id}/users")
    public ResponseEntity<Page<UserDTO>> listUsersByGroup(
            @PathVariable Long id,
            Pageable pageable) {

        List<UserDTO> allUsers = groupService.findUsersByGroupId(id);

        // Paginação manual
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int start = Math.min((int) pageable.getOffset(), allUsers.size());
        int end = Math.min(start + size, allUsers.size());

        Page<UserDTO> pageResult = new PageImpl<>(
            allUsers.subList(start, end),
            pageable,
            allUsers.size()
        );

        return ResponseEntity.ok(pageResult);
    }

    @Operation(summary = "Listar usuários de múltiplos grupos", description = "Retorna usuários únicos vinculados a múltiplos grupos (por lista de IDs).")
    @GetMapping("/users-by-groups")
    public ResponseEntity<Page<UserDTO>> listUsersByGroupIds(
            @RequestParam List<Long> ids,
            Pageable pageable) {

        List<UserDTO> users = groupService.findUsersByGroupIds(ids);

        // Remover duplicados (se necessário)
        List<UserDTO> uniqueUsers = users.stream()
            .distinct()
            .collect(Collectors.toList());

        // Paginar manualmente
        int start = Math.min((int) pageable.getOffset(), uniqueUsers.size());
        int end = Math.min(start + pageable.getPageSize(), uniqueUsers.size());

        Page<UserDTO> pageResult = new PageImpl<>(
            uniqueUsers.subList(start, end), pageable, uniqueUsers.size());

        return ResponseEntity.ok(pageResult);
    }
}
