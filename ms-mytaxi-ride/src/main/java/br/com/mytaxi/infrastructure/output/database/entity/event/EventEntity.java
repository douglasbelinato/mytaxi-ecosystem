package br.com.mytaxi.infrastructure.output.database.entity.event;

import br.com.mytaxi.domain.model.event.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    private String id;
    private String aggregateId;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    private String payload;
    @Column(name = "retry_count")
    private Integer retryCount;
    @Column(name = "date_processed")
    private LocalDateTime dateProcessed;

}
