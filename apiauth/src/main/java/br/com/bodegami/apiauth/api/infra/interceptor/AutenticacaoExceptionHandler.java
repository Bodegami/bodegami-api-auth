package br.com.bodegami.apiauth.api.infra.interceptor;

import br.com.bodegami.apiauth.api.domain.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class AutenticacaoExceptionHandler {

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<?> getInvalidTokenException(InvalidTokenException ex, HttpServletRequest request) {
        int statusCode = 403;
        StandardError responseError = createStandardError(ex, request, statusCode);
        return ResponseEntity.status(statusCode).body(responseError);
    }

    private StandardError createStandardError(Exception ex, HttpServletRequest request, int status) {
        return new StandardError(
                Instant.now(),
                status,
                ex.getMessage(),
                request.getRequestURI());
    }

}
