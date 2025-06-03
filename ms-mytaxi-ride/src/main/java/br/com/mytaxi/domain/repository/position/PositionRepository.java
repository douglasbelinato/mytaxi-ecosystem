package br.com.mytaxi.domain.repository.position;

import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.position.Position;

import java.util.List;

public interface PositionRepository {

    void save(Position position);

    List<Position> findAllByRideId(Id id);

}
