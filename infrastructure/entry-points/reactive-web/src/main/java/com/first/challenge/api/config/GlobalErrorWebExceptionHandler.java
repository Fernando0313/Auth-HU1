package com.first.challenge.api.config;

import com.first.challenge.model.user.exceptions.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

//@Component
//@Order(-2) // Prioridad sobre el default
public class GlobalErrorWebExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof BusinessException) {
            status = HttpStatus.BAD_REQUEST;
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body;
        String path = exchange.getRequest().getPath().value();
        String requestId = UUID.randomUUID().toString();

        if (ex instanceof BusinessException be) {
            body = Map.of(
                    "timestamp", OffsetDateTime.now(),
                    "path", path,
                    "status", status.value(),
                    "error", status.getReasonPhrase(),
                    "requestId", requestId,
                    "errorCode", be.getCode(),
                    "message", be.getMessage()
            );
        } else {
            body = Map.of(
                    "timestamp", OffsetDateTime.now(),
                    "path", path,
                    "status", status.value(),
                    "error", status.getReasonPhrase(),
                    "requestId", requestId
            );
        }

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(body);
        } catch (Exception e) {
            bytes = ("{\"error\":\"Internal Server Error\"}").getBytes();
        }

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer))
                .doOnError(throwable -> {
                    // Si falla, no hacemos nada, WebFlux maneja fallback
                });
    }
}
