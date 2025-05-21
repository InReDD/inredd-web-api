package api.webservices.inredd.domain.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "academic")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idUser"
)
public class Academic {

    @Id
    @Column(name = "id_user")
    private Long idUser;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address address;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "institution", length = 255)
    private String institution;

    @Column(name = "lattes_id", length = 255)
    private String lattesId;

    @Column(name = "abstract", columnDefinition = "TEXT")
    private String abstractText;

    public Academic() {
        // Default
    }

    /**
     * Constructor initializing association with User
     */
    public Academic(User user) {
        this.user = user;
        this.idUser = user.getIdUser();
    }

    // Getters and setters

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getLattesId() {
        return lattesId;
    }

    public void setLattesId(String lattesId) {
        this.lattesId = lattesId;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
