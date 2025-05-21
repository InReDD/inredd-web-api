package api.webservices.inredd.domain.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "groups")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idGroups"
)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_groups")
    private Long idGroups;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(name = "groups_has_user",
            joinColumns = @JoinColumn(name = "id_groups"),
            inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<User> users = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "group_permission",
        joinColumns = @JoinColumn(name = "id_groups"),
        inverseJoinColumns = @JoinColumn(name = "id_permission")
    )
    private List<Permission> permissions = new ArrayList<>();

    public Long getIdGroups() {
        return idGroups;
    }

    public void setIdGroups(Long idGroups) {
        this.idGroups = idGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(idGroups, group.idGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroups);
    }
}
