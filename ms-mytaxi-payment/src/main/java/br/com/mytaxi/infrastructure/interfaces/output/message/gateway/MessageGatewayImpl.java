package br.com.mytaxi.infrastructure.interfaces.output.message.gateway;

import br.com.mytaxi.application.output.message.MessageGateway;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Named
@RequiredArgsConstructor
class MessageGatewayImpl implements MessageGateway {

    private final MessageSource messageSource;

    @Override
    public String get(String message, Object[] arguments, Locale locale) {
        return messageSource.getMessage(message, arguments, locale);
    }
}
