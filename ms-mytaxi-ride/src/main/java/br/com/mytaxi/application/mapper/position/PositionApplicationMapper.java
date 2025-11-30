package br.com.mytaxi.application.mapper.position;

import br.com.mytaxi.application.usecase.dto.registerposition.RegisterPositionInputDTO;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.position.Position;
import org.mapstruct.Mapper;

@Mapper
public interface PositionApplicationMapper {

    default Candidate<Position> toDomain(RegisterPositionInputDTO dto) {
        return Position.create(dto.rideId(), dto.latitude(), dto.longitude());
    }

}
