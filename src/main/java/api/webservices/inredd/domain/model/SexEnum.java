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

    @com.fasterxml.jackson.annotation.JsonCreator
    public static SexEnum fromValue(String value) {
        for (SexEnum sex : SexEnum.values()) {
            if (sex.value.equalsIgnoreCase(value) || sex.name().equalsIgnoreCase(value)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Unknown value for SexEnum: " + value);
    }
}