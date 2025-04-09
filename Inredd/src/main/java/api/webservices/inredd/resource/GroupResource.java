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

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.UserDTO;
import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.domain.model.dto.GroupDTO;
import api.webservices.inredd.repository.GroupRepository;
import api.webservices.inredd.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupResource {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<GroupDTO> list() {
        return groupRepository.findAll().stream()
            .map(GroupDTO::new)
            .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_REGISTER_ACTIVITY') and #oauth2.hasScope('write')")
    public Group create(@Valid @RequestBody Group group,
                        HttpServletResponse response) {
        return groupService.save(group);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> findById(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group
            .map(g -> ResponseEntity.ok(new GroupDTO(g)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVE_ACTIVITY') and #oauth2.hasScope('write')")
    public void remove(@PathVariable Long id) {
        groupRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_ACTIVITY') and #oauth2.hasScope('write')")
    public ResponseEntity<Group> update(@PathVariable Long id,
                                        @Valid @RequestBody Group group) {
        Group updatedGroup = groupService.update(id, group);
        return ResponseEntity.ok(updatedGroup);
    }

    @GetMapping("/{id}/users")
    public List<UserDTO> listUsersByGroup(@PathVariable Long id) {
        return groupService.findUsersByGroupId(id);
    }
}
