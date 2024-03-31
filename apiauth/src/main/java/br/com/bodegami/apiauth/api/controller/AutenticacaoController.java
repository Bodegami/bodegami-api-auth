package br.com.bodegami.apiauth.api.controller;

import br.com.bodegami.apiauth.api.domain.model.AuthenticationRequest;
import br.com.bodegami.apiauth.api.domain.model.AuthenticationResponse;
import br.com.bodegami.apiauth.api.domain.service.TokenValidatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AutenticacaoController {

    @Autowired
    private TokenValidatorService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> isValid(@RequestBody @Valid AuthenticationRequest request) {
        service.validarToken(request.token());

        return ResponseEntity.ok(new AuthenticationResponse(true));
    }

}
