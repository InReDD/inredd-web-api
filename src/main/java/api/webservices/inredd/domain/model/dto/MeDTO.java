package api.webservices.inredd.domain.model.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeDTO {
    private String displayName;
    private String firstName;
    private String lastName;
    private String imageBase64;

    // Dados de acesso às soluções
    private boolean userHasAccessToD2L;
    private String  accessToD2LSince;
    private boolean userHasAccessToOpenData;
    private String  accessToOpenDataSince;

    // Lista simples de nomes de permissões (sem prefixo "ROLE_")
    private List<String> permissionNames;

    public MeDTO(
      String displayName,
      String firstName,
      String lastName,
      String imageBase64,
      boolean userHasAccessToD2L,
      String  accessToD2LSince,
      boolean userHasAccessToOpenData,
      String  accessToOpenDataSince,
      Set<String> perms // ex: ["ROLE_LIST_MEMBER", "ROLE_EDIT_GROUP", ...]
    ) {
      this.displayName             = displayName;
      this.firstName               = firstName;
      this.lastName                = lastName;
      this.imageBase64             = imageBase64;

      this.userHasAccessToD2L      = userHasAccessToD2L;
      this.accessToD2LSince        = accessToD2LSince;
      this.userHasAccessToOpenData = userHasAccessToOpenData;
      this.accessToOpenDataSince   = accessToOpenDataSince;

      // Monta a lista de permissões "limpas": tira o prefixo "ROLE_"
      this.permissionNames = perms.stream()
                                  .filter(p -> p.startsWith("ROLE_"))
                                  .map(p -> p.substring("ROLE_".length()))
                                  .collect(Collectors.toList());
    }

    // --- getters / setters ---

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<String> getPermissionNames() {
        return permissionNames;
    }
    public void setPermissionNames(List<String> permissionNames) {
        this.permissionNames = permissionNames;
    }
}
