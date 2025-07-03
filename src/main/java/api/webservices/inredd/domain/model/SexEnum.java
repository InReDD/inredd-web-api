package api.webservices.inredd.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SexEnum {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String value;

    SexEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}