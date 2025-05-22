package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private Boolean active;
    private Boolean userHasAcceptedTerms;
    private Boolean userHasAcceptedPrivacyPolicy;
    private AcademicDTO academic;
    private List<AddressDTO> addresses = new ArrayList<>();
    private List<GroupDTO> groups = new ArrayList<>();
    private List<PermissionDTO> permissions = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.idUser = user.getIdUser();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.contact = user.getContact();
        this.active = user.getActive();
        this.userHasAcceptedTerms = user.getUserHasAcceptedTerms();
        this.userHasAcceptedPrivacyPolicy = user.getUserHasAcceptedPrivacyPolicy();

        if (user.getAcademic() != null) {
            this.academic = new AcademicDTO(user.getAcademic());
        }

        if (user.getAddresses() != null) {
            this.addresses = user.getAddresses().stream()
                    .map(AddressDTO::new)
                    .collect(Collectors.toList());
        }

        if (user.getGroups() != null) {
            this.groups = user.getGroups().stream()
                    .map(GroupDTO::new)
                    .collect(Collectors.toList());
        }

        if (user.getPermissions() != null) {
            this.permissions = user.getPermissions().stream()
                    .map(PermissionDTO::new)
                    .collect(Collectors.toList());
        }
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getUserHasAcceptedTerms() {
        return userHasAcceptedTerms;
    }

    public void setUserHasAcceptedTerms(Boolean userHasAcceptedTerms) {
        this.userHasAcceptedTerms = userHasAcceptedTerms;
    }

    public Boolean getUserHasAcceptedPrivacyPolicy() {
        return userHasAcceptedPrivacyPolicy;
    }

    public void setUserHasAcceptedPrivacyPolicy(Boolean userHasAcceptedPrivacyPolicy) {
        this.userHasAcceptedPrivacyPolicy = userHasAcceptedPrivacyPolicy;
    }

    public AcademicDTO getAcademic() {
        return academic;
    }

    public void setAcademic(AcademicDTO academic) {
        this.academic = academic;
    }

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }

    public List<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}