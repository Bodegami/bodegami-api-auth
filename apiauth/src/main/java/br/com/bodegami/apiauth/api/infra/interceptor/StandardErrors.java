package br.com.bodegami.apiauth.api.infra.interceptor;

import java.time.Instant;
import java.util.List;

public record StandardErrors(
        Instant timestamp,
        Integer status,
        List<FieldError> errors,
        String path
) {
}
