package api.webservices.inredd.domain.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class RadiographUploadDTO {

    private Long visitId;
    private Long patientId;
    private MultipartFile file;
    private String viewerContextJson;

    public RadiographUploadDTO(Long visitId, Long patientId, MultipartFile file, String viewerContextJson) {
        this.visitId = visitId;
        this.patientId = patientId;
        this.file = file;
        this.viewerContextJson = viewerContextJson;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getViewerContextJson() {
        return viewerContextJson;
    }

    public void setViewerContextJson(String viewerContextJson) {
        this.viewerContextJson = viewerContextJson;
    }

}