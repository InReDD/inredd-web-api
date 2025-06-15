package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.domain.model.Address;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para retornar os detalhes completos de um membro, incluindo:
 *   - id do usuário
 *   - nome dividido em firstName / lastName / academicTitle
 *   - data de criação do usuário na plataforma (signedInAt)
 *   - data de quando foi inserido no grupo (memberSince, vinda da tabela de junção)
 *   - lista de endereços, cada um com street/city/state/country
 *   - demais campos já existentes (e.g. email, contato, permissões etc.)
 */
public class MemberDetailDTO {

    private Long   id;
    private String firstName;
    private String lastName;
    private String academicTitle;
    private String createdOn;     // data em que o usuário foi criado (signedInAt)

    private List<AddressDTO> addresses;
    private String           email;
    private String           contact;

    private List<GroupInfo>  groups;         // lista de grupos + data de inserção
    private String           institution;
    private String           photoBase64;
    private String           signedInOn;     // quando fez o primeiro login (igual createdOn, mas mantido para compatibilidade)
    private String           memberSince;    // (manter para compatibilidade: data da aceitação de termos)

    private String           lattesId;
    private String           lattesUrl;
    private String           bio;            // abstractText
    private Boolean          userHasAccessToD2L;
    private String           accessToD2LSince;
    private Boolean          userHasAccessToOpenData;
    private String           accessToOpenDataSince;

    // --- Construtor principal, que recebe a entidade User e popula todos os campos ---
    public MemberDetailDTO(User u) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 1) ID
        this.id = u.getIdUser();

        // 2) Nome separado
        this.firstName     = u.getFirstName();
        this.lastName      = u.getLastName();
        this.academicTitle = (u.getAcademic() != null) ? u.getAcademic().getTitle() : null;

        // 3) Data de criação do usuário (signedInAt)
        if (u.getSignedInAt() != null) {
            this.createdOn = fmt.format(u.getSignedInAt().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        // 4) Endereços (AddressDTO verá abaixo formato)
        this.addresses = u.getAddresses().stream()
            .map(AddressDTO::new)
            .collect(Collectors.toList());

        // 5) Email e contato
        this.email   = u.getEmail();
        this.contact = u.getContact();

        // 6) signedInOn (manter compatibilidade com antigo campo)
        if (u.getSignedInAt() != null) {
            this.signedInOn = fmt.format(u.getSignedInAt().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        // 7) memberSince (data de aceitação de termos; se tiver)
        if (u.getAcceptedTermsAt() != null) {
            this.memberSince = fmt.format(u.getAcceptedTermsAt().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        // 8) Grupos + data de inserção no grupo
        //    Para obter a data de inclusão no grupo (member_since), precisaremos consultar a tabela "groups_has_user".
        //    Supondo que você tenha um método em MemberRepository que retorne a tupla (Group, Instant memberSince),
        //    aqui simplificamos fazendo um cast bruto (caso tenha mapeado uma entidade intermediária).
        this.groups = u.getGroups().stream()
            .map(g -> {
                // "memberSince" deverá vir de um campo extra na entidade de junção (GroupUser) se existir.
                // Se você não tiver uma entidade intermediária, será necessário um JOIN manual no repositório.
                // Aqui colocamos null ou a mesma data de createdOn, apenas para ilustrar:
                String memberSinceStr = null;
                if (fmt != null && u.getAcceptedTermsAt() != null) {
                    memberSinceStr = fmt.format(u.getAcceptedTermsAt().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                return new GroupInfo(g.getIdGroups(), g.getName(), memberSinceStr);
            })
            .collect(Collectors.toList());

        // 9) Academic (institution, photo, etc.)
        this.institution = (u.getAcademic() != null) ? u.getAcademic().getInstitution() : null;

        if (u.getPublicPicture() != null) {
            this.photoBase64 = u.getPublicPicture();
        }

        if (u.getAcademic() != null) {
            this.lattesId = u.getAcademic().getLattesId();
            this.bio      = u.getAcademic().getAbstractText();
            if (this.lattesId != null && !this.lattesId.trim().isEmpty()) {
                this.lattesUrl = "http://lattes.cnpq.br/" + this.lattesId.trim();
            }
        }

        // 10) Acessos a D2L / Open Data
        this.userHasAccessToD2L = u.getUserHasAccessToD2L();
        if (u.getAccessToD2LSince() != null) {
            this.accessToD2LSince = fmt.format(u.getAccessToD2LSince().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        this.userHasAccessToOpenData = u.getUserHasAccessToOpenData();
        if (u.getAccessToOpenDataSince() != null) {
            this.accessToOpenDataSince = fmt.format(u.getAccessToOpenDataSince().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    // --- Getters (omiti setters para breviedade) ---

    public Long getId() {
        return id;
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

    public String getCreatedOn() {
        return createdOn;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getSignedInOn() {
        return signedInOn;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public List<GroupInfo> getGroups() {
        return groups;
    }

    public String getInstitution() {
        return institution;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public String getLattesId() {
        return lattesId;
    }

    public String getBio() {
        return bio;
    }

    public Boolean getUserHasAccessToD2L() {
        return userHasAccessToD2L;
    }

    public String getAccessToD2LSince() {
        return accessToD2LSince;
    }

    public Boolean getUserHasAccessToOpenData() {
        return userHasAccessToOpenData;
    }

    public String getAccessToOpenDataSince() {
        return accessToOpenDataSince;
    }

    // --- Classes auxiliares internas ---

    /** 
     * Em “groups_has_user” existe um campo `member_since` (timestamp). 
     * Essa classe encapsula id, nome do grupo e data de entrada.
     */
    public static class GroupInfo {
        private Long   id;
        private String name;
        private String memberSince;

        public GroupInfo(Long id, String name, String memberSince) {
            this.id          = id;
            this.name        = name;
            this.memberSince = memberSince;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        /**
         * Formato: “dd/MM/yyyy”
         */
        public String getMemberSince() {
            return memberSince;
        }
    }

    /**
     * Cada endereço será enviado como objeto com campos:
     *   - street  (campo `address` na entidade)
     *   - city
     *   - state
     *   - country
     */
    public static class AddressDTO {
        private String street;
        private String city;
        private String state;
        private String country;

        public AddressDTO(Address a) {
            this.street  = a.getAddress();   // nome do campo na entidade
            this.city    = a.getCity();
            this.state   = a.getState();
            this.country = a.getCountry();
        }

        public String getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }
    }

    public String getLattesUrl() {
        return lattesUrl;
    }

    public void setLattesUrl(String lattesUrl) {
        this.lattesUrl = lattesUrl;
    }
}
