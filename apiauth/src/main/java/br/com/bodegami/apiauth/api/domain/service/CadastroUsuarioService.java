package br.com.bodegami.apiauth.api.domain.service;

import br.com.bodegami.apiauth.api.domain.UsuarioRepository;
import br.com.bodegami.apiauth.api.domain.exception.InternalServerErrorException;
import br.com.bodegami.apiauth.api.domain.model.CadastroUsuarioRequest;
import br.com.bodegami.apiauth.api.domain.model.CadastroUsuarioResponse;
import br.com.bodegami.apiauth.api.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public CadastroUsuarioResponse cadastrarUsuario(CadastroUsuarioRequest request) {
        try {

            String passEncoded = encrypt(request.senha().trim());
            Usuario usuario = repository.save(
                    new Usuario(null, request.email().trim(), passEncoded));

            return new CadastroUsuarioResponse(usuario.getId(), usuario.getUsername(), usuario.getPassword());
        }
        catch (Exception ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }

    }

    private String encrypt(String pass) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pass);
    }

}
