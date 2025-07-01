package api.webservices.inredd.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "specific_health_questions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SpecificHealthQuestions {

    @Id
    private Long id;

    @Column(name = "has_cardiovascular_issue")
    private Boolean hasCardiovascularIssue;

    @Column(name = "has_rheumatic_fever")
    private Boolean hasRheumaticFever;

    @Column(name = "has_joint_pain")
    private Boolean hasJointPain;

    @Column(name = "has_chest_pain")
    private Boolean hasChestPain;

    @Column(name = "has_fatigue_on_exertion")
    private Boolean hasFatigueOnExertion;

    @Column(name = "has_ankle_edema")
    private Boolean hasAnkleEdema;

    @Column(name = "has_recent_weight_loss")
    private Boolean hasRecentWeightLoss;

    @Column(name = "had_hepatitis")
    private Boolean hadHepatitis;

    @Column(name = "has_kidney_problems")
    private Boolean hasKidneyProblems;

    @Column(name = "has_gastric_problems")
    private Boolean hasGastricProblems;

    @Column(name = "has_dizziness_fainting")
    private Boolean hasDizzinessFainting;

    @Column(name = "has_epilepsy")
    private Boolean hasEpilepsy;

    @Column(name = "was_hospitalized")
    private Boolean wasHospitalized;

    @Column(name = "has_persistent_cough")
    private Boolean hasPersistentCough;

    @Column(name = "had_local_anesthesia")
    private Boolean hadLocalAnesthesia;

    @Column(name = "had_anesthesia_reaction")
    private Boolean hadAnesthesiaReaction;

    @Column(name = "had_general_anesthesia")
    private Boolean hadGeneralAnesthesia;

    @Column(name = "has_excessive_bleeding")
    private Boolean hasExcessiveBleeding;

    @Column(name = "bleeding_control_method", columnDefinition = "TEXT")
    private String bleedingControlMethod;

    @Column(name = "had_dental_treatment_complication")
    private Boolean hadDentalTreatmentComplication;

    @Column(name = "took_penicillin")
    private Boolean tookPenicillin;

    @Column(name = "took_corticosteroid_last_12m") 
    private Boolean tookCorticosteroidLast12m;

    @Column(name = "has_allergies")
    private Boolean hasAllergies;

    @Column(name = "had_medication_related_problem")
    private Boolean hadMedicationRelatedProblem;

    @Column(name = "uses_substances")
    private Boolean usesSubstances;

    @Column(name = "had_oral_white_spots")
    private Boolean hadOralWhiteSpots;

    @Column(name = "white_spots_treatment", columnDefinition = "TEXT")
    private String whiteSpotsTreatment;

    @Column(name = "has_recurrent_aphthous_ulcers")
    private Boolean hasRecurrentAphthousUlcers;

    @Column(name = "had_hiv_test")
    private Boolean hadHivTest;

    @Column(name = "has_insensitive_body_area")
    private Boolean hasInsensitiveBodyArea;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "anamnesis_id")
    private AnamnesisForm anamnesisForm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getHasCardiovascularIssue() { return hasCardiovascularIssue; }
    public void setHasCardiovascularIssue(Boolean hasCardiovascularIssue) { this.hasCardiovascularIssue = hasCardiovascularIssue; }
    public Boolean getHasRheumaticFever() { return hasRheumaticFever; }
    public void setHasRheumaticFever(Boolean hasRheumaticFever) { this.hasRheumaticFever = hasRheumaticFever; }
    public Boolean getHasJointPain() { return hasJointPain; }
    public void setHasJointPain(Boolean hasJointPain) { this.hasJointPain = hasJointPain; }
    public Boolean getHasChestPain() { return hasChestPain; }
    public void setHasChestPain(Boolean hasChestPain) { this.hasChestPain = hasChestPain; }
    public Boolean getHasFatigueOnExertion() { return hasFatigueOnExertion; }
    public void setHasFatigueOnExertion(Boolean hasFatigueOnExertion) { this.hasFatigueOnExertion = hasFatigueOnExertion; }
    public Boolean getHasAnkleEdema() { return hasAnkleEdema; }
    public void setHasAnkleEdema(Boolean hasAnkleEdema) { this.hasAnkleEdema = hasAnkleEdema; }
    public Boolean getHasRecentWeightLoss() { return hasRecentWeightLoss; }
    public void setHasRecentWeightLoss(Boolean hasRecentWeightLoss) { this.hasRecentWeightLoss = hasRecentWeightLoss; }
    public Boolean getHadHepatitis() { return hadHepatitis; }
    public void setHadHepatitis(Boolean hadHepatitis) { this.hadHepatitis = hadHepatitis; }
    public Boolean getHasKidneyProblems() { return hasKidneyProblems; }
    public void setHasKidneyProblems(Boolean hasKidneyProblems) { this.hasKidneyProblems = hasKidneyProblems; }
    public Boolean getHasGastricProblems() { return hasGastricProblems; }
    public void setHasGastricProblems(Boolean hasGastricProblems) { this.hasGastricProblems = hasGastricProblems; }
    public Boolean getHasDizzinessFainting() { return hasDizzinessFainting; }
    public void setHasDizzinessFainting(Boolean hasDizzinessFainting) { this.hasDizzinessFainting = hasDizzinessFainting; }
    public Boolean getHasEpilepsy() { return hasEpilepsy; }
    public void setHasEpilepsy(Boolean hasEpilepsy) { this.hasEpilepsy = hasEpilepsy; }
    public Boolean getWasHospitalized() { return wasHospitalized; }
    public void setWasHospitalized(Boolean wasHospitalized) { this.wasHospitalized = wasHospitalized; }
    public Boolean getHasPersistentCough() { return hasPersistentCough; }
    public void setHasPersistentCough(Boolean hasPersistentCough) { this.hasPersistentCough = hasPersistentCough; }
    public Boolean getHadLocalAnesthesia() { return hadLocalAnesthesia; }
    public void setHadLocalAnesthesia(Boolean hadLocalAnesthesia) { this.hadLocalAnesthesia = hadLocalAnesthesia; }
    public Boolean getHadAnesthesiaReaction() { return hadAnesthesiaReaction; }
    public void setHadAnesthesiaReaction(Boolean hadAnesthesiaReaction) { this.hadAnesthesiaReaction = hadAnesthesiaReaction; }
    public Boolean getHadGeneralAnesthesia() { return hadGeneralAnesthesia; }
    public void setHadGeneralAnesthesia(Boolean hadGeneralAnesthesia) { this.hadGeneralAnesthesia = hadGeneralAnesthesia; }
    public Boolean getHasExcessiveBleeding() { return hasExcessiveBleeding; }
    public void setHasExcessiveBleeding(Boolean hasExcessiveBleeding) { this.hasExcessiveBleeding = hasExcessiveBleeding; }
    public String getBleedingControlMethod() { return bleedingControlMethod; }
    public void setBleedingControlMethod(String bleedingControlMethod) { this.bleedingControlMethod = bleedingControlMethod; }
    public Boolean getHadDentalTreatmentComplication() { return hadDentalTreatmentComplication; }
    public void setHadDentalTreatmentComplication(Boolean hadDentalTreatmentComplication) { this.hadDentalTreatmentComplication = hadDentalTreatmentComplication; }
    public Boolean getTookPenicillin() { return tookPenicillin; }
    public void setTookPenicillin(Boolean tookPenicillin) { this.tookPenicillin = tookPenicillin; }
    public Boolean getTookCorticosteroidLast12m() { return tookCorticosteroidLast12m; }
    public void setTookCorticosteroidLast12m(Boolean tookCorticosteroidLast12m) { this.tookCorticosteroidLast12m = tookCorticosteroidLast12m; }
    public Boolean getHasAllergies() { return hasAllergies; }
    public void setHasAllergies(Boolean hasAllergies) { this.hasAllergies = hasAllergies; }
    public Boolean getHadMedicationRelatedProblem() { return hadMedicationRelatedProblem; }
    public void setHadMedicationRelatedProblem(Boolean hadMedicationRelatedProblem) { this.hadMedicationRelatedProblem = hadMedicationRelatedProblem; }
    public Boolean getUsesSubstances() { return usesSubstances; }
    public void setUsesSubstances(Boolean usesSubstances) { this.usesSubstances = usesSubstances; }
    public Boolean getHadOralWhiteSpots() { return hadOralWhiteSpots; }
    public void setHadOralWhiteSpots(Boolean hadOralWhiteSpots) { this.hadOralWhiteSpots = hadOralWhiteSpots; }
    public String getWhiteSpotsTreatment() { return whiteSpotsTreatment; }
    public void setWhiteSpotsTreatment(String whiteSpotsTreatment) { this.whiteSpotsTreatment = whiteSpotsTreatment; }
    public Boolean getHasRecurrentAphthousUlcers() { return hasRecurrentAphthousUlcers; }
    public void setHasRecurrentAphthousUlcers(Boolean hasRecurrentAphthousUlcers) { this.hasRecurrentAphthousUlcers = hasRecurrentAphthousUlcers; }
    public Boolean getHadHivTest() { return hadHivTest; }
    public void setHadHivTest(Boolean hadHivTest) { this.hadHivTest = hadHivTest; }
    public Boolean getHasInsensitiveBodyArea() { return hasInsensitiveBodyArea; }
    public void setHasInsensitiveBodyArea(Boolean hasInsensitiveBodyArea) { this.hasInsensitiveBodyArea = hasInsensitiveBodyArea; }
    public AnamnesisForm getAnamnesisForm() { return anamnesisForm; }
    public void setAnamnesisForm(AnamnesisForm anamnesisForm) { this.anamnesisForm = anamnesisForm; }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SpecificHealthQuestions other = (SpecificHealthQuestions) obj;
        return Objects.equals(id, other.id);
    }
}