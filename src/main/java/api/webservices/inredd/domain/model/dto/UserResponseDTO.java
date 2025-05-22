package api.webservices.inredd.domain.model.dto;

import java.time.Instant;

public class UserResponseDTO {
    private Long    idUser;
    private String  firstName;
    private String  lastName;
    private String  email;
    private Boolean active;
    private Instant signedInAt;
    private Instant acceptedTermsAt;
    private Boolean userHasAcceptedTerms;
    private Instant acceptedPrivacyPolicyAt;
    private Boolean userHasAcceptedPrivacyPolicy;
    private Boolean userHasAccessToD2L;
    private Instant accessToD2LSince;
    private Boolean userHasAccessToOpenData;
    private Instant accessToOpenDataSince;

    public UserResponseDTO(api.webservices.inredd.domain.model.User u) {
        this.idUser                       = u.getIdUser();
        this.firstName                    = u.getFirstName();
        this.lastName                     = u.getLastName();
        this.email                        = u.getEmail();
        this.active                       = u.getActive();
        this.signedInAt                   = u.getSignedInAt();
        this.acceptedTermsAt              = u.getAcceptedTermsAt();
        this.userHasAcceptedTerms         = u.getUserHasAcceptedTerms();
        this.acceptedPrivacyPolicyAt      = u.getAcceptedPrivacyPolicyAt();
        this.userHasAcceptedPrivacyPolicy = u.getUserHasAcceptedPrivacyPolicy();
        this.userHasAccessToD2L           = u.getUserHasAccessToD2L();
        this.accessToD2LSince             = u.getAccessToD2LSince();
        this.userHasAccessToOpenData      = u.getUserHasAccessToOpenData();
        this.accessToOpenDataSince        = u.getAccessToOpenDataSince();
    }

    // getters (nenhum setter necessário para resposta imutável)
    public Long    getIdUser()                       { return idUser; }
    public String  getFirstName()                    { return firstName; }
    public String  getLastName()                     { return lastName; }
    public String  getEmail()                        { return email; }
    public Boolean getActive()                       { return active; }
    public Instant getSignedInAt()                   { return signedInAt; }
    public Instant getAcceptedTermsAt()              { return acceptedTermsAt; }
    public Boolean getUserHasAcceptedTerms()         { return userHasAcceptedTerms; }
    public Instant getAcceptedPrivacyPolicyAt()      { return acceptedPrivacyPolicyAt; }
    public Boolean getUserHasAcceptedPrivacyPolicy(){ return userHasAcceptedPrivacyPolicy; }
    public Boolean getUserHasAccessToD2L()           { return userHasAccessToD2L; }
    public Instant getAccessToD2LSince()             { return accessToD2LSince; }
    public Boolean getUserHasAccessToOpenData()      { return userHasAccessToOpenData; }
    public Instant getAccessToOpenDataSince()        { return accessToOpenDataSince; }
}
