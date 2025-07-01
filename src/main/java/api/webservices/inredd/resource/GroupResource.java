package api.webservices.inredd.resource;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.domain.model.dto.GroupCreateDTO;
import api.webservices.inredd.domain.model.dto.GroupDetailDTO;
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
    public ResponseEntity<Page<SimpleGroupDTO>> list(Pageable pageable) {
        Page<SimpleGroupDTO> page = groupRepository
            .findAll(pageable)
            .map(g -> new SimpleGroupDTO(g.getIdGroups(), g.getName(), g.getDescription()));
        return ResponseEntity.ok(page);
    }

    @Operation(
      summary = "Exibir detalhes de um grupo",
      description = "Retorna detalhes completos de um grupo (id, name, description + lista de membros)."
    )
    @GetMapping("/{id}")
    public ResponseEntity<GroupDetailDTO> getGroupById(@PathVariable("id") Long id) {
        try {
            GroupDetailDTO dto = groupService.getGroupDetail(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
      summary = "Criar um novo grupo",
      description = "POST /groups — cria um grupo com nome, descrição e lista de permissões"
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE_GROUP') and #oauth2.hasScope('write')")
    public ResponseEntity<SimpleGroupDTO> create(@RequestBody GroupCreateDTO dto) {
        Group g = groupService.createGroup(dto);
        // antes: g.getId()
        SimpleGroupDTO out = new SimpleGroupDTO(
            g.getIdGroups(),
            g.getName(),
            g.getDescription()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @Operation(
      summary = "Atualizar um grupo existente",
      description = "PUT /groups/{id} — redefine nome, descrição e permissões do grupo"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_EDIT_GROUP') and #oauth2.hasScope('write')")
    public ResponseEntity<SimpleGroupDTO> update(
            @PathVariable Long id,
            @RequestBody GroupCreateDTO dto) {

        Group updated = groupService.updateGroup(id, dto);
        // antes usava updated.getIdUser(), agora:
        SimpleGroupDTO out = new SimpleGroupDTO(
            updated.getIdGroups(),
            updated.getName(),
            updated.getDescription()
        );
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
