package api.webservices.inredd.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SexEnum {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro");

    private final String value;

    SexEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}