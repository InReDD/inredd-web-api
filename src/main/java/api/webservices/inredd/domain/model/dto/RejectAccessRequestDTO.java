package api.webservices.inredd.domain.model.dto;

import javax.validation.constraints.NotBlank;

public class RejectAccessRequestDTO {
    @NotBlank
    private String reason;

    public RejectAccessRequestDTO() {}
    public RejectAccessRequestDTO(String reason) { this.reason = reason; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}