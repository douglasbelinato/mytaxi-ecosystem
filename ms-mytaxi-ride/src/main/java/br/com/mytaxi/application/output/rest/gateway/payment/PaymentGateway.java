package br.com.mytaxi.application.output.rest.gateway.payment;

import br.com.mytaxi.domain.model.common.Money;

public interface PaymentGateway {

    void process(Money amount, String creditCardToken);

}
