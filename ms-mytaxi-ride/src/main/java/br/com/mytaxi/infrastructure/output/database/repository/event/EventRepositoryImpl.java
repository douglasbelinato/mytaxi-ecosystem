package br.com.mytaxi.infrastructure.output.database.repository.event;

import br.com.mytaxi.domain.model.event.Event;
import br.com.mytaxi.domain.repository.event.EventRepository;
import br.com.mytaxi.infrastructure.output.database.entity.event.EventStatus;
import br.com.mytaxi.infrastructure.output.database.mapper.event.EventDatabaseMapper;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;

import java.util.List;

@Named
@AllArgsConstructor
class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final EventDatabaseMapper eventDatabaseMapper;

    @Override
    public void save(Event event) {
        eventJpaRepository.save(eventDatabaseMapper.toEntity(event));
    }

    @Override
    public void saveAll(List<Event> events) {
        eventJpaRepository.saveAll(eventDatabaseMapper.toEntityList(events));
    }

    @Override
    public List<Event> listAllPending() {
        return eventDatabaseMapper.toDomainList(eventJpaRepository.findAllByStatusIs(EventStatus.PENDING));
    }
}
