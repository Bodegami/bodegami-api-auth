package br.com.bodegami.apiauth.api.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroUsuarioRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 5, max = 50)
        String senha
) {
}
