package br.com.mytaxi.domain.exception;

import br.com.mytaxi.domain.model.common.Constraints;
import lombok.Getter;

import java.util.List;

@Getter
public class DomainException extends RuntimeException {

    private static final String MESSAGE = "validation.domain.exception.message";
    private final List<String> constraints;

    public DomainException(String constraint) {
        super(MESSAGE);
        this.constraints = List.of(constraint);
    }

    public DomainException(Constraints constraints) {
        super(MESSAGE);
        this.constraints = constraints.listAll();
    }

}
