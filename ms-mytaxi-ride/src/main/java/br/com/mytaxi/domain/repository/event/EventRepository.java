package br.com.mytaxi.domain.repository.event;

import br.com.mytaxi.domain.model.event.Event;

import java.util.List;

public interface EventRepository {

    void save(Event event);

    void saveAll(List<Event> events);

    List<Event> listAllPending();

}
