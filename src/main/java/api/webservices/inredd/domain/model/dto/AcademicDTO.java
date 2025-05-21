package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Academic;

public class AcademicDTO {

    private String title;
    private String institution;
    private String lattesId;
    private String abstractText;
    private AddressDTO address;

    public AcademicDTO() {
    }

    public AcademicDTO(Academic academic) {
        this.title = academic.getTitle();
        this.institution = academic.getInstitution();
        this.lattesId = academic.getLattesId();
        this.abstractText = academic.getAbstractText();

        if (academic.getAddress() != null) {
            this.address = new AddressDTO(academic.getAddress());
        }
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}