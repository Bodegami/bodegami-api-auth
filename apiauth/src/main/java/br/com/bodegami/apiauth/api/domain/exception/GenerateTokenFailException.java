package br.com.bodegami.apiauth.api.domain.exception;

public class GenerateTokenFailException extends RuntimeException {
    public GenerateTokenFailException(String msg) {
        super(msg);
    }
}
