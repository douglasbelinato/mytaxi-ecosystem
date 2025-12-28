package br.com.mytaxi.application.output.topic.gateway;

import br.com.mytaxi.domain.model.event.Event;

import java.util.List;

public interface PublishEventGateway {

    void publish(List<Event> events);

}
