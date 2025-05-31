package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.User;
import java.util.stream.Collectors;


public class MemberViewDTO {
    private Long   id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String academicTitle;
    private String group;
    private String email;
    private String institution;

    public MemberViewDTO(User user) {
        this.id = user.getIdUser();

        // primeiro e último nome separados
        this.firstName = user.getFirstName();
        this.lastName  = user.getLastName();

        // fullName (concatenação dos dois)
        this.fullName = this.firstName + " " + this.lastName;

        // academicTitle (pode vir nulo se não houver registro em Academic)
        this.academicTitle = (user.getAcademic() != null)
            ? user.getAcademic().getTitle()
            : null;

        // se o usuário pertencer a vários grupos, concatene os nomes:
        this.group = user.getGroups().stream()
                         .map(g -> g.getName())
                         .collect(Collectors.joining(", "));

        this.email = user.getEmail();
        this.institution = (user.getAcademic() != null)
            ? user.getAcademic().getInstitution()
            : "";
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public String getGroup() {
        return group;
    }

    public String getEmail() {
        return email;
    }

    public String getInstitution() {
        return institution;
    }
}
