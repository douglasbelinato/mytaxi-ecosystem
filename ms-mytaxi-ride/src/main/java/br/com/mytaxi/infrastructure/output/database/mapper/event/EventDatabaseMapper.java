package br.com.mytaxi.infrastructure.output.database.mapper.event;

import br.com.mytaxi.domain.model.event.Event;
import br.com.mytaxi.infrastructure.output.database.entity.event.EventEntity;
import br.com.mytaxi.infrastructure.output.database.entity.event.EventStatus;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
public class EventDatabaseMapper {

    public List<EventEntity> toEntityList(List<Event> event) {
        return event.stream().map(this::toEntity).toList();
    }

    public EventEntity toEntity(Event event) {
        return EventEntity.builder()
                .id(event.getId())
                .aggregateId(event.getAggregateId())
                .type(event.getType())
                .status(EventStatus.PENDING)
                .payload(event.getPayload())
                .retryCount(event.getRetryCount())
                .build();
    }

    public List<Event> toDomainList(List<EventEntity> entityList) {
        return entityList.stream().map(this::toDomainList).toList();
    }

    public Event toDomainList(EventEntity entity) {
        return Event.of(entity.getId(), entity.getAggregateId(), entity.getType(), entity.getStatus(),
                entity.getPayload(), entity.getRetryCount());
    }

}
