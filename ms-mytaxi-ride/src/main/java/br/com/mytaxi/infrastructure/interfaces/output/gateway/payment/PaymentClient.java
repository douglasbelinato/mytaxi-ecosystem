package br.com.mytaxi.infrastructure.interfaces.output.gateway.payment;

import br.com.mytaxi.infrastructure.interfaces.output.gateway.dto.payment.PaymentRQ;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "paymentClient", url = "${integration.payment.host}")
public interface PaymentClient {

    @PostMapping(value = "/v1/payments")
    void execute(PaymentRQ request);

}
