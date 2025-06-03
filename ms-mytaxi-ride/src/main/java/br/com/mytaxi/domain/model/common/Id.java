package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class Id {

    private final String value;

    public static Candidate<Id> create() {
        return Candidate.<Id>builder()
                .value(Id.builder().value(UUID.randomUUID().toString()).build())
                .build();
    }

    public static Candidate<Id> of(String fieldName, String value) {
        var candidateBuilder = Candidate.<Id>builder();
        try {
            candidateBuilder.value(Id.builder().value(UUID.fromString(value).toString()).build());
        } catch (RuntimeException e) {
            candidateBuilder.constraints(Constraints.builder().add(fieldName, "invalid id").build());
        }
        return candidateBuilder.build();
    }

}
