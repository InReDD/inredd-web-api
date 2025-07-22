package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.AnamnesisForm;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnamnesisFormDTO {

    private Long id;
    private BigDecimal weightKg;
    private BigDecimal heightM;
    private Integer systolicBp;
    private Integer diastolicBp;
    private Boolean isPregnant;
    private Boolean hadRecentFever;
    private Boolean isUnderMedicalTreatment;
    private Boolean isTakingMedication;
    private String detailedMedicalHistory;
    private String familyHealthHistory;
    private String previousDentalHistory;
    private String psychosocialHistory;
    private String additionalInfoForDentist;
    private String specialNeedsDuringTreatment;
    private SpecificHealthQuestionsDTO specificHealthQuestions;

    // Default constructor
    public AnamnesisFormDTO() {}

    // Constructor from Entity
    public AnamnesisFormDTO(AnamnesisForm entity) {
        this.id = entity.getId();
        this.weightKg = entity.getWeightKg();
        this.heightM = entity.getHeightM();
        this.systolicBp = entity.getSystolicBp();
        this.diastolicBp = entity.getDiastolicBp();
        this.isPregnant = entity.getPregnant();
        this.hadRecentFever = entity.getHadRecentFever();
        this.isUnderMedicalTreatment = entity.getUnderMedicalTreatment();
        this.isTakingMedication = entity.getTakingMedication();
        this.detailedMedicalHistory = entity.getDetailedMedicalHistory();
        this.familyHealthHistory = entity.getFamilyHealthHistory();
        this.previousDentalHistory = entity.getPreviousDentalHistory();
        this.psychosocialHistory = entity.getPsychosocialHistory();
        this.additionalInfoForDentist = entity.getAdditionalInfoForDentist();
        this.specialNeedsDuringTreatment = entity.getSpecialNeedsDuringTreatment();

        if (entity.getSpecificHealthQuestions() != null) {
            this.specificHealthQuestions = new SpecificHealthQuestionsDTO(entity.getSpecificHealthQuestions());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public BigDecimal getHeightM() {
        return heightM;
    }

    public void setHeightM(BigDecimal heightM) {
        this.heightM = heightM;
    }

    public Integer getSystolicBp() {
        return systolicBp;
    }

    public void setSystolicBp(Integer systolicBp) {
        this.systolicBp = systolicBp;
    }

    public Integer getDiastolicBp() {
        return diastolicBp;
    }

    public void setDiastolicBp(Integer diastolicBp) {
        this.diastolicBp = diastolicBp;
    }

    public Boolean getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public Boolean getHadRecentFever() {
        return hadRecentFever;
    }

    public void setHadRecentFever(Boolean hadRecentFever) {
        this.hadRecentFever = hadRecentFever;
    }

    public Boolean getIsUnderMedicalTreatment() {
        return isUnderMedicalTreatment;
    }

    public void setIsUnderMedicalTreatment(Boolean isUnderMedicalTreatment) {
        this.isUnderMedicalTreatment = isUnderMedicalTreatment;
    }

    public Boolean getIsTakingMedication() {
        return isTakingMedication;
    }

    public void setIsTakingMedication(Boolean isTakingMedication) {
        this.isTakingMedication = isTakingMedication;
    }

    public String getDetailedMedicalHistory() {
        return detailedMedicalHistory;
    }

    public void setDetailedMedicalHistory(String detailedMedicalHistory) {
        this.detailedMedicalHistory = detailedMedicalHistory;
    }

    public String getFamilyHealthHistory() {
        return familyHealthHistory;
    }

    public void setFamilyHealthHistory(String familyHealthHistory) {
        this.familyHealthHistory = familyHealthHistory;
    }

    public String getPreviousDentalHistory() {
        return previousDentalHistory;
    }

    public void setPreviousDentalHistory(String previousDentalHistory) {
        this.previousDentalHistory = previousDentalHistory;
    }

    public String getPsychosocialHistory() {
        return psychosocialHistory;
    }

    public void setPsychosocialHistory(String psychosocialHistory) {
        this.psychosocialHistory = psychosocialHistory;
    }

    public String getAdditionalInfoForDentist() {
        return additionalInfoForDentist;
    }

    public void setAdditionalInfoForDentist(String additionalInfoForDentist) {
        this.additionalInfoForDentist = additionalInfoForDentist;
    }

    public String getSpecialNeedsDuringTreatment() {
        return specialNeedsDuringTreatment;
    }

    public void setSpecialNeedsDuringTreatment(String specialNeedsDuringTreatment) {
        this.specialNeedsDuringTreatment = specialNeedsDuringTreatment;
    }

    public SpecificHealthQuestionsDTO getSpecificHealthQuestions() {
        return specificHealthQuestions;
    }

    public void setSpecificHealthQuestions(SpecificHealthQuestionsDTO specificHealthQuestions) {
        this.specificHealthQuestions = specificHealthQuestions;
    }
}