package api.webservices.inredd.domain.model.dto;

import java.util.List;

public class ProfileDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String academicTitle;
    private String institution;

    private String publicPicture;     
    private String lattesId;   
    private String abstractText;   
    private List<String> groupNames;  

    private List<AddressDTO> addresses;
    private Boolean userHasAccessToD2L;
    private Boolean userHasAccessToOpenData;

    public ProfileDTO() {
        // no-args
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getUserHasAccessToD2L() {
        return userHasAccessToD2L;
    }

    public void setUserHasAccessToD2L(Boolean userHasAccessToD2L) {
        this.userHasAccessToD2L = userHasAccessToD2L;
    }

    public Boolean getUserHasAccessToOpenData() {
        return userHasAccessToOpenData;
    }

    public void setUserHasAccessToOpenData(Boolean userHasAccessToOpenData) {
        this.userHasAccessToOpenData = userHasAccessToOpenData;
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

    public List<String> getGroupNames() {
        return groupNames;
    }
    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }
}
