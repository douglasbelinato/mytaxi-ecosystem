package br.com.mytaxi.infrastructure.input.rest.mapper.ride;

import br.com.mytaxi.application.usecase.dto.acceptride.AcceptRideInputDTO;
import br.com.mytaxi.application.usecase.dto.completeride.CompleteRideInputDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideInputDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideOutputDTO;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideInputDTO;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideOutputDTO;
import br.com.mytaxi.application.usecase.dto.startride.StartRideInputDTO;
import br.com.mytaxi.infrastructure.input.rest.dto.acceptride.AcceptRideRQ;
import br.com.mytaxi.infrastructure.input.rest.dto.completeride.CompleteRideRQ;
import br.com.mytaxi.infrastructure.input.rest.dto.requestride.RequestRideRQ;
import br.com.mytaxi.infrastructure.input.rest.dto.requestride.RequestRideRS;
import br.com.mytaxi.infrastructure.input.rest.dto.searchride.RideRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RideRestMapper {

    RequestRideInputDTO toRequestRideInputDTO(RequestRideRQ request);

    RequestRideRS toRequestRideRS(RequestRideOutputDTO dto);

    @Mapping(target = "id", source = "id")
    SearchRideInputDTO toSearchRideInputDTO(String id);

    RideRS toSearchRideRS(SearchRideOutputDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "driverId", source = "request.driverId")
    AcceptRideInputDTO toAcceptRideInputDTO(String id, AcceptRideRQ request);

    @Mapping(target = "id", source = "id")
    StartRideInputDTO toStartRideInputDTO(String id);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "creditCardToken", source = "request.creditCardToken")
    CompleteRideInputDTO toCompleteRideInputDTO(String id, CompleteRideRQ request);

}