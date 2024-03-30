package br.com.bodegami.apiauth.api.domain;

import br.com.bodegami.apiauth.api.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String user);
}
