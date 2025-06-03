package br.com.mytaxi.application.mapper.position;

import br.com.mytaxi.application.usecase.dto.registerposition.RegisterPositionInputDTO;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.position.Position;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionApplicationMapper {

    public static Candidate<Position> toDomain(RegisterPositionInputDTO dto) {
        return Position.create(dto.rideId(), dto.latitude(), dto.latitude());
    }

}
