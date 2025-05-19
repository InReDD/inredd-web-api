package api.webservices.inredd.domain.model.dto;

public class MeDTO {
    private String displayName;
    private String imageBase64;

    public MeDTO(String displayName, String imageBase64) {
        this.displayName  = displayName;
        this.imageBase64  = imageBase64;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageBase64() {
        return imageBase64;
    }
}