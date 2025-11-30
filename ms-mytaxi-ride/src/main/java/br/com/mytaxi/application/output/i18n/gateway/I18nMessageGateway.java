package br.com.mytaxi.application.output.i18n.gateway;

import java.util.Locale;

public interface I18nMessageGateway {

    String get(String message, Object[] arguments, Locale locale);

}
