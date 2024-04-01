package br.com.bodegami.apiauth.api.controller.impl;

import br.com.bodegami.apiauth.api.controller.AutenticacaoControllerAbstract;
import br.com.bodegami.apiauth.api.domain.model.AuthenticationRequest;
import br.com.bodegami.apiauth.api.domain.model.AuthenticationResponse;
import br.com.bodegami.apiauth.api.domain.service.TokenValidatorService;
import br.com.bodegami.apiauth.api.infra.interceptor.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AutenticacaoController extends AutenticacaoControllerAbstract {

    @Autowired
    private TokenValidatorService service;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> isValid(@RequestBody @Valid AuthenticationRequest request) {
        service.validarToken(request.token());

        return ResponseEntity.ok(new AuthenticationResponse(true));
    }

}
