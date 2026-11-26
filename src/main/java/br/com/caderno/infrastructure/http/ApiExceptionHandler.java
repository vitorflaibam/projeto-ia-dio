package br.com.caderno.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, DateTimeParseException.class})
    public ResponseEntity<ErroResponse> tratarDadosInvalidos(RuntimeException excecao) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse(excecao.getMessage()));
    }
}
