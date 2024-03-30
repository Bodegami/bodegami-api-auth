package br.com.bodegami.apiauth.api.domain.service;

import br.com.bodegami.apiauth.api.domain.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Base64;
import java.util.Map;


@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private TokenValidatorService validatorService;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API-AUTH")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", usuario.getId())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }
    }

    public void validarToken(String token) {
        validatorService.validarToken(token);
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
