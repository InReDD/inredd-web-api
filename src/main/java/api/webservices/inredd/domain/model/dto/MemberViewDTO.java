package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.User;
import java.util.stream.Collectors;

/**
 * DTO usado para listar cada membro, agora contendo:
 *  - id
 *  - fullName  (nome completo)
 *  - firstName
 *  - lastName
 *  - group     (nome ou nomes de grupos)
 *  - email
 *  - institution
 */
public class MemberViewDTO {
    private Long   id;
    private String fullName;      // nome completo
    private String firstName;     // somente o primeiro nome
    private String lastName;      // somente o sobrenome
    private String group;         // nomes dos grupos concatenados
    private String email;
    private String institution;

    public MemberViewDTO(User user) {
        this.id = user.getIdUser();

        // primeiro e último nome separados
        this.firstName = user.getFirstName();
        this.lastName  = user.getLastName();

        // fullName (concatenação dos dois)
        this.fullName = this.firstName + " " + this.lastName;

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
