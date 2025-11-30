package br.com.mytaxi.infrastructure.input.rest.exception;

import br.com.mytaxi.application.output.i18n.gateway.I18nMessageGateway;
import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.exception.NotFoundException;
import br.com.mytaxi.infrastructure.input.rest.dto.exception.ExceptionRS;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final I18nMessageGateway i18nMessageGateway;

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionRS> handleDomainException(DomainException e, HttpServletRequest request,
                                                             Locale locale) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, request.getMethod(), request.getRequestURI(),
                e.getMessage(), e.getConstraints(), locale);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionRS> handleNotFoundException(NotFoundException e, HttpServletRequest request,
                                                               Locale locale) {
        return buildResponse(HttpStatus.NOT_FOUND, request.getMethod(), request.getRequestURI(),
                e.getMessage(), null, locale);
    }

    private ResponseEntity<ExceptionRS> buildResponse(HttpStatus status, String method, String path, String message,
                                                      List<String> constraints, Locale locale) {
        var interConstraints = new ArrayList<String>();
        for (var constraint : ObjectUtils.defaultIfNull(constraints, new ArrayList<String>())) {
            interConstraints.add(i18nMessageGateway.get(constraint, null, locale));
        }
        var response = ExceptionRS.builder()
                .timestamp(LocalDateTime.now())
                .method(method)
                .path(path)
                .status(status.value())
                .message(i18nMessageGateway.get(message, null, locale))
                .constraints(interConstraints)
                .build();
        return new ResponseEntity<>(response, status);
    }

}
