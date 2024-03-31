package br.com.bodegami.apiauth.api.controller;

import br.com.bodegami.apiauth.api.domain.model.AuthenticationRequest;
import br.com.bodegami.apiauth.api.domain.model.AuthenticationResponse;
import br.com.bodegami.apiauth.api.domain.model.DadosTokenJWT;
import br.com.bodegami.apiauth.api.infra.interceptor.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public abstract class AutenticacaoControllerAbstract {

    @Operation(summary = "Validate a Bearer Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "True",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DadosTokenJWT.class)) }),
            @ApiResponse(responseCode = "403", description = "Token Expirado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "403", description = "Token Inválido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }),
            @ApiResponse(responseCode = "422", description = "Usuário inválido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }) })
    public abstract ResponseEntity<AuthenticationResponse> isValid(AuthenticationRequest request);

}
