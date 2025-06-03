package br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.position;

import br.com.mytaxi.application.usecase.dto.registerposition.RegisterPositionInputDTO;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.registerposition.RegisterPositionRQ;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionRestMapper {

    public static RegisterPositionInputDTO toRegisterPositionInputDTO(String rideId, RegisterPositionRQ request) {
        return RegisterPositionInputDTO.builder()
                .rideId(rideId)
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();
    }

}
