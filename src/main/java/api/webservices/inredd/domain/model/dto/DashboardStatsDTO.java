package api.webservices.inredd.domain.model.dto;

import java.util.List;

public class DashboardStatsDTO {

    private long totalPatients;
    private long upcomingVisitsCount; 
    private List<PatientDTO> recentPatients; 

    public DashboardStatsDTO(long totalPatients, long upcomingVisitsCount, List<PatientDTO> recentPatients) {
        this.totalPatients = totalPatients;
        this.upcomingVisitsCount = upcomingVisitsCount;
        this.recentPatients = recentPatients;
    }

    public long getTotalPatients() { return totalPatients; }
    public void setTotalPatients(long totalPatients) { this.totalPatients = totalPatients; }
    public long getUpcomingVisitsCount() { return upcomingVisitsCount; }
    public void setUpcomingVisitsCount(long upcomingVisitsCount) { this.upcomingVisitsCount = upcomingVisitsCount; }
    public List<PatientDTO> getRecentPatients() { return recentPatients; }
    public void setRecentPatients(List<PatientDTO> recentPatients) { this.recentPatients = recentPatients; }
}