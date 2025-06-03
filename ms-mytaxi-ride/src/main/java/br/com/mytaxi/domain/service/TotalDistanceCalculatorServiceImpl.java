package br.com.mytaxi.domain.service;

import br.com.mytaxi.domain.model.common.Distance;
import br.com.mytaxi.domain.model.position.Position;
import br.com.mytaxi.domain.service.distance.TotalDistanceCalculatorService;
import jakarta.inject.Named;

import java.util.List;

@Named
class TotalDistanceCalculatorServiceImpl implements TotalDistanceCalculatorService {

    @Override
    public Distance execute(List<Position> positions) {
        if (positions == null || positions.size() < 2) {
            return Distance.of(0d).getValue();
        }
        var total = 0.0d;
        for (var i = 0; i < positions.size() - 1; i++) {
            var currentPosition = positions.get(i);
            var nextPosition = positions.get(i + 1);
            total += Distance.create(
                    currentPosition.getCoordinate(), nextPosition.getCoordinate()).getValue().getValue();
        }
        return Distance.of(total).getValue();
    }

}
