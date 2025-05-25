package api.webservices.inredd.domain.model.dto;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class MeDTO {
    private String displayName;
    private String imageBase64;

    // novos campos de acesso às soluções
    private boolean userHasAccessToD2L;
    private String  accessToD2LSince;
    private boolean userHasAccessToOpenData;
    private String  accessToOpenDataSince;

    // permissões
    private boolean canListMember;
    private boolean canInviteMember;
    private boolean canDeleteMember;
    private boolean canEditMember;
    private boolean canListGroup;
    private boolean canCreateGroup;
    private boolean canDeleteGroup;
    private boolean canEditGroup;
    private boolean canModeratePaper;
    private boolean canUploadPaper;
    private boolean canViewOpenDataDashboard;
    private boolean canViewD2LDashboard;
    private boolean canModerateAccessRequests;
    private boolean canListUser;
    private boolean canDeleteUser;
    private boolean canEditAcceptTerms;
    private boolean canEditPrivacyPolicy;
    // …e qualquer outra que você tenha no banco

    public MeDTO(
      String displayName,
      String imageBase64,
      boolean userHasAccessToD2L,
      String  accessToD2LSince,
      boolean userHasAccessToOpenData,
      String  accessToOpenDataSince,
      Set<String> perms
    ) {
      this.displayName            = displayName;
      this.imageBase64            = imageBase64;
      this.userHasAccessToD2L     = userHasAccessToD2L;
      this.accessToD2LSince       = accessToD2LSince;
      this.userHasAccessToOpenData= userHasAccessToOpenData;
      this.accessToOpenDataSince  = accessToOpenDataSince;

      this.canListMember               = perms.contains("ROLE_LIST_MEMBER");
      this.canInviteMember             = perms.contains("ROLE_INVITE_MEMBER");
      this.canDeleteMember             = perms.contains("ROLE_DELETE_MEMBER");
      this.canEditMember               = perms.contains("ROLE_EDIT_MEMBER");
      this.canListGroup                = perms.contains("ROLE_LIST_GROUP");
      this.canCreateGroup              = perms.contains("ROLE_CREATE_GROUP");
      this.canDeleteGroup              = perms.contains("ROLE_DELETE_GROUP");
      this.canEditGroup                = perms.contains("ROLE_EDIT_GROUP");
      this.canModeratePaper            = perms.contains("ROLE_MODERATE_PAPER");
      this.canUploadPaper              = perms.contains("ROLE_UPLOAD_PAPER");
      this.canViewOpenDataDashboard    = perms.contains("ROLE_SOLUTION_VIEW_OPEN_DATA_DASHBOARD");
      this.canViewD2LDashboard         = perms.contains("ROLE_SOLUTION_VIEW_D2L_DASHBOARD");
      this.canModerateAccessRequests   = perms.contains("ROLE_SOLUTION_MODERATE_ACCESS_REQUESTS");
      this.canListUser                 = perms.contains("ROLE_SOLUTION_LIST_USER");
      this.canDeleteUser               = perms.contains("ROLE_SOLUTION_DELETE_USER");
      this.canEditAcceptTerms          = perms.contains("ROLE_EDIT_ACCEPT_TERMS");
      this.canEditPrivacyPolicy        = perms.contains("ROLE_EDIT_PRIVACY_POLICY");
      // …e por aí vai
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public boolean isUserHasAccessToD2L() {
        return userHasAccessToD2L;
    }

    public void setUserHasAccessToD2L(boolean userHasAccessToD2L) {
        this.userHasAccessToD2L = userHasAccessToD2L;
    }

    public String getAccessToD2LSince() {
        return accessToD2LSince;
    }

    public void setAccessToD2LSince(String accessToD2LSince) {
        this.accessToD2LSince = accessToD2LSince;
    }

    public boolean isUserHasAccessToOpenData() {
        return userHasAccessToOpenData;
    }

    public void setUserHasAccessToOpenData(boolean userHasAccessToOpenData) {
        this.userHasAccessToOpenData = userHasAccessToOpenData;
    }

    public String getAccessToOpenDataSince() {
        return accessToOpenDataSince;
    }

    public void setAccessToOpenDataSince(String accessToOpenDataSince) {
        this.accessToOpenDataSince = accessToOpenDataSince;
    }

    public boolean isCanListMember() {
        return canListMember;
    }

    public void setCanListMember(boolean canListMember) {
        this.canListMember = canListMember;
    }

    public boolean isCanInviteMember() {
        return canInviteMember;
    }

    public void setCanInviteMember(boolean canInviteMember) {
        this.canInviteMember = canInviteMember;
    }

    public boolean isCanDeleteMember() {
        return canDeleteMember;
    }

    public void setCanDeleteMember(boolean canDeleteMember) {
        this.canDeleteMember = canDeleteMember;
    }

    public boolean isCanEditMember() {
        return canEditMember;
    }

    public void setCanEditMember(boolean canEditMember) {
        this.canEditMember = canEditMember;
    }

    public boolean isCanListGroup() {
        return canListGroup;
    }

    public void setCanListGroup(boolean canListGroup) {
        this.canListGroup = canListGroup;
    }

    public boolean isCanCreateGroup() {
        return canCreateGroup;
    }

    public void setCanCreateGroup(boolean canCreateGroup) {
        this.canCreateGroup = canCreateGroup;
    }

    public boolean isCanDeleteGroup() {
        return canDeleteGroup;
    }

    public void setCanDeleteGroup(boolean canDeleteGroup) {
        this.canDeleteGroup = canDeleteGroup;
    }

    public boolean isCanEditGroup() {
        return canEditGroup;
    }

    public void setCanEditGroup(boolean canEditGroup) {
        this.canEditGroup = canEditGroup;
    }

    public boolean isCanModeratePaper() {
        return canModeratePaper;
    }

    public void setCanModeratePaper(boolean canModeratePaper) {
        this.canModeratePaper = canModeratePaper;
    }

    public boolean isCanUploadPaper() {
        return canUploadPaper;
    }

    public void setCanUploadPaper(boolean canUploadPaper) {
        this.canUploadPaper = canUploadPaper;
    }

    public boolean isCanViewOpenDataDashboard() {
        return canViewOpenDataDashboard;
    }

    public void setCanViewOpenDataDashboard(boolean canViewOpenDataDashboard) {
        this.canViewOpenDataDashboard = canViewOpenDataDashboard;
    }

    public boolean isCanViewD2LDashboard() {
        return canViewD2LDashboard;
    }

    public void setCanViewD2LDashboard(boolean canViewD2LDashboard) {
        this.canViewD2LDashboard = canViewD2LDashboard;
    }

    public boolean isCanModerateAccessRequests() {
        return canModerateAccessRequests;
    }

    public void setCanModerateAccessRequests(boolean canModerateAccessRequests) {
        this.canModerateAccessRequests = canModerateAccessRequests;
    }

    public boolean isCanListUser() {
        return canListUser;
    }

    public void setCanListUser(boolean canListUser) {
        this.canListUser = canListUser;
    }

    public boolean isCanDeleteUser() {
        return canDeleteUser;
    }

    public void setCanDeleteUser(boolean canDeleteUser) {
        this.canDeleteUser = canDeleteUser;
    }

    public boolean isCanEditAcceptTerms() {
        return canEditAcceptTerms;
    }

    public void setCanEditAcceptTerms(boolean canEditAcceptTerms) {
        this.canEditAcceptTerms = canEditAcceptTerms;
    }

    public boolean isCanEditPrivacyPolicy() {
        return canEditPrivacyPolicy;
    }

    public void setCanEditPrivacyPolicy(boolean canEditPrivacyPolicy) {
        this.canEditPrivacyPolicy = canEditPrivacyPolicy;
    }
}