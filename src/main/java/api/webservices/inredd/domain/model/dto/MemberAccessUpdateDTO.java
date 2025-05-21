package api.webservices.inredd.domain.model.dto;

import javax.validation.constraints.NotNull;

/**
 * DTO para atualizar apenas flags de acesso e grupo de um membro.
 * As datas serão definidas pelo sistema: se a flag for true, a data atual; se false, null.
 */
public class MemberAccessUpdateDTO {

    @NotNull
    private Boolean userHasAccessToD2L;

    @NotNull
    private Boolean userHasAccessToOpenData;

    @NotNull
    private Long groupId;

    public Boolean getUserHasAccessToD2L() {
        return userHasAccessToD2L;
    }

    public void setUserHasAccessToD2L(Boolean userHasAccessToD2L) {
        this.userHasAccessToD2L = userHasAccessToD2L;
    }

    public Boolean getUserHasAccessToOpenData() {
        return userHasAccessToOpenData;
    }

    public void setUserHasAccessToOpenData(Boolean userHasAccessToOpenData) {
        this.userHasAccessToOpenData = userHasAccessToOpenData;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
