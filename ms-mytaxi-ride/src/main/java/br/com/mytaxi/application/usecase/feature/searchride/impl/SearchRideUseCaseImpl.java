package br.com.mytaxi.application.usecase.feature.searchride.impl;

import br.com.mytaxi.application.mapper.ride.RideApplicationMapper;
import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.application.output.rest.gateway.account.SearchAccountGateway;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideInputDTO;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideOutputDTO;
import br.com.mytaxi.application.usecase.feature.searchride.SearchRideUseCase;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class SearchRideUseCaseImpl implements SearchRideUseCase {

    private final RideRepository rideRepository;
    private final SearchAccountGateway searchAccountGateway;

    @Override
    public SearchRideOutputDTO execute(SearchRideInputDTO inputDTO) {
        var id = Id.of("id", inputDTO.id()).getValue();
        var ride = rideRepository.findByIdOrThrow(id);
        var passengerAccount = searchAccountGateway.search(ride.getPassengerId());
        AccountDTO driverAccount = null;
        if (ride.getDriverId() != null) {
            driverAccount = searchAccountGateway.search(ride.getDriverId());
        }
        return RideApplicationMapper.toSearchRideOutputDTO(ride, passengerAccount, driverAccount);
    }
}
