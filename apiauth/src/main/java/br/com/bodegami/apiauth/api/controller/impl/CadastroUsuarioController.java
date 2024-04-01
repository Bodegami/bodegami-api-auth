package br.com.bodegami.apiauth.api.controller.impl;

import br.com.bodegami.apiauth.api.controller.CadastroUsuarioControllerAbstract;
import br.com.bodegami.apiauth.api.domain.model.CadastroUsuarioRequest;
import br.com.bodegami.apiauth.api.domain.model.CadastroUsuarioResponse;
import br.com.bodegami.apiauth.api.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class CadastroUsuarioController extends CadastroUsuarioControllerAbstract {

    @Autowired
    private CadastroUsuarioService service;
    @Override
    @PostMapping
    public ResponseEntity<CadastroUsuarioResponse> cadastrarUsuario(
            @RequestBody @Valid CadastroUsuarioRequest request) {
        CadastroUsuarioResponse response = service.cadastrarUsuario(request);
        URI uri = UriComponentsBuilder.fromPath(response.id().toString()).buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
