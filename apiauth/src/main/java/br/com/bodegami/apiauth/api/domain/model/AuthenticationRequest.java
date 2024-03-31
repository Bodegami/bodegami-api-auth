package br.com.bodegami.apiauth.api.domain.model;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank
        String token
) {
}
