package br.com.mytaxi.application.usecase.feature.produceevent;

import br.com.mytaxi.application.output.topic.gateway.PublishEventGateway;
import br.com.mytaxi.domain.model.event.Event;
import br.com.mytaxi.domain.repository.event.EventRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

@Named
@RequiredArgsConstructor
class PublishEventUseCaseImpl implements PublishEventUseCase {

    private final EventRepository eventRepository;
    private final PublishEventGateway publishEventGateway;

    @Transactional
    @Override
    public void execute() {
        var events = eventRepository.listAllPending();
        if (CollectionUtils.isNotEmpty(events)) {
            events.forEach(Event::publish);
            publishEventGateway.publish(events);
            eventRepository.saveAll(events);
        }
    }
}
