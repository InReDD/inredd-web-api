package api.webservices.inredd.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "\"user\"")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	@NotNull
	@Size(min = 3, max = 45)
	private String firstName;
	@NotNull
	@Size(min = 3, max = 45)
	private String lastName;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(min = 6, max = 150)
	private String password;
	@Size(min = 3, max = 50)
	private String contact;
	@NotNull
	private Boolean active;
	@Lob
    @Column(name = "public_picture", nullable = true)
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] publicPicture;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "id_user_permission"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
	private List<Permission> permissions;
	@ManyToMany(mappedBy = "users")
	private List<Group> groups;
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
	@JsonManagedReference   // lado “pai” da serialização
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

	@Column(name = "accepted_terms_at")
    private Instant acceptedTermsAt;

    @Column(name = "user_has_accepted_terms", nullable = false)
    private Boolean userHasAcceptedTerms = Boolean.FALSE;

	@Column(name = "accepted_privacy_policy_at")
    private Instant acceptedPrivacyPolicyAt;

    @Column(name = "user_has_accepted_privacy_policy", nullable = false)
    private Boolean userHasAcceptedPrivacyPolicy = Boolean.FALSE;

	public Long getId() {
		return idUser;
	}

	public void setId(Long idUser) {
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

	public byte[] getPublicPicture() {
        return publicPicture;
    }

    public void setPublicPicture(byte[] publicPicture) {
        this.publicPicture = publicPicture;
    }

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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
        if (academic != null) {
            academic.setUser(this);
        }
    }

	public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        for (Address a : addresses) {
        	a.setUser(this);
        }
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }
	
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
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

}
