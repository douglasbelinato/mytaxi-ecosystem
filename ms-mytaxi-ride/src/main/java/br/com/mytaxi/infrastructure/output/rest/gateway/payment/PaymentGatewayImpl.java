package br.com.mytaxi.infrastructure.output.rest.gateway.payment;

import br.com.mytaxi.application.output.rest.gateway.payment.PaymentGateway;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.common.Money;
import br.com.mytaxi.infrastructure.output.rest.client.payment.PaymentRestClient;
import br.com.mytaxi.infrastructure.output.rest.mapper.payment.PaymentGatewayMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentRestClient restClient;
    private final PaymentGatewayMapper paymentGatewayMapper;

    @Override
    public void process(Id rideId, Money amount, String creditCardToken) {
        restClient.execute(paymentGatewayMapper.toPaymentRQ(rideId.getValue(), amount.getValue(), creditCardToken));
    }
}
