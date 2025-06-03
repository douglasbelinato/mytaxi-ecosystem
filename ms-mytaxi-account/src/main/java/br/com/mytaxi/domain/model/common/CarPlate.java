package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class CarPlate {

    private static final Pattern OLD_PATTERN = Pattern.compile("^[A-Z]{3}-?\\d{4}$");
    private static final Pattern MERCOSUL_PATTERN = Pattern.compile("^[A-Z]{3}\\d[A-Z]\\d{2}$");

    private final String value;

    public static Candidate<CarPlate> create(String value) {
        var fieldName = "carPlate";
        var candidateBuilder = Candidate.<CarPlate>builder();
        var constraints = Constraints.builder().fieldName(fieldName);
        if (!isValid(value)) {
            constraints.add(fieldName, "invalid car plate");
        } else {
            candidateBuilder.value(CarPlate.builder().value(value).build());
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

    private static boolean isValid(String value) {
        if (value == null) return false;
        var cleanedValue = value.replaceAll("-", "");
        return OLD_PATTERN.matcher(cleanedValue).matches() || MERCOSUL_PATTERN.matcher(cleanedValue).matches();
    }

}
