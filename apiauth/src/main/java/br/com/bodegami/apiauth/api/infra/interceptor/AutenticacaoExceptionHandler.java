package br.com.bodegami.apiauth.api.infra.interceptor;

import br.com.bodegami.apiauth.api.domain.exception.GenerateTokenFailException;
import br.com.bodegami.apiauth.api.domain.exception.InvalidTokenException;
import br.com.bodegami.apiauth.api.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class AutenticacaoExceptionHandler {

    @ExceptionHandler(value = InvalidTokenException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseEntity<StandardError> getInvalidTokenException(
            InvalidTokenException ex, HttpServletRequest request) {
        StandardError responseError = createStandardError(ex, request, FORBIDDEN.value());
        return ResponseEntity.status(FORBIDDEN).body(responseError);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<StandardErrors> getMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        StandardErrors responseError = createStandardErrors(ex, request, BAD_REQUEST.value());
        return ResponseEntity.status(BAD_REQUEST).body(responseError);

    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<StandardError> getResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        StandardError responseError = createStandardError(ex, request, UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(responseError);
    }

    @ExceptionHandler(value = GenerateTokenFailException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<StandardError> getGenerateTokenFailException(
            GenerateTokenFailException ex, HttpServletRequest request) {
        StandardError responseError = createStandardError(ex, request, UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(responseError);
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
