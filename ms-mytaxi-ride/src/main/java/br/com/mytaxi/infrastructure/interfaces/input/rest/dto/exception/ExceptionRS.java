package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.exception;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ExceptionRS(LocalDateTime timestamp, String method, String path, int status, String message,
                          List<String> constraints) {
}
