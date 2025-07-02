package api.webservices.inredd.domain.model.dto;

import java.util.List;
import java.util.Map;

public class AdvancedSearchResultDTO {

    /**
     * A list of VisitDTOs that match the search criteria.
     */
    private List<VisitDTO> results;

    /**
     * A map containing aggregated data.
     */
    private Map<String, Object> stats;

    /**
     * Constructs a new AdvancedSearchResultDTO.
     *
     * @param results The list of visit results.
     * @param stats   The map of calculated statistics.
     */
    public AdvancedSearchResultDTO(List<VisitDTO> results, Map<String, Object> stats) {
        this.results = results;
        this.stats = stats;
    }

    public List<VisitDTO> getResults() {
        return results;
    }

    public void setResults(List<VisitDTO> results) {
        this.results = results;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public void setStats(Map<String, Object> stats) {
        this.stats = stats;
    }
}