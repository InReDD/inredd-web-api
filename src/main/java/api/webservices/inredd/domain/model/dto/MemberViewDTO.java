package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.User;
import java.util.stream.Collectors;

/**
 * DTO usado para listar cada membro, agora contendo:
 *  - id
 *  - fullName        (nome completo)
 *  - firstName       (apenas o primeiro nome)
 *  - lastName        (apenas o sobrenome)
 *  - academicTitle   (título acadêmico, ex: "Profa. Dra.")
 *  - academicAbstract (resumo/abstract do Academic)
 *  - group           (nomes dos grupos concatenados)
 *  - email
 *  - institution
 */
public class MemberViewDTO {
    private Long   id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String academicTitle;
    private String academicAbstract;
    private String group;
    private String email;
    private String institution;
    private String lattesUrl;

    public MemberViewDTO(User user) {
        this.id = user.getIdUser();

        // primeiro e último nome separados
        this.firstName = user.getFirstName();
        this.lastName  = user.getLastName();

        // fullName (concatenação dos dois)
        this.fullName = this.firstName + " " + this.lastName;

        // academicTitle e academicAbstract (podem vir nulos se não houver Academic)
        if (user.getAcademic() != null) {
            this.academicTitle    = user.getAcademic().getTitle();
            this.academicAbstract = user.getAcademic().getAbstractText();
        } else {
            this.academicTitle    = null;
            this.academicAbstract = null;
        }

        // monta o link do lattes se houver lattesId
        String lattesId = user.getAcademic().getLattesId();
        if (lattesId != null && !lattesId.trim().isEmpty()) {
            this.lattesUrl = "http://lattes.cnpq.br/" + lattesId.trim();
        } else {
            this.lattesUrl = null;
        }

        // se o usuário pertencer a vários grupos, concatene os nomes:
        this.group = user.getGroups().stream()
                         .map(g -> g.getName())
                         .collect(Collectors.joining(", "));

        this.email = user.getEmail();
        this.institution = (user.getAcademic() != null)
            ? user.getAcademic().getInstitution()
            : "";
    }

    // --- getters ---

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

    public String getAcademicAbstract() {
        return academicAbstract;
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

    public String getLattesUrl() { 
        return lattesUrl; 
    }
}
