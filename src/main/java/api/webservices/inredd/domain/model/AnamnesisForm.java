package api.webservices.inredd.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "anamnesis_forms")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnamnesisForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_kg")
    private BigDecimal weightKg;

    @Column(name = "height_m")
    private BigDecimal heightM;

    @Column(name = "systolic_bp")
    private Integer systolicBp;

    @Column(name = "diastolic_bp")
    private Integer diastolicBp;

    @Column(name = "is_pregnant")
    private Boolean isPregnant;

    @Column(name = "had_recent_fever")
    private Boolean hadRecentFever;

    @Column(name = "is_under_medical_treatment")
    private Boolean isUnderMedicalTreatment;

    @Column(name = "is_taking_medication")
    private Boolean isTakingMedication;

    @Column(name = "detailed_medical_history", columnDefinition = "TEXT")
    private String detailedMedicalHistory;

    @Column(name = "family_health_history", columnDefinition = "TEXT")
    private String familyHealthHistory;

    @Column(name = "previous_dental_history", columnDefinition = "TEXT")
    private String previousDentalHistory;

    @Column(name = "psychosocial_history", columnDefinition = "TEXT")
    private String psychosocialHistory;

    @Column(name = "additional_info_for_dentist", columnDefinition = "TEXT")
    private String additionalInfoForDentist;

    @Column(name = "special_needs_during_treatment", columnDefinition = "TEXT")
    private String specialNeedsDuringTreatment;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false, unique = true)
    private Visit visit;

    @OneToOne(mappedBy = "anamnesisForm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private SpecificHealthQuestions specificHealthQuestions;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getWeightKg() { return weightKg; }
    public void setWeightKg(BigDecimal weightKg) { this.weightKg = weightKg; }
    public BigDecimal getHeightM() { return heightM; }
    public void setHeightM(BigDecimal heightM) { this.heightM = heightM; }
    public Integer getSystolicBp() { return systolicBp; }
    public void setSystolicBp(Integer systolicBp) { this.systolicBp = systolicBp; }
    public Integer getDiastolicBp() { return diastolicBp; }
    public void setDiastolicBp(Integer diastolicBp) { this.diastolicBp = diastolicBp; }
    public Boolean getPregnant() { return isPregnant; }
    public void setPregnant(Boolean pregnant) { isPregnant = pregnant; }
    public Boolean getHadRecentFever() { return hadRecentFever; }
    public void setHadRecentFever(Boolean hadRecentFever) { this.hadRecentFever = hadRecentFever; }
    public Boolean getUnderMedicalTreatment() { return isUnderMedicalTreatment; }
    public void setUnderMedicalTreatment(Boolean underMedicalTreatment) { isUnderMedicalTreatment = underMedicalTreatment; }
    public Boolean getTakingMedication() { return isTakingMedication; }
    public void setTakingMedication(Boolean takingMedication) { isTakingMedication = takingMedication; }
    public String getDetailedMedicalHistory() { return detailedMedicalHistory; }
    public void setDetailedMedicalHistory(String detailedMedicalHistory) { this.detailedMedicalHistory = detailedMedicalHistory; }
    public String getFamilyHealthHistory() { return familyHealthHistory; }
    public void setFamilyHealthHistory(String familyHealthHistory) { this.familyHealthHistory = familyHealthHistory; }
    public String getPreviousDentalHistory() { return previousDentalHistory; }
    public void setPreviousDentalHistory(String previousDentalHistory) { this.previousDentalHistory = previousDentalHistory; }
    public String getPsychosocialHistory() { return psychosocialHistory; }
    public void setPsychosocialHistory(String psychosocialHistory) { this.psychosocialHistory = psychosocialHistory; }
    public String getAdditionalInfoForDentist() { return additionalInfoForDentist; }
    public void setAdditionalInfoForDentist(String additionalInfoForDentist) { this.additionalInfoForDentist = additionalInfoForDentist; }
    public String getSpecialNeedsDuringTreatment() { return specialNeedsDuringTreatment; }
    public void setSpecialNeedsDuringTreatment(String specialNeedsDuringTreatment) { this.specialNeedsDuringTreatment = specialNeedsDuringTreatment; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public Visit getVisit() { return visit; }
    public void setVisit(Visit visit) { this.visit = visit; }
    public SpecificHealthQuestions getSpecificHealthQuestions() { return specificHealthQuestions; }
    public void setSpecificHealthQuestions(SpecificHealthQuestions specificHealthQuestions) { this.specificHealthQuestions = specificHealthQuestions; }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AnamnesisForm other = (AnamnesisForm) obj;
        return Objects.equals(id, other.id);
    }
}