package api.webservices.inredd.domain.model.dto;

public class MemberDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private Boolean active;
    private String academicTitle;
    private String academicInstitution;
    private String academicLattesId;
    private String academicAbstract;

    /**
     * Constructor used by JPA constructor expression in @Query
     */
    public MemberDTO(String firstName,
                     String lastName,
                     String email,
                     String contact,
                     Boolean active,
                     String academicTitle,
                     String academicInstitution,
                     String academicLattesId,
                     String academicAbstract) {
        this.firstName           = firstName;
        this.lastName            = lastName;
        this.email               = email;
        this.contact             = contact;
        this.active              = active;
        this.academicTitle       = academicTitle;
        this.academicInstitution = academicInstitution;
        this.academicLattesId    = academicLattesId;
        this.academicAbstract    = academicAbstract;
    }

    // Getters only for immutability
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public String getEmail()     { return email;     }
    public String getContact()   { return contact;   }
    public Boolean getActive()   { return active;    }
    public String getAcademicTitle()       { return academicTitle; }
    public String getAcademicInstitution() { return academicInstitution; }
    public String getAcademicLattesId()    { return academicLattesId; }
    public String getAcademicAbstract()    { return academicAbstract; }
}
