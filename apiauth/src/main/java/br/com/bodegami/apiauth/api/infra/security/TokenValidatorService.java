package br.com.bodegami.apiauth.api.infra.security;

import br.com.bodegami.apiauth.api.domain.Usuario;
import br.com.bodegami.apiauth.api.domain.UsuarioRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Map;

@Service
public class TokenValidatorService {

    @Autowired
    private UsuarioRepository repository;

    public void validarToken(String token) {
        try {
            String[] parts = token.split("\\.");
            Map<String, Object> mapHeader = getHeader(parts[0]);
            Map<String, Object> mapPayload = getPayload(parts[1]);

            if (isTokenExpired(mapPayload. get("exp").toString())) {
                //TODO retornar exception
            }

            UserDetails usuario = repository.findByLogin(mapPayload.get("sub").toString());
            System.out.println((Usuario) usuario);


            String signature = decode(parts[2]);
            System.out.println(mapHeader);
            System.out.println(mapPayload);
        }
        catch (Exception e) {
            //TODO retornar exception
        }



    }


    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private Map<String, Object> getHeader(String headerToken) {
        JSONObject headerJson = new JSONObject(decode(headerToken));
        return headerJson.toMap();
    }

    private Map<String, Object> getPayload(String payloadToken) {
        JSONObject headerJson = new JSONObject(decode(payloadToken));
        return headerJson.toMap();
    }

    private boolean isTokenExpired(String expiration) {
        long exp = Long.parseLong(expiration);
        return exp <= (System.currentTimeMillis() / 1000);
    }

}
