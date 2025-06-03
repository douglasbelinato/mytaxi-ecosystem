package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class Name {

    private final String value;

    public static Candidate<Name> create(String fieldName, String value, int minSize, int maxSize) {
        var finalFieldName = ObjectUtils.defaultIfNull(fieldName, "undefined");
        var candidateBuilder = Candidate.<Name>builder();
        var constraints = Constraints.builder().fieldName(finalFieldName);
        if (StringUtils.isBlank(value)) {
            constraints.add(finalFieldName, "must not be null/empty");
        } else if (!value.matches("^[A-Za-zÀ-ÿ ]+$")) {
            constraints.add(finalFieldName,
                    "must have only letters");
        } else {
            if (value.length() < minSize || value.length() > maxSize) {
                constraints.add(finalFieldName,
                        "size invalid. It must be between " + minSize + " and " + maxSize);
            } else {
                candidateBuilder.value(Name.builder().value(value).build());
            }
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

}
