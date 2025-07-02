package api.webservices.inredd.specification;

import api.webservices.inredd.domain.model.*;
import api.webservices.inredd.domain.model.dto.VisitSearchCriteriaDTO;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;

public class VisitSpecification {

    public static Specification<Visit> createSpecification(VisitSearchCriteriaDTO criteria) {
        // Start with a specification that does nothing (will be built upon)
        Specification<Visit> spec = Specification.where(null);

        // --- Standard Filters ---
        if (criteria.getPatientName() != null && !criteria.getPatientName().isEmpty()) {
            spec = spec.and(patientNameContains(criteria.getPatientName()));
        }
        if (criteria.getVisitDateStart() != null) {
            spec = spec.and(visitDateGreaterThanOrEqual(criteria.getVisitDateStart()));
        }
        if (criteria.getVisitDateEnd() != null) {
            spec = spec.and(visitDateLessThanOrEqual(criteria.getVisitDateEnd()));
        }
        if (criteria.getMainComplaintContains() != null && !criteria.getMainComplaintContains().isEmpty()) {
            spec = spec.and(mainComplaintContains(criteria.getMainComplaintContains()));
        }

        // --- Dynamically add all health condition filters ---
        if (criteria.getHasCardiovascularIssue() != null) {
            spec = spec.and(hasHealthCondition("hasCardiovascularIssue", criteria.getHasCardiovascularIssue()));
        }
        if (criteria.getHasRheumaticFever() != null) {
            spec = spec.and(hasHealthCondition("hasRheumaticFever", criteria.getHasRheumaticFever()));
        }
        if (criteria.getHasJointPain() != null) {
            spec = spec.and(hasHealthCondition("hasJointPain", criteria.getHasJointPain()));
        }
        if (criteria.getHasChestPain() != null) {
            spec = spec.and(hasHealthCondition("hasChestPain", criteria.getHasChestPain()));
        }
        if (criteria.getHasFatigueOnExertion() != null) {
            spec = spec.and(hasHealthCondition("hasFatigueOnExertion", criteria.getHasFatigueOnExertion()));
        }
        if (criteria.getHasAnkleEdema() != null) {
            spec = spec.and(hasHealthCondition("hasAnkleEdema", criteria.getHasAnkleEdema()));
        }
        if (criteria.getHasRecentWeightLoss() != null) {
            spec = spec.and(hasHealthCondition("hasRecentWeightLoss", criteria.getHasRecentWeightLoss()));
        }
        if (criteria.getHadHepatitis() != null) {
            spec = spec.and(hasHealthCondition("hadHepatitis", criteria.getHadHepatitis()));
        }
        if (criteria.getHasKidneyProblems() != null) {
            spec = spec.and(hasHealthCondition("hasKidneyProblems", criteria.getHasKidneyProblems()));
        }
        if (criteria.getHasGastricProblems() != null) {
            spec = spec.and(hasHealthCondition("hasGastricProblems", criteria.getHasGastricProblems()));
        }
        if (criteria.getHasDizzinessFainting() != null) {
            spec = spec.and(hasHealthCondition("hasDizzinessFainting", criteria.getHasDizzinessFainting()));
        }
        if (criteria.getHasEpilepsy() != null) {
            spec = spec.and(hasHealthCondition("hasEpilepsy", criteria.getHasEpilepsy()));
        }
        if (criteria.getWasHospitalized() != null) {
            spec = spec.and(hasHealthCondition("wasHospitalized", criteria.getWasHospitalized()));
        }
        if (criteria.getHasPersistentCough() != null) {
            spec = spec.and(hasHealthCondition("hasPersistentCough", criteria.getHasPersistentCough()));
        }
        if (criteria.getHadLocalAnesthesia() != null) {
            spec = spec.and(hasHealthCondition("hadLocalAnesthesia", criteria.getHadLocalAnesthesia()));
        }
        if (criteria.getHadAnesthesiaReaction() != null) {
            spec = spec.and(hasHealthCondition("hadAnesthesiaReaction", criteria.getHadAnesthesiaReaction()));
        }
        if (criteria.getHadGeneralAnesthesia() != null) {
            spec = spec.and(hasHealthCondition("hadGeneralAnesthesia", criteria.getHadGeneralAnesthesia()));
        }
        if (criteria.getHasExcessiveBleeding() != null) {
            spec = spec.and(hasHealthCondition("hasExcessiveBleeding", criteria.getHasExcessiveBleeding()));
        }
        if (criteria.getHadDentalTreatmentComplication() != null) {
            spec = spec.and(hasHealthCondition("hadDentalTreatmentComplication", criteria.getHadDentalTreatmentComplication()));
        }
        if (criteria.getTookPenicillin() != null) {
            spec = spec.and(hasHealthCondition("tookPenicillin", criteria.getTookPenicillin()));
        }
        if (criteria.getTookCorticosteroidLast12m() != null) {
            spec = spec.and(hasHealthCondition("tookCorticosteroidLast12m", criteria.getTookCorticosteroidLast12m()));
        }
        if (criteria.getHasAllergies() != null) {
            spec = spec.and(hasHealthCondition("hasAllergies", criteria.getHasAllergies()));
        }
        if (criteria.getHadMedicationRelatedProblem() != null) {
            spec = spec.and(hasHealthCondition("hadMedicationRelatedProblem", criteria.getHadMedicationRelatedProblem()));
        }
        if (criteria.getUsesSubstances() != null) {
            spec = spec.and(hasHealthCondition("usesSubstances", criteria.getUsesSubstances()));
        }
        if (criteria.getHadOralWhiteSpots() != null) {
            spec = spec.and(hasHealthCondition("hadOralWhiteSpots", criteria.getHadOralWhiteSpots()));
        }
        if (criteria.getHasRecurrentAphthousUlcers() != null) {
            spec = spec.and(hasHealthCondition("hasRecurrentAphthousUlcers", criteria.getHasRecurrentAphthousUlcers()));
        }
        if (criteria.getHadHivTest() != null) {
            spec = spec.and(hasHealthCondition("hadHivTest", criteria.getHadHivTest()));
        }
        if (criteria.getHasInsensitiveBodyArea() != null) {
            spec = spec.and(hasHealthCondition("hasInsensitiveBodyArea", criteria.getHasInsensitiveBodyArea()));
        }

        return spec;
    }

