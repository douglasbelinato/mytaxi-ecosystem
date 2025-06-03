package br.com.mytaxi.application.usecase;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import br.com.mytaxi.application.usecase.dto.UseCaseOutputDTO;

public interface UseCase<I extends UseCaseInputDTO, O extends UseCaseOutputDTO> {

    O execute(I inputDTO);

}
