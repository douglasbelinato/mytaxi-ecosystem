package br.com.mytaxi.infrastructure.interfaces.output.gateway.payment;

import br.com.mytaxi.application.output.rest.gateway.payment.PaymentGateway;
import br.com.mytaxi.domain.model.common.Money;
import br.com.mytaxi.infrastructure.interfaces.output.gateway.mapper.payment.PaymentGatewayMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentClient paymentClient;

    @Override
    public void process(Money amount, String creditCardToken) {
        paymentClient.execute(PaymentGatewayMapper.toPaymentRQ(amount.getValue(), creditCardToken));
    }
}
