package api.webservices.inredd.domain.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Utilizado para retornar ao cliente os dados da AccessRequest antes de completar o cadastro.
 */
public class ValidateAccessRequestDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String solution;

    private String reason;

    /**
     * Segundos restantes até expirar.
     */
    @NotNull
    private Long expiresIn;

    public ValidateAccessRequestDTO() {
    }

    public ValidateAccessRequestDTO(String email, String firstName, String solution,
                                    String reason, Long expiresIn) {
        this.email     = email;
        this.firstName = firstName;
        this.solution  = solution;
        this.reason    = reason;
        this.expiresIn = expiresIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
