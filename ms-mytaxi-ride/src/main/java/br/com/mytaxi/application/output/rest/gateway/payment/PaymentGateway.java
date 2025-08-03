package br.com.mytaxi.application.output.rest.gateway.payment;

import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.common.Money;

public interface PaymentGateway {

    void process(Id rideId, Money amount, String creditCardToken);

}
