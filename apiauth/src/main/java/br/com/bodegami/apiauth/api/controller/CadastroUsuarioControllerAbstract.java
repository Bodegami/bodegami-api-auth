package br.com.bodegami.apiauth.api.controller;

import br.com.bodegami.apiauth.api.domain.model.CadastroUsuarioRequest;
import br.com.bodegami.apiauth.api.domain.model.CadastroUsuarioResponse;
import br.com.bodegami.apiauth.api.infra.interceptor.StandardError;
import br.com.bodegami.apiauth.api.infra.interceptor.StandardErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public abstract class CadastroUsuarioControllerAbstract {

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "True",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CadastroUsuarioResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Token Expirado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrors.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }) })
    public abstract ResponseEntity<CadastroUsuarioResponse> cadastrarUsuario(CadastroUsuarioRequest request);

}
