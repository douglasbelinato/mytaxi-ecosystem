package br.com.mytaxi.application.usecase.dto.requestride;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record RequestRideInputDTO(String passengerId, Double latitudeFrom, Double longitudeFrom,
                                  Double latitudeTo, Double longitudeTo)
        implements UseCaseInputDTO {
}
