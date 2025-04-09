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
}
