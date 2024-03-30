package br.com.bodegami.apiauth.api.domain.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String msg) {
        super(msg);
    }

}
