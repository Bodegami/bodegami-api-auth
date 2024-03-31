package br.com.bodegami.apiauth.api.domain.service;

import br.com.bodegami.apiauth.api.domain.UsuarioRepository;
import br.com.bodegami.apiauth.api.domain.exception.InvalidTokenException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
public class TokenValidatorService {

    @Autowired
    private UsuarioRepository repository;

    public void validarToken(String token) {
        String[] parts = token.split("\\.");
        Map<String, Object> mapHeaders = getDecodeToken(parts[0]);
        Map<String, Object> mapPayload = getDecodeToken(parts[1]);

        validateHeaderFields(mapHeaders);
        validatePayloadFields(mapPayload);
        isTokenExpired(mapPayload.get("exp").toString());

        validateUserLogin(mapPayload.get("sub").toString());
    }

    private String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private Map<String, Object> getDecodeToken(String token) {
        try {
            JSONObject decodeToken = new JSONObject(decode(token));
            return decodeToken.toMap();
        }
        catch (Exception ex) {
            throw new InvalidTokenException(ex.getMessage());
        }

    }

    private void isTokenExpired(String expiration) {
        long exp = Long.parseLong(expiration);

        if (exp <= (System.currentTimeMillis() / 1000)) {
            throw new InvalidTokenException("Token expirado!");
        }
    }

    private void validateHeaderFields(Map<String, Object> header) {
        boolean isValidTyp = header.containsKey("typ") && header.get("typ").equals("JWT");
        boolean isValidAlg = header.containsKey("alg") && header.get("alg").equals("HS256");

        if (!isValidTyp || !isValidAlg) {
            throw new InvalidTokenException("Token Inválido!");
        }
    }

    private void validatePayloadFields(Map<String, Object> payload) {
        boolean isValidSub = payload.containsKey("sub");
        boolean isValidIssuer = payload.containsKey("iss") && payload.get("iss").equals("API-AUTH");
        boolean isValidId = payload.containsKey("id");
        boolean isValidExpiration = payload.containsKey("exp");

        if (!isValidSub || !isValidIssuer || !isValidId || !isValidExpiration) {
            throw new InvalidTokenException("Token Inválido!");
        }
    }

    private void validateUserLogin(String login) {
        try {
            repository.findByLogin(login);
        }
        catch (UsernameNotFoundException usernameNotFoundException) {
            throw new InvalidTokenException("Usuário inválido!");
        }
    }

}
