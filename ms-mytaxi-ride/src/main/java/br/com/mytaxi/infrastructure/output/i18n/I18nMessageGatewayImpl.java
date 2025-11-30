package br.com.mytaxi.infrastructure.output.i18n;

import br.com.mytaxi.application.output.i18n.gateway.I18nMessageGateway;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Named
@RequiredArgsConstructor
class I18nMessageGatewayImpl implements I18nMessageGateway {

    private final MessageSource messageSource;

    @Override
    public String get(String message, Object[] arguments, Locale locale) {
        return messageSource.getMessage(message, arguments, locale);
    }
}
