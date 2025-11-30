package br.com.mytaxi.infrastructure.output.database.repository.position;

import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.position.Position;
import br.com.mytaxi.domain.repository.position.PositionRepository;
import br.com.mytaxi.infrastructure.output.database.mapper.position.PositionDatabaseMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
class PositionRepositoryImpl implements PositionRepository {

    private final PositionJpaRepository positionJpaRepository;
    private final PositionDatabaseMapper positionDatabaseMapper;

    @Override
    public void save(Position position) {
        positionJpaRepository.save(positionDatabaseMapper.toEntity(position));
    }

    @Override
    public List<Position> findAllByRideId(Id id) {
        return positionDatabaseMapper.toDomainList(positionJpaRepository.findAllByRideId(id.getValue()));
    }

}
