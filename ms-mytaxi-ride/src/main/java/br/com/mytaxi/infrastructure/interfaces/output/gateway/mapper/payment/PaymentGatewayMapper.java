package br.com.mytaxi.infrastructure.interfaces.output.gateway.mapper.payment;

import br.com.mytaxi.infrastructure.interfaces.output.gateway.dto.payment.PaymentRQ;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentGatewayMapper {

    public static PaymentRQ toPaymentRQ(BigDecimal amount, String creditCardToken) {
        return PaymentRQ.builder().amount(amount).creditCardToken(creditCardToken).build();
    }

}
