package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Address;
import api.webservices.inredd.domain.model.User;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class MemberDetailDTO {
    private String fullTitleAndName;
    private String institution;
    private String photoBase64;
    private List<String> addresses;
    private String email;
    private String contact;
    private String signedInOn;
    private String memberSince;
    private List<GroupInfo> groups;
    private String lattesId;
    private String bio;
    private Boolean userHasAccessToD2L;
    private String accessToD2LSince;
    private Boolean userHasAccessToOpenData;
    private String accessToOpenDataSince;

    public static class GroupInfo {
        private Long id;
        private String name;

        public GroupInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public void setId(Long id) { this.id = id; }
        public void setName(String name) { this.name = name; }
    }

    public MemberDetailDTO(User u) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (u.getAcademic() != null && u.getAcademic().getTitle() != null) {
            this.fullTitleAndName = u.getAcademic().getTitle()
                + " " + u.getFirstName() + " " + u.getLastName();
        } else {
            this.fullTitleAndName = u.getFirstName() + " " + u.getLastName();
        }

        this.institution = u.getAcademic() != null
            ? u.getAcademic().getInstitution() : null;

        if (u.getPublicPicture() != null) {
            this.photoBase64 = Base64.getEncoder()
                .encodeToString(u.getPublicPicture());
        }

        this.addresses = u.getAddresses().stream()
            .map(a -> a.getCountry() + ", " + a.getState() + ", " + a.getCity())
            .collect(Collectors.toList());

        this.email = u.getEmail();
        this.contact = u.getContact();

        if (u.getSignedInAt() != null) {
            this.signedInOn = fmt.format(
                u.getSignedInAt().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }

        if (u.getAcceptedTermsAt() != null) {
            this.memberSince = fmt.format(
                u.getAcceptedTermsAt().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }

        this.groups = u.getGroups().stream()
            .map(g -> new GroupInfo(g.getIdGroups(), g.getName()))
            .collect(Collectors.toList());

        if (u.getAcademic() != null) {
            this.lattesId = u.getAcademic().getLattesId();
            this.bio = u.getAcademic().getAbstractText();
        }

        this.userHasAccessToD2L = u.getUserHasAccessToD2L();
        if (u.getAccessToD2LSince() != null) {
            this.accessToD2LSince = fmt.format(
                u.getAccessToD2LSince().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }

        this.userHasAccessToOpenData = u.getUserHasAccessToOpenData();
        if (u.getAccessToOpenDataSince() != null) {
            this.accessToOpenDataSince = fmt.format(
                u.getAccessToOpenDataSince().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        }
    }

    // ----- Getters -----
    public String getFullTitleAndName() { return fullTitleAndName; }
    public String getInstitution() { return institution; }
    public String getPhotoBase64() { return photoBase64; }
    public List<String> getAddresses() { return addresses; }
    public String getEmail() { return email; }
    public String getContact() { return contact; }
    public String getSignedInOn() { return signedInOn; }
    public String getMemberSince() { return memberSince; }
    public List<GroupInfo> getGroups() { return groups; }
    public String getLattesId() { return lattesId; }
    public String getBio() { return bio; }
    public Boolean getUserHasAccessToD2L() { return userHasAccessToD2L; }
    public String getAccessToD2LSince() { return accessToD2LSince; }
    public Boolean getUserHasAccessToOpenData() { return userHasAccessToOpenData; }
    public String getAccessToOpenDataSince() { return accessToOpenDataSince; }
}
