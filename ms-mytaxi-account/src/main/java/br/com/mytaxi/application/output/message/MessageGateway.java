package br.com.mytaxi.application.output.message;

import java.util.Locale;

public interface MessageGateway {

    String get(String message, Object[] arguments, Locale locale);

}
