package br.com.mytaxi.domain.model.ride;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RideStatusEnum {

    REQUESTED, ACCEPTED, IN_PROGRESS, COMPLETED;

    private static final Map<String, RideStatusEnum> valuesMapped;

    static {
        valuesMapped = Arrays.stream(RideStatusEnum.values())
                .collect(Collectors.toMap(Enum::name, Function.identity()));
    }

    public static Optional<RideStatusEnum> of(String value) {
        return Optional.ofNullable(valuesMapped.get(value));
    }

}
