package br.com.mytaxi.application.usecase.feature.payride.impl;

import br.com.mytaxi.application.mapper.transaction.TransactionApplicationMapper;
import br.com.mytaxi.application.usecase.dto.payride.PayRideInputDTO;
import br.com.mytaxi.application.usecase.feature.payride.PayRideUseCase;
import br.com.mytaxi.domain.repository.transaction.TransactionRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class PayRideUseCaseImpl implements PayRideUseCase {

    private final TransactionRepository transactionRepository;

    @Override
    public void execute(PayRideInputDTO inputDTO) {
        transactionRepository.save(TransactionApplicationMapper.toDomain(inputDTO).getValue());
    }

}
