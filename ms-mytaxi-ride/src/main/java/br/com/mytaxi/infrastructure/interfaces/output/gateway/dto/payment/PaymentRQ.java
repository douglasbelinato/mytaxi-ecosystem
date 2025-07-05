package br.com.mytaxi.infrastructure.interfaces.output.gateway.dto.payment;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRQ(BigDecimal amount, String creditCardToken) {
}
