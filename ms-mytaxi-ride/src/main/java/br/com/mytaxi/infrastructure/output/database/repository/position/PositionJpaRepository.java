package br.com.mytaxi.infrastructure.output.database.repository.position;

import br.com.mytaxi.infrastructure.output.database.entity.position.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionJpaRepository extends JpaRepository<PositionEntity, String> {

    List<PositionEntity> findAllByRideId(String rideId);

}
