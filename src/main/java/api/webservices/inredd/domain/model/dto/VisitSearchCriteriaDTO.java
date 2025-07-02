// package api.webservices.inredd.domain.model.dto;
package api.webservices.inredd.domain.model.dto;

import java.time.LocalDate;

public class VisitSearchCriteriaDTO {
    private String patientName;
    private LocalDate visitDateStart;
    private LocalDate visitDateEnd;
    private String mainComplaintContains;

    private Boolean hasCardiovascularIssue;
    private Boolean hasRheumaticFever;
    private Boolean hasJointPain;
    private Boolean hasChestPain;
    private Boolean hasFatigueOnExertion;
    private Boolean hasAnkleEdema;
    private Boolean hasRecentWeightLoss;
    private Boolean hadHepatitis;
    private Boolean hasKidneyProblems;
    private Boolean hasGastricProblems;
    private Boolean hasDizzinessFainting;
    private Boolean hasEpilepsy;
    private Boolean wasHospitalized;
    private Boolean hasPersistentCough;
    private Boolean hadLocalAnesthesia;
    private Boolean hadAnesthesiaReaction;
    private Boolean hadGeneralAnesthesia;
    private Boolean hasExcessiveBleeding;
    private Boolean hadDentalTreatmentComplication;
    private Boolean tookPenicillin;
    private Boolean tookCorticosteroidLast12m;
    private Boolean hasAllergies;
    private Boolean hadMedicationRelatedProblem;
    private Boolean usesSubstances;
    private Boolean hadOralWhiteSpots;
    private Boolean hasRecurrentAphthousUlcers;
    private Boolean hadHivTest;
    private Boolean hasInsensitiveBodyArea;

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public LocalDate getVisitDateStart() { return visitDateStart; }
    public void setVisitDateStart(LocalDate visitDateStart) { this.visitDateStart = visitDateStart; }
    public LocalDate getVisitDateEnd() { return visitDateEnd; }
    public void setVisitDateEnd(LocalDate visitDateEnd) { this.visitDateEnd = visitDateEnd; }
    public String getMainComplaintContains() { return mainComplaintContains; }
    public void setMainComplaintContains(String mainComplaintContains) { this.mainComplaintContains = mainComplaintContains; }
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
    public Boolean getHasRecurrentAphthousUlcers() { return hasRecurrentAphthousUlcers; }
    public void setHasRecurrentAphthousUlcers(Boolean hasRecurrentAphthousUlcers) { this.hasRecurrentAphthousUlcers = hasRecurrentAphthousUlcers; }
    public Boolean getHadHivTest() { return hadHivTest; }
    public void setHadHivTest(Boolean hadHivTest) { this.hadHivTest = hadHivTest; }
    public Boolean getHasInsensitiveBodyArea() { return hasInsensitiveBodyArea; }
    public void setHasInsensitiveBodyArea(Boolean hasInsensitiveBodyArea) { this.hasInsensitiveBodyArea = hasInsensitiveBodyArea; }
}