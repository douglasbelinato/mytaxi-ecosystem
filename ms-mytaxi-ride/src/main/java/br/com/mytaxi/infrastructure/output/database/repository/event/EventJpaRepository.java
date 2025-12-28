package br.com.mytaxi.infrastructure.output.database.repository.event;

import br.com.mytaxi.infrastructure.output.database.entity.event.EventEntity;
import br.com.mytaxi.infrastructure.output.database.entity.event.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, String> {

    List<EventEntity> findAllByStatusIs(EventStatus status);

}
