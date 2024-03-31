package br.com.bodegami.apiauth.api.infra.interceptor;

import br.com.bodegami.apiauth.api.domain.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class AutenticacaoExceptionHandler {

    @ExceptionHandler(value = InvalidTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<StandardError> getInvalidTokenException(
            InvalidTokenException ex, HttpServletRequest request) {
        int statusCode = 403;
        StandardError responseError = createStandardError(ex, request, statusCode);
        return ResponseEntity.status(statusCode).body(responseError);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> getMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        int status = 400;
        StandardErrors responseError = createStandardErrors(ex, request, status);
        return ResponseEntity.status(status).body(responseError);

    }

    private StandardError createStandardError(Exception ex, HttpServletRequest request, int status) {
        return new StandardError(
                Instant.now(),
                status,
                ex.getMessage(),
                request.getRequestURI());
    }

    private StandardErrors createStandardErrors(MethodArgumentNotValidException ex,
                                                HttpServletRequest request, int status) {
        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new FieldError(e.getField(), e.getDefaultMessage()))
                .toList();

        return new StandardErrors(
                Instant.now(),
                status,
                errors,
                request.getRequestURI());
    }

}
