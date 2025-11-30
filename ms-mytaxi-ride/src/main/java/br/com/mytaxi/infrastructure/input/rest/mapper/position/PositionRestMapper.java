package br.com.mytaxi.infrastructure.input.rest.mapper.position;

import br.com.mytaxi.application.usecase.dto.registerposition.RegisterPositionInputDTO;
import br.com.mytaxi.infrastructure.input.rest.dto.registerposition.RegisterPositionRQ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PositionRestMapper {

    @Mapping(target = "rideId", source = "rideId")
    @Mapping(target = "latitude", source = "request.latitude")
    @Mapping(target = "longitude", source = "request.longitude")
    RegisterPositionInputDTO toRegisterPositionInputDTO(String rideId, RegisterPositionRQ request);

}