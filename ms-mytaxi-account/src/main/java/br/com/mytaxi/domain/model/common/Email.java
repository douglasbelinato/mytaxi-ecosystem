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
public final class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE
    );

    private final String value;

    public static Candidate<Email> create(String value, int minSize, int maxSize) {
        var fieldName = "email";
        var candidateBuilder = Candidate.<Email>builder();
        var constraints = Constraints.builder().fieldName(fieldName);
        if (StringUtils.isBlank(value)) {
            constraints.add(fieldName, "must not be null/empty");
        } else if (!EMAIL_PATTERN.matcher(value).matches()) {
            constraints.add(fieldName,
                    "invalid email");
        } else {
            if (value.length() < minSize || value.length() > maxSize) {
                constraints.add(fieldName,
                        "size invalid. It must be between " + minSize + " and " + maxSize);
            } else {
                candidateBuilder.value(Email.builder().value(value).build());
            }
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

}
