package api.webservices.inredd.domain.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "academic")
public class Academic {

    @Id
    @Column(name = "id_user")
    private Long idUser;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JsonBackReference
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "institution", length = 255)
    private String institution;

    @Column(name = "lattes_id", length = 255)
    private String lattesId;

    @Column(name = "abstract", columnDefinition = "TEXT")
    private String abstractText;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_address")
    private Address address;

    public Academic() {
        // Default
    }

    /**
     * Constructor initializing association with User
     */
    public Academic(User user) {
        this.user = user;
        this.idUser = user.getId();
    }

    // Getters and setters

    public Long getIdUser() {
        return idUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.idUser = user.getId();
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