    /**
     * A utility to find an existing join or create a new one if it doesn't exist.
     * This prevents duplicate joins and resolves potential NullPointerExceptions.
     */
    @SuppressWarnings("unchecked")
    private static <FROM, TO> Join<FROM, TO> getOrCreateJoin(From<FROM, ?> from, String attribute, JoinType joinType) {
        for (Join<?, ?> j : from.getJoins()) {
            if (j.getAttribute().getName().equals(attribute)) {
                return (Join<FROM, TO>) j;
            }
        }
        return from.join(attribute, joinType);
    }

    /**
     * Generic specification for any boolean health question.
     */
    private static Specification<Visit> hasHealthCondition(String attributeName, boolean value) {
        return (root, query, criteriaBuilder) -> {
            Join<Visit, AnamnesisForm> anamnesisJoin = getOrCreateJoin(root, "anamnesisForm", JoinType.LEFT);
            Join<Visit, AnamnesisForm> questionsJoin = getOrCreateJoin(anamnesisJoin, "specificHealthQuestions", JoinType.LEFT);
            return criteriaBuilder.equal(questionsJoin.get(attributeName), value);
        };
    }

    /**
     * Specification to filter by patient name (case-insensitive).
     */
    private static Specification<Visit> patientNameContains(String patientName) {
        return (root, query, criteriaBuilder) -> {
            Join<Visit, Patient> patientJoin = getOrCreateJoin(root, "patient", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(patientJoin.get("fullName")), "%" + patientName.toLowerCase() + "%");
        };
    }

    /**
     * Specification to filter visits on or after a specific date.
     */
    private static Specification<Visit> visitDateGreaterThanOrEqual(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("visitDate"), startDate);
    }

    /**
     * Specification to filter visits on or before a specific date.
     */
    private static Specification<Visit> visitDateLessThanOrEqual(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("visitDate"), endDate);
    }

    /**
     * Specification to filter by text within the main complaint (case-insensitive).
     */
    private static Specification<Visit> mainComplaintContains(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("mainComplaint")), "%" + text.toLowerCase() + "%");
    }
}