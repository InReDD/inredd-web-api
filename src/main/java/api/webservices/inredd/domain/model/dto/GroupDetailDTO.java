package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.Permission;

import java.util.List;
import java.util.stream.Collectors;


public class GroupDetailDTO {

    private Long   id;
    private String name;
    private String description;
    private List<MemberSummary> members;
    private List<PermissionInfo> permissions;

    public GroupDetailDTO(Group group) {
        this.id          = group.getIdGroups();
        this.name        = group.getName();
        this.description = group.getDescription();


        this.members = group.getUsers().stream()
            .map(MemberSummary::new)
            .collect(Collectors.toList());

        this.permissions = group.getPermissions().stream()
            .map(PermissionInfo::new)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<MemberSummary> getMembers() {
        return members;
    }

    public List<PermissionInfo> getPermissions() {
        return permissions;
    }

    public static class MemberSummary {
        private Long   id;
        private String firstName;
        private String lastName;
        private String email;
        private String institution;

        public MemberSummary(User u) {
            this.id           = u.getIdUser();
            this.firstName    = u.getFirstName();
            this.lastName     = u.getLastName();
            this.email        = u.getEmail();
            this.institution  = (u.getAcademic() != null)
                                  ? u.getAcademic().getInstitution()
                                  : null;
        }

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getInstitution() {
            return institution;
        }
    }

    public static class PermissionInfo {
        private Long id;
        private String name;
    
        public PermissionInfo(Permission p) {
            this.id = p.getId();
            this.name = p.getDescription();
        }
    
        public Long getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }
}
