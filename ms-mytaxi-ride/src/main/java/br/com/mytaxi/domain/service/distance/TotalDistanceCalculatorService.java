package br.com.mytaxi.domain.service.distance;

import br.com.mytaxi.domain.model.common.Distance;
import br.com.mytaxi.domain.model.position.Position;

import java.util.List;

public interface TotalDistanceCalculatorService {

    Distance execute(List<Position> positions);

}
