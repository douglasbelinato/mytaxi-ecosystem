package br.com.mytaxi.application.usecase;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;

public interface NoOutputUseCase<I extends UseCaseInputDTO> {

    void execute(I inputDTO);

}
