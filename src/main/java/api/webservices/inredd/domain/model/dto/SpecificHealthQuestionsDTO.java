package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.SpecificHealthQuestions;

public class SpecificHealthQuestionsDTO {

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
    private String bleedingControlMethod;
    private Boolean hadDentalTreatmentComplication;
    private Boolean tookPenicillin;
    private Boolean tookCorticosteroidLast12m;
    private Boolean hasAllergies;
    private Boolean hadMedicationRelatedProblem;
    private Boolean usesSubstances;
    private Boolean hadOralWhiteSpots;
    private String whiteSpotsTreatment;
    private Boolean hasRecurrentAphthousUlcers;
    private Boolean hadHivTest;
    private Boolean hasInsensitiveBodyArea;

    // Default constructor
    public SpecificHealthQuestionsDTO() {}

    // Constructor from Entity
    public SpecificHealthQuestionsDTO(SpecificHealthQuestions entity) {
        this.hasCardiovascularIssue = entity.getHasCardiovascularIssue();
        this.hasRheumaticFever = entity.getHasRheumaticFever();
        this.hasJointPain = entity.getHasJointPain();
        this.hasChestPain = entity.getHasChestPain();
        this.hasFatigueOnExertion = entity.getHasFatigueOnExertion();
        this.hasAnkleEdema = entity.getHasAnkleEdema();
        this.hasRecentWeightLoss = entity.getHasRecentWeightLoss();
        this.hadHepatitis = entity.getHadHepatitis();
        this.hasKidneyProblems = entity.getHasKidneyProblems();
        this.hasGastricProblems = entity.getHasGastricProblems();
        this.hasDizzinessFainting = entity.getHasDizzinessFainting();
        this.hasEpilepsy = entity.getHasEpilepsy();
        this.wasHospitalized = entity.getWasHospitalized();
        this.hasPersistentCough = entity.getHasPersistentCough();
        this.hadLocalAnesthesia = entity.getHadLocalAnesthesia();
        this.hadAnesthesiaReaction = entity.getHadAnesthesiaReaction();
        this.hadGeneralAnesthesia = entity.getHadGeneralAnesthesia();
        this.hasExcessiveBleeding = entity.getHasExcessiveBleeding();
        this.bleedingControlMethod = entity.getBleedingControlMethod();
        this.hadDentalTreatmentComplication = entity.getHadDentalTreatmentComplication();
        this.tookPenicillin = entity.getTookPenicillin();
        this.tookCorticosteroidLast12m = entity.getTookCorticosteroidLast12m();
        this.hasAllergies = entity.getHasAllergies();
        this.hadMedicationRelatedProblem = entity.getHadMedicationRelatedProblem();
        this.usesSubstances = entity.getUsesSubstances();
        this.hadOralWhiteSpots = entity.getHadOralWhiteSpots();
        this.whiteSpotsTreatment = entity.getWhiteSpotsTreatment();
        this.hasRecurrentAphthousUlcers = entity.getHasRecurrentAphthousUlcers();
        this.hadHivTest = entity.getHadHivTest();
        this.hasInsensitiveBodyArea = entity.getHasInsensitiveBodyArea();
    }

    public Boolean getHasCardiovascularIssue() {
        return hasCardiovascularIssue;
    }

    public void setHasCardiovascularIssue(Boolean hasCardiovascularIssue) {
        this.hasCardiovascularIssue = hasCardiovascularIssue;
    }

    public Boolean getHasRheumaticFever() {
        return hasRheumaticFever;
    }

    public void setHasRheumaticFever(Boolean hasRheumaticFever) {
        this.hasRheumaticFever = hasRheumaticFever;
    }

    public Boolean getHasJointPain() {
        return hasJointPain;
    }

    public void setHasJointPain(Boolean hasJointPain) {
        this.hasJointPain = hasJointPain;
    }

    public Boolean getHasChestPain() {
        return hasChestPain;
    }

    public void setHasChestPain(Boolean hasChestPain) {
        this.hasChestPain = hasChestPain;
    }

    public Boolean getHasFatigueOnExertion() {
        return hasFatigueOnExertion;
    }

    public void setHasFatigueOnExertion(Boolean hasFatigueOnExertion) {
        this.hasFatigueOnExertion = hasFatigueOnExertion;
    }

    public Boolean getHasAnkleEdema() {
        return hasAnkleEdema;
    }

    public void setHasAnkleEdema(Boolean hasAnkleEdema) {
        this.hasAnkleEdema = hasAnkleEdema;
    }

    public Boolean getHasRecentWeightLoss() {
        return hasRecentWeightLoss;
    }

    public void setHasRecentWeightLoss(Boolean hasRecentWeightLoss) {
        this.hasRecentWeightLoss = hasRecentWeightLoss;
    }

    public Boolean getHadHepatitis() {
        return hadHepatitis;
    }

    public void setHadHepatitis(Boolean hadHepatitis) {
        this.hadHepatitis = hadHepatitis;
    }

    public Boolean getHasKidneyProblems() {
        return hasKidneyProblems;
    }

    public void setHasKidneyProblems(Boolean hasKidneyProblems) {
        this.hasKidneyProblems = hasKidneyProblems;
    }

