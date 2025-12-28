package br.com.mytaxi.infrastructure.output.topic.gateway;

import br.com.mytaxi.application.output.topic.gateway.PublishEventGateway;
import br.com.mytaxi.domain.model.event.Event;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
class PublishEventGatewayImpl implements PublishEventGateway {

    @Override
    public void publish(List<Event> events) {

    }
}
