package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class Money {

    private final BigDecimal value;

    public static Candidate<Money> create(String fieldName, BigDecimal value) {
        var candidateBuilder = Candidate.<Money>builder();
        var constraints = Constraints.builder().fieldName(ObjectUtils.defaultIfNull(fieldName, "undefined"));
        if (value == null) {
            constraints.add(fieldName, "must not be null");
        } else if (value.compareTo(BigDecimal.ZERO) < 0) {
            constraints.add(fieldName, "must be greater than zero");
        } else {
            candidateBuilder.value(Money.builder().value(value).build());
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

}
