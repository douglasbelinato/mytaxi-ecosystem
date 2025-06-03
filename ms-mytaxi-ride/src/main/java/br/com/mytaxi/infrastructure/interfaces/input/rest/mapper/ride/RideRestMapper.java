package br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.ride;

import br.com.mytaxi.application.usecase.dto.acceptride.AcceptRideInputDTO;
import br.com.mytaxi.application.usecase.dto.completeride.CompleteRideInputDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideInputDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideOutputDTO;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideInputDTO;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideOutputDTO;
import br.com.mytaxi.application.usecase.dto.startride.StartRideInputDTO;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.acceptride.AcceptRideRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.requestride.RequestRideRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.requestride.RequestRideRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.searchride.RideRS;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideRestMapper {

    public static RequestRideInputDTO toRequestRideInputDTO(RequestRideRQ request) {
        return RequestRideInputDTO.builder()
                .passengerId(request.passengerId())
                .latitudeFrom(request.latitudeFrom())
                .longitudeFrom(request.longitudeFrom())
                .latitudeTo(request.latitudeTo())
                .longitudeTo(request.longitudeTo())
                .build();
    }

    public static RequestRideRS toRequestRideRS(RequestRideOutputDTO dto) {
        return RequestRideRS.builder().id(dto.id()).build();
    }

    public static SearchRideInputDTO toSearchRideInputDTO(String id) {
        return SearchRideInputDTO.builder().id(id).build();
    }

    public static RideRS toSearchRideRS(SearchRideOutputDTO dto) {
        return RideRS.builder()
                .id(dto.id())
                .passengerId(dto.passengerId())
                .driverId(dto.driverId())
                .status(dto.status())
                .fare(dto.fare())
                .distance(dto.distance())
                .latitudeFrom(dto.latitudeFrom())
                .longitudeFrom(dto.longitudeFrom())
                .latitudeTo(dto.latitudeTo())
                .longitudeTo(dto.longitudeTo())
                .build();
    }

    public static AcceptRideInputDTO toAcceptRideInputDTO(String id, AcceptRideRQ request) {
        return AcceptRideInputDTO.builder().id(id).driverId(request.driverId()).build();
    }

    public static StartRideInputDTO toStartRideInputDTO(String id) {
        return StartRideInputDTO.builder().id(id).build();
    }

    public static CompleteRideInputDTO toCompleteRideInputDTO(String id) {
        return CompleteRideInputDTO.builder().id(id).build();
    }

}
