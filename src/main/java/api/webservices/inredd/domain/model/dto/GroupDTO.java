package api.webservices.inredd.domain.model.dto;

import java.util.List;
import java.util.stream.Collectors;

public class GroupDTO {
    private Long id;
    private String name;
    private List<UserDTO> users;

    public GroupDTO(api.webservices.inredd.domain.model.Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.users = group.getUsers().stream()
                         .map(UserDTO::new)
                         .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}