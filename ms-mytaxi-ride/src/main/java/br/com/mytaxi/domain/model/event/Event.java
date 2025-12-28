package br.com.mytaxi.domain.model.event;

import br.com.mytaxi.infrastructure.output.database.entity.event.EventStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Event {

    private static final Integer ZERO = 0;

    private final String id;
    private final String aggregateId;
    private final EventType type;
    private EventStatus status;
    private final String payload;
    private Integer retryCount;
    private LocalDateTime dateProcessed;

    public static Event create(String aggregateId, EventType type, String payload) {
        return Event.builder()
                .id(UUID.randomUUID().toString())
                .aggregateId(aggregateId)
                .type(type)
                .status(EventStatus.PENDING)
                .payload(payload)
                .retryCount(ZERO)
                .build();
    }

    public static Event of(String id, String aggregateId, EventType type, EventStatus status, String payload,
                           Integer retryCount) {
        return Event.builder()
                .id(id)
                .aggregateId(aggregateId)
                .type(type)
                .status(status)
                .payload(payload)
                .retryCount(retryCount)
                .build();
    }

    public void publish() {
        this.status = EventStatus.PUBLISHED;
        this.dateProcessed = LocalDateTime.now();
    }

    public void retry() {
        this.retryCount++;
        this.dateProcessed = LocalDateTime.now();
    }

}
