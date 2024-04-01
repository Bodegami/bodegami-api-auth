package br.com.bodegami.apiauth.api.domain.model;

public record CadastroUsuarioResponse(
        Long id,
        String login,
        String senha
) {

}
