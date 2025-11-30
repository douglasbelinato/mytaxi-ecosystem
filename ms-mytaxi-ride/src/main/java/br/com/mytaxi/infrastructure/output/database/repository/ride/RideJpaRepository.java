package br.com.mytaxi.infrastructure.output.database.repository.ride;

import br.com.mytaxi.infrastructure.output.database.entity.ride.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideJpaRepository extends JpaRepository<RideEntity, String> {

    Optional<RideEntity> findByPassengerIdAndStatusNot(String passengerId, String status);

    boolean existsByDriverIdAndStatusIn(String driverId, List<String> statusList);

}