    public Boolean getHasGastricProblems() {
        return hasGastricProblems;
    }

    public void setHasGastricProblems(Boolean hasGastricProblems) {
        this.hasGastricProblems = hasGastricProblems;
    }

    public Boolean getHasDizzinessFainting() {
        return hasDizzinessFainting;
    }

    public void setHasDizzinessFainting(Boolean hasDizzinessFainting) {
        this.hasDizzinessFainting = hasDizzinessFainting;
    }

    public Boolean getHasEpilepsy() {
        return hasEpilepsy;
    }

    public void setHasEpilepsy(Boolean hasEpilepsy) {
        this.hasEpilepsy = hasEpilepsy;
    }

    public Boolean getWasHospitalized() {
        return wasHospitalized;
    }

    public void setWasHospitalized(Boolean wasHospitalized) {
        this.wasHospitalized = wasHospitalized;
    }

    public Boolean getHasPersistentCough() {
        return hasPersistentCough;
    }

    public void setHasPersistentCough(Boolean hasPersistentCough) {
        this.hasPersistentCough = hasPersistentCough;
    }

    public Boolean getHadLocalAnesthesia() {
        return hadLocalAnesthesia;
    }

    public void setHadLocalAnesthesia(Boolean hadLocalAnesthesia) {
        this.hadLocalAnesthesia = hadLocalAnesthesia;
    }

    public Boolean getHadAnesthesiaReaction() {
        return hadAnesthesiaReaction;
    }

    public void setHadAnesthesiaReaction(Boolean hadAnesthesiaReaction) {
        this.hadAnesthesiaReaction = hadAnesthesiaReaction;
    }

    public Boolean getHadGeneralAnesthesia() {
        return hadGeneralAnesthesia;
    }

    public void setHadGeneralAnesthesia(Boolean hadGeneralAnesthesia) {
        this.hadGeneralAnesthesia = hadGeneralAnesthesia;
    }

    public Boolean getHasExcessiveBleeding() {
        return hasExcessiveBleeding;
    }

    public void setHasExcessiveBleeding(Boolean hasExcessiveBleeding) {
        this.hasExcessiveBleeding = hasExcessiveBleeding;
    }

    public String getBleedingControlMethod() {
        return bleedingControlMethod;
    }

    public void setBleedingControlMethod(String bleedingControlMethod) {
        this.bleedingControlMethod = bleedingControlMethod;
    }

    public Boolean getHadDentalTreatmentComplication() {
        return hadDentalTreatmentComplication;
    }

    public void setHadDentalTreatmentComplication(Boolean hadDentalTreatmentComplication) {
        this.hadDentalTreatmentComplication = hadDentalTreatmentComplication;
    }

    public Boolean getTookPenicillin() {
        return tookPenicillin;
    }

    public void setTookPenicillin(Boolean tookPenicillin) {
        this.tookPenicillin = tookPenicillin;
    }

    public Boolean getTookCorticosteroidLast12m() {
        return tookCorticosteroidLast12m;
    }

    public void setTookCorticosteroidLast12m(Boolean tookCorticosteroidLast12m) {
        this.tookCorticosteroidLast12m = tookCorticosteroidLast12m;
    }

    public Boolean getHasAllergies() {
        return hasAllergies;
    }

    public void setHasAllergies(Boolean hasAllergies) {
        this.hasAllergies = hasAllergies;
    }

    public Boolean getHadMedicationRelatedProblem() {
        return hadMedicationRelatedProblem;
    }

    public void setHadMedicationRelatedProblem(Boolean hadMedicationRelatedProblem) {
        this.hadMedicationRelatedProblem = hadMedicationRelatedProblem;
    }

    public Boolean getUsesSubstances() {
        return usesSubstances;
    }

    public void setUsesSubstances(Boolean usesSubstances) {
        this.usesSubstances = usesSubstances;
    }

    public Boolean getHadOralWhiteSpots() {
        return hadOralWhiteSpots;
    }

    public void setHadOralWhiteSpots(Boolean hadOralWhiteSpots) {
        this.hadOralWhiteSpots = hadOralWhiteSpots;
    }

    public String getWhiteSpotsTreatment() {
        return whiteSpotsTreatment;
    }

    public void setWhiteSpotsTreatment(String whiteSpotsTreatment) {
        this.whiteSpotsTreatment = whiteSpotsTreatment;
    }

    public Boolean getHasRecurrentAphthousUlcers() {
        return hasRecurrentAphthousUlcers;
    }

    public void setHasRecurrentAphthousUlcers(Boolean hasRecurrentAphthousUlcers) {
        this.hasRecurrentAphthousUlcers = hasRecurrentAphthousUlcers;
    }

    public Boolean getHadHivTest() {
        return hadHivTest;
    }

    public void setHadHivTest(Boolean hadHivTest) {
        this.hadHivTest = hadHivTest;
    }

    public Boolean getHasInsensitiveBodyArea() {
        return hasInsensitiveBodyArea;
    }

    public void setHasInsensitiveBodyArea(Boolean hasInsensitiveBodyArea) {
        this.hasInsensitiveBodyArea = hasInsensitiveBodyArea;
    }

   
}