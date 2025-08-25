package com.first.challenge.api;

import com.first.challenge.model.user.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String,Object>> handleBusinessException(BusinessException ex, ServerWebExchange exchange) {
        Map<String, Object> body = Map.of(
                "timestamp", OffsetDateTime.now(),
                "path", exchange.getRequest().getPath().toString(),
                "status", 400,
                "error", "Bad Request",
                "requestId", UUID.randomUUID().toString(),
                "errorCode", ex.getCode(),
                "message", ex.getMessage()
        );
        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGenericException(Exception ex, ServerWebExchange exchange) {
        Map<String, Object> body = Map.of(
                "timestamp", OffsetDateTime.now(),
                "path", exchange.getRequest().getPath().toString(),
                "status", 500,
                "error", "Internal Server Error",
                "requestId", UUID.randomUUID().toString()
        );
        return ResponseEntity.status(500).body(body);
    }
}
