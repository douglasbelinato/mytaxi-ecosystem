package br.com.mytaxi.infrastructure.output.rest.client.payment;

import br.com.mytaxi.infrastructure.output.rest.dto.payment.PaymentRQ;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "paymentClient", url = "${integration.payment.host}")
public interface PaymentRestClient {

    @PostMapping(value = "/v1/payments")
    void execute(PaymentRQ request);

}
