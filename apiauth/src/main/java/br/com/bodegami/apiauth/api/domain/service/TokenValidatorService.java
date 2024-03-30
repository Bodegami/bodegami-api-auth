package br.com.bodegami.apiauth.api.domain.service;

import br.com.bodegami.apiauth.api.domain.UsuarioRepository;
import br.com.bodegami.apiauth.api.domain.exception.InvalidTokenException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
public class TokenValidatorService {

    @Autowired
    private UsuarioRepository repository;

    public void validarToken(String token) {
        try {
            String[] parts = token.split("\\.");
            Map<String, Object> mapPayload = getPayload(parts[1]);

            if (isTokenExpired(mapPayload.get("exp").toString())) {
                throw new InvalidTokenException("Token expirado!");
            }

            repository.findByLogin(mapPayload.get("sub").toString());
        }
        catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
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
