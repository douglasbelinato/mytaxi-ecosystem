package br.com.mytaxi.infrastructure.interfaces.output.database.repository.position;

import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.position.Position;
import br.com.mytaxi.domain.repository.position.PositionRepository;
import br.com.mytaxi.infrastructure.interfaces.output.database.mapper.position.PositionDatabaseMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
class PositionRepositoryImpl implements PositionRepository {

    private final PositionJpaRepository positionJpaRepository;

    @Override
    public void save(Position position) {
        positionJpaRepository.save(PositionDatabaseMapper.toEntity(position));
    }

    @Override
    public List<Position> findAllByRideId(Id id) {
        return PositionDatabaseMapper.toDomainList(positionJpaRepository.findAllByRideId(id.getValue()));
    }

}
