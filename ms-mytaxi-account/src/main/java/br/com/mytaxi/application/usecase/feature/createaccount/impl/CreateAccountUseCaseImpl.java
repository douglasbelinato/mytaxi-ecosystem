package br.com.mytaxi.application.usecase.feature.createaccount.impl;

import br.com.mytaxi.application.mapper.account.AccountApplicationMapper;
import br.com.mytaxi.application.usecase.feature.createaccount.CreateAccountUseCase;
import br.com.mytaxi.application.usecase.dto.createaccount.AccountCreatedDTO;
import br.com.mytaxi.application.usecase.dto.createaccount.CreateAccountDTO;
import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.repository.account.AccountRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountRepository accountRepository;

    @Override
    public AccountCreatedDTO execute(CreateAccountDTO dto) {
        var candidate = AccountApplicationMapper.toDomain(dto);
        candidate.validate();
        var account = candidate.getValue();
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new DomainException("validation.account.email.already.used");
        }
        account = accountRepository.save(account);
        return AccountApplicationMapper.toAccountCreatedDTO(account.getId());
    }
}
