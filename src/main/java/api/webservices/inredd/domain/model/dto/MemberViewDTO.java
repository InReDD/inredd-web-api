package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.User;
import java.util.stream.Collectors;

public class MemberViewDTO {
    private Long   id;
    private String name;
    private String group;
    private String email;
    private String institution;

    public MemberViewDTO(User user) {
        this.id = user.getIdUser();
        this.name = user.getFirstName() + " " + user.getLastName();
        this.group = user.getGroups().stream()
                         .map(g -> g.getName())
                         .collect(Collectors.joining(", "));
        this.email = user.getEmail();
        this.institution = user.getAcademic() != null
            ? user.getAcademic().getInstitution()
            : "";
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getGroup() { return group; }
    public String getEmail() { return email; }
    public String getInstitution() { return institution; }
}
