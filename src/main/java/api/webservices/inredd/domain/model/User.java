package api.webservices.inredd.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "\"user\"")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "idUser"
)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long idUser;

	@NotNull
	@Size(min = 3, max = 45)
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Size(min = 3, max = 45)
	@Column(name = "last_name")
	private String lastName;

	@NotNull
	@Email
	@Column(name = "email")
	private String email;

	@NotNull
	@Size(min = 6, max = 150)
	@Column(name = "password")
	private String password;

	@Size(min = 3, max = 50)
	@Column(name = "contact")
	private String contact;

	@NotNull
	@Column(name = "active")
	private Boolean active;

    @Column(name = "public_picture")
	private String publicPicture;

	@Column(name = "signed_in_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Instant signedInAt;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_permission",
			joinColumns = @JoinColumn(name = "id_user_permission"),
			inverseJoinColumns = @JoinColumn(name = "id_permission")
	)
	private List<Permission> permissions = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "groups_has_user",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_groups")
	)
	private List<Group> groups = new ArrayList<>();

	@ManyToMany(mappedBy = "users")
	private List<Paper> papers;
	/**
     * Relacionamento one-to-one para Academic.
     * Lado inverso do @MapsId na entidade Academic.
     * Opcional porque o usuário nao vem com todos os dados acadêmicos preenchidos.
     */
    @OneToOne(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        optional = true
    )
//	@JsonManagedReference   // lado “pai” da serialização
    private Academic academic;
	/**
     * Relacionamento one-to-many para Address.
     * Cada usuário pode ter múltiplos endereços.
     */
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Address> addresses = new ArrayList<>();

	@Column(name = "accepted_terms_at",
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Instant acceptedTermsAt;

    @Column(name = "user_has_accepted_terms", nullable = false)
    private Boolean userHasAcceptedTerms = Boolean.FALSE;

	@Column(name = "accepted_privacy_policy_at",
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Instant acceptedPrivacyPolicyAt;

    @Column(name = "user_has_accepted_privacy_policy", nullable = false)
    private Boolean userHasAcceptedPrivacyPolicy = Boolean.FALSE;

	@Column(name = "user_has_access_to_d2l", nullable = false)
    private Boolean userHasAccessToD2L = Boolean.FALSE;

    @Column(name = "access_to_d2l_since")
    private Instant accessToD2LSince;

    @Column(name = "user_has_access_to_open_data", nullable = false)
    private Boolean userHasAccessToOpenData = Boolean.FALSE;

    @Column(name = "access_to_open_data_since")
    private Instant accessToOpenDataSince;

	@Override
	public int hashCode() {
		return Objects.hash(idUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(idUser, other.idUser);
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getPublicPicture() {
		return publicPicture;
	}

	public void setPublicPicture(String publicPicture) {
		this.publicPicture = publicPicture;
	}

	public Instant getSignedInAt() {
        return signedInAt;
    }
	
    public void setSignedInAt(Instant signedInAt) {
        this.signedInAt = signedInAt;
    }

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Paper> getPapers() {
		return papers;
	}

	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}

	public Academic getAcademic() {
		return academic;
	}

	public void setAcademic(Academic academic) {
		this.academic = academic;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Instant getAcceptedTermsAt() {
		return acceptedTermsAt;
	}

	public void setAcceptedTermsAt(Instant acceptedTermsAt) {
		this.acceptedTermsAt = acceptedTermsAt;
	}

	public Boolean getUserHasAcceptedTerms() {
		return userHasAcceptedTerms;
	}

	public void setUserHasAcceptedTerms(Boolean userHasAcceptedTerms) {
		this.userHasAcceptedTerms = userHasAcceptedTerms;
	}

	public Instant getAcceptedPrivacyPolicyAt() {
		return acceptedPrivacyPolicyAt;
	}

	public void setAcceptedPrivacyPolicyAt(Instant acceptedPrivacyPolicyAt) {
		this.acceptedPrivacyPolicyAt = acceptedPrivacyPolicyAt;
	}

	public Boolean getUserHasAcceptedPrivacyPolicy() {
		return userHasAcceptedPrivacyPolicy;
	}

	public void setUserHasAcceptedPrivacyPolicy(Boolean userHasAcceptedPrivacyPolicy) {
		this.userHasAcceptedPrivacyPolicy = userHasAcceptedPrivacyPolicy;
	}

	public Boolean getUserHasAccessToD2L() { 
		return userHasAccessToD2L; 
	}
    public void setUserHasAccessToD2L(Boolean v) { 
		this.userHasAccessToD2L = v; 
	}

    public Instant getAccessToD2LSince() { 
		return accessToD2LSince; 
	}
    public void setAccessToD2LSince(Instant v) { 
		this.accessToD2LSince = v; 
	}

    public Boolean getUserHasAccessToOpenData() { 
		return userHasAccessToOpenData; 
	}
    public void setUserHasAccessToOpenData(Boolean v) { 
		this.userHasAccessToOpenData = v; 
	}

    public Instant getAccessToOpenDataSince() { 
		return accessToOpenDataSince; 
	}
    public void setAccessToOpenDataSince(Instant v) { 
		this.accessToOpenDataSince = v; 
	}
}