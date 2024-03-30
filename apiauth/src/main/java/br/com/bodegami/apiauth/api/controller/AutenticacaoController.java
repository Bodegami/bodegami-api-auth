package br.com.bodegami.apiauth.api.controller;

import br.com.bodegami.apiauth.api.domain.model.AuthenticationRequest;
import br.com.bodegami.apiauth.api.domain.service.TokenValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AutenticacaoController {

    @Autowired
    private TokenValidatorService service;

    @GetMapping()
    public ResponseEntity<Boolean> isValid(@RequestBody AuthenticationRequest request) {
        service.validarToken(request.token());

        return ResponseEntity.ok(true);
    }

}
