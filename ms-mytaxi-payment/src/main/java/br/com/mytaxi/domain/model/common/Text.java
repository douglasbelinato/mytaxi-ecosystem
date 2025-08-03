package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public class Text {

    private final String value;

    public static Candidate<Text> create(String fieldName, String value, int minSize, int maxSize) {
        var finalFieldName = ObjectUtils.defaultIfNull(fieldName, "undefined");
        var candidateBuilder = Candidate.<Text>builder();
        var constraints = Constraints.builder().fieldName(finalFieldName);
        if (value == null) {
            constraints.add(fieldName, "must not be null");
        } else {
            if (value.length() < minSize || value.length() > maxSize) {
                constraints.add(fieldName,
                        "size invalid. It must be between " + minSize + " and " + maxSize);
            } else {
                candidateBuilder.value(Text.builder().value(value).build());
            }
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

}
