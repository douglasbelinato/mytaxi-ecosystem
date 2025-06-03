package br.com.mytaxi.domain.model.common;

import br.com.mytaxi.domain.exception.DomainException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public final class Candidate<T> {

    private final T value;
    private final Constraints constraints;

    @Builder
    private Candidate(T value, Constraints constraints) {
        this.value = value;
        if (constraints != null) {
            this.constraints = constraints;
        } else {
            this.constraints = Constraints.builder().build();
        }
    }

    public boolean isNotValid() {
        return !isValid();
    }

    public boolean isValid() {
        return !constraints.exist();
    }

    public T getValue() {
        if (isNotValid()) {
            throw new DomainException(constraints);
        }
        return value;
    }

}
