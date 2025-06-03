package br.com.mytaxi.application.usecase.feature.searchaccount.impl;

import br.com.mytaxi.application.mapper.account.AccountApplicationMapper;
import br.com.mytaxi.application.usecase.dto.searchaccount.AccountDTO;
import br.com.mytaxi.application.usecase.dto.searchaccount.SearchAccountDTO;
import br.com.mytaxi.application.usecase.feature.searchaccount.SearchAccountUseCase;
import br.com.mytaxi.domain.exception.NotFoundException;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.repository.account.AccountRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class SearchAccountUseCaseImpl implements SearchAccountUseCase {

    private final AccountRepository accountRepository;

    @Override
    public AccountDTO execute(SearchAccountDTO inputDTO) {
        var candidate = Id.of(inputDTO.id());
        candidate.validate();
        var optionalAccount = accountRepository.findById(candidate.getValue());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("validation.account.not-found");
        }
        return AccountApplicationMapper.toAccountDTO(optionalAccount.get());
    }
}
