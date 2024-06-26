package br.com.bodegami.apiauth.api.infra.interceptor;

import java.time.Instant;

public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String path) {
}
