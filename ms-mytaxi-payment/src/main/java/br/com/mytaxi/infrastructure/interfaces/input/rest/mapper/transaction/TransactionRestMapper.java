package br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.transaction;

import br.com.mytaxi.application.usecase.dto.payride.PayRideInputDTO;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.payride.PayRideRQ;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionRestMapper {

    public static PayRideInputDTO toPayRideInputDTO(PayRideRQ request) {
        return PayRideInputDTO.builder()
                .rideId(request.rideId())
                .amount(request.amount())
                .build();
    }

}
