package br.com.mytaxi.infrastructure.interfaces.input.topic.consumer;

import br.com.mytaxi.application.usecase.dto.payride.PayRideInputDTO;
import br.com.mytaxi.application.usecase.feature.payride.PayRideUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.topic.dto.RideCompletedEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RideEventsConsumer {

    private final PayRideUseCase payRideUseCase;

    @Bean
    @Transactional
    public Consumer<Message<RideCompletedEventPayload>> rideEvents() {
        return message -> {
            RideCompletedEventPayload payload = message.getPayload();

            log.info("Processing ride completed event for rideId: {}, amount: {}",
                    payload.rideId(), payload.amount());

            try {
                PayRideInputDTO inputDTO = PayRideInputDTO.builder()
                        .rideId(payload.rideId())
                        .amount(payload.amount())
                        .creditCardToken(payload.creditCardToken())
                        .build();

                payRideUseCase.execute(inputDTO);

                log.info("Ride payment processed successfully for rideId: {}", payload.rideId());
            } catch (Exception e) {
                log.error("Error processing ride completed event for rideId: {}", payload.rideId(), e);
                throw e;
            }
        };
    }
}