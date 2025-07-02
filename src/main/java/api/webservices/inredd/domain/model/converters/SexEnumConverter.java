package api.webservices.inredd.domain.model.converters;

import api.webservices.inredd.domain.model.SexEnum;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class SexEnumConverter implements AttributeConverter<SexEnum, String> {

    @Override
    public String convertToDatabaseColumn(SexEnum sexEnum) {
        if (sexEnum == null) {
            return null;
        }
        return sexEnum.getValue();
    }

    @Override
    public SexEnum convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(SexEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}