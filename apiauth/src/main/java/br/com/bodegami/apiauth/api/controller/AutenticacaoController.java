package br.com.bodegami.apiauth.api.controller;

import br.com.bodegami.apiauth.api.domain.DadosAutenticacao;
import br.com.bodegami.apiauth.api.domain.Usuario;
import br.com.bodegami.apiauth.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> efetuarLogin(@RequestBody DadosAutenticacao dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication authentication = manager.authenticate(token);
        Usuario usuario = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(tokenService.gerarToken(usuario));
    }

}
