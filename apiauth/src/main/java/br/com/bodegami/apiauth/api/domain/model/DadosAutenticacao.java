package br.com.bodegami.apiauth.api.domain.model;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
