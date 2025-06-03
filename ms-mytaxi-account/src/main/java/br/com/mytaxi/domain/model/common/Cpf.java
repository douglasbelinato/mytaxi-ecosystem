package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class Cpf {

    private final String value;

    public static Candidate<Cpf> create(String value) {
        var fieldName = "cpf";
        var candidateBuilder = Candidate.<Cpf>builder();
        var constraints = Constraints.builder().fieldName(fieldName);
        if (!isValid(value)) {
            constraints.add(fieldName, "invalid cpf");
        } else {
            candidateBuilder.value(Cpf.builder().value(value).build());
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

    private static boolean isValid(String value) {
        if (value == null) return false;
        var digits = value.replaceAll("\\D", "");
        if (digits.length() != 11 || digits.matches("(\\d)\\1{10}")) return false;
        try {
            int d1 = calculateDigit(digits, 10);
            int d2 = calculateDigit(digits + d1, 11);
            return digits.equals(digits.substring(0, 9) + d1 + d2);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int calculateDigit(String digits, int weight) {
        var sum = 0;
        for (var i = 0; i < digits.length() && weight > 1; i++, weight--) {
            sum += Character.getNumericValue(digits.charAt(i)) * weight;
        }
        int mod = sum % 11;
        return (mod < 2) ? 0 : 11 - mod;
    }

    public String getFormatted() {
        return value.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

}
