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
import api.webservices.inredd.domain.model.dto.GroupCreateDTO;
import api.webservices.inredd.domain.model.dto.SimpleGroupDTO;
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

    @Operation(
      summary = "Listar grupos paginados",
      description = "Retorna todos os grupos (somente id e name) com suporte a paginação e ordenação."
    )
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_LIST_GROUP') and #oauth2.hasScope('read')")
    public ResponseEntity<Page<SimpleGroupDTO>> list(Pageable pageable) {
        Page<SimpleGroupDTO> page = groupRepository
            .findAll(pageable)
            .map(g -> new SimpleGroupDTO(g.getId(), g.getName()));
        return ResponseEntity.ok(page);
    }

    @Operation(
      summary = "Criar um novo grupo",
      description = "POST /groups — cria um grupo com nome, descrição e lista de permissões"
    )
    @PreAuthorize("hasAuthority('ROLE_CREATE_GROUP') and #oauth2.hasScope('write')")
    @PostMapping
    public ResponseEntity<SimpleGroupDTO> create(@RequestBody GroupCreateDTO dto) {
        var g = groupService.createGroup(dto);
        var out = new SimpleGroupDTO(g.getId(), g.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @Operation(
      summary = "Atualizar um grupo existente",
      description = "PUT /groups/{id} — redefine nome, descrição e permissões do grupo"
    )
    @PreAuthorize("hasAuthority('ROLE_EDIT_GROUP') and #oauth2.hasScope('write')")
    @PutMapping("/{id}")
    public ResponseEntity<SimpleGroupDTO> update(
            @PathVariable Long id,
            @RequestBody GroupCreateDTO dto) {

        Group updated = groupService.updateGroup(id, dto);
        SimpleGroupDTO out = new SimpleGroupDTO(updated.getId(), updated.getName());
        return ResponseEntity.ok(out);
    }

    @Operation(
      summary = "Remover um grupo",
      description = "DELETE /groups/{id} — só deleta se não houver usuários associados"
    )
    @PreAuthorize("hasAuthority('ROLE_DELETE_GROUP') and #oauth2.hasScope('write')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }

}
