package br.com.bodegami.apiauth.api.controller;

import br.com.bodegami.apiauth.api.domain.model.DadosAutenticacao;
import br.com.bodegami.apiauth.api.domain.model.DadosTokenJWT;
import br.com.bodegami.apiauth.api.infra.interceptor.StandardError;
import br.com.bodegami.apiauth.api.infra.interceptor.StandardErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public abstract class LoginControllerAbstract {

    @Operation(summary = "Generate a Bearer Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "True",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DadosTokenJWT.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrors.class)) }),
            @ApiResponse(responseCode = "422", description = "Internal Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardError.class)) }) })
    public abstract ResponseEntity<DadosTokenJWT> efetuarLogin(DadosAutenticacao dados);

}
