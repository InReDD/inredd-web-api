package api.webservices.inredd.domain.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

public class ProfileUpdateDTO {

    public ProfileUpdateDTO() {
    }

    @Size(min = 3, max = 45)
    private String firstName;

    @Size(min = 3, max = 45)
    private String lastName;

    @Email
    private String email;

    @Size(min = 3, max = 50)
    private String contact;

    private String academicTitle;

    private String institution;

    private String lattesId;

    private String abstractText;

    private String publicPicture;

    private List<AddressDTO> addresses;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public String getPublicPicture() {
        return publicPicture;
    }
    public void setPublicPicture(String publicPicture) {
        this.publicPicture = publicPicture;
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
}
