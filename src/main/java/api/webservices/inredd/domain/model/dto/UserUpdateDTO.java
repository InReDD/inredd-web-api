package api.webservices.inredd.domain.model.dto;

public class UserUpdateDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private Boolean active;
    private AcademicUpdateDTO academic;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public AcademicUpdateDTO getAcademic() {
        return academic;
    }

    public void setAcademic(AcademicUpdateDTO academic) {
        this.academic = academic;
    }
}