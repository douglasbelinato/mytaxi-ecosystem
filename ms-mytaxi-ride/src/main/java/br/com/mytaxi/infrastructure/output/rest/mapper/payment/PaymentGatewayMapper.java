package br.com.mytaxi.infrastructure.output.rest.mapper.payment;

import br.com.mytaxi.infrastructure.output.rest.dto.payment.PaymentRQ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper
public interface PaymentGatewayMapper {

    @Mapping(target = "rideId", source = "rideId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "creditCardToken", source = "creditCardToken")
    PaymentRQ toPaymentRQ(String rideId, BigDecimal amount, String creditCardToken);

}