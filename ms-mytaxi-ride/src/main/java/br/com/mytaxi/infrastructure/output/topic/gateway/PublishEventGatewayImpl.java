package br.com.mytaxi.infrastructure.output.topic.gateway;

import br.com.mytaxi.application.output.topic.gateway.PublishEventGateway;
import br.com.mytaxi.domain.model.event.Event;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@Slf4j
@Named
@RequiredArgsConstructor
class PublishEventGatewayImpl implements PublishEventGateway {

    private static final String BINDING_NAME = "rideEvents-out-0";
    private static final String AGGREGATE_ID_HEADER = "aggregateId";

    private final StreamBridge streamBridge;

    @Override
    public void publish(List<Event> events) {
        events.forEach(this::publishEvent);
    }

    private void publishEvent(Event event) {
        try {
            var message = MessageBuilder.withPayload(event.getPayload())
                    .setHeader(AGGREGATE_ID_HEADER, event.getAggregateId())
                    .build();
            streamBridge.send(BINDING_NAME, message);
            log.info("Event published successfully: eventId={}, aggregateId={}, type={}",
                    event.getId(), event.getAggregateId(), event.getType());
        } catch (Exception e) {
            log.error("Error publishing event: eventId={}, aggregateId={}, type={}",
                    event.getId(), event.getAggregateId(), event.getType(), e);
            throw new RuntimeException("Failed to publish event", e);
        }
    }

}
