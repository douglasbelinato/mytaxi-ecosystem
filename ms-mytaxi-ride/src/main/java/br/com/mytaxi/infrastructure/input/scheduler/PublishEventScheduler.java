package br.com.mytaxi.infrastructure.input.scheduler;

import br.com.mytaxi.application.usecase.feature.produceevent.PublishEventUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublishEventScheduler {

    private final PublishEventUseCase publishEventUseCase;

    @Scheduled(fixedDelay = 10000)
    @SchedulerLock(
            name = "publishPendingEvents",
            lockAtMostFor = "9s",
            lockAtLeastFor = "5s"
    )
    public void publishPendingEvents() {
        log.info("Starting scheduled task to publish pending events");
        try {
            publishEventUseCase.execute();
            log.info("Scheduled task completed successfully");
        } catch (Exception e) {
            log.error("Error executing scheduled task to publish events", e);
        }
    }
}