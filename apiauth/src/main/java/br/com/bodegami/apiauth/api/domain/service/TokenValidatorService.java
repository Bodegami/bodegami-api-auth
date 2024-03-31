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
            Map<String, Object> mapHeaders = getDecodeToken(parts[0]);
            Map<String, Object> mapPayload = getDecodeToken(parts[1]);

            if (validatePayloadFields(mapPayload) && validateHeaderFields(mapHeaders)) {
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

    private Map<String, Object> getDecodeToken(String headerToken) {
        JSONObject headerJson = new JSONObject(decode(headerToken));
        return headerJson.toMap();
    }

    private boolean isTokenExpired(String expiration) {
        long exp = Long.parseLong(expiration);
        return exp <= (System.currentTimeMillis() / 1000);
    }

    private boolean validateHeaderFields(Map<String, Object> header) {
        boolean isValidTyp = header.containsKey("typ") && header.get("typ").equals("JWT");
        boolean isValidAlg = header.containsKey("alg") && header.get("alg").equals("HS256");

        return isValidTyp && isValidAlg;
    }

    private boolean validatePayloadFields(Map<String, Object> payload) {
        boolean isValidSub = payload.containsKey("sub");
        boolean isValidIssuer = payload.containsKey("iss") && payload.get("iss").equals("API-AUTH");
        boolean isValidId = payload.containsKey("id");
        boolean isValidExpiration = payload.containsKey("exp") && isTokenExpired(payload.get("exp").toString());

        return isValidSub && isValidIssuer && isValidId && isValidExpiration;
    }

    //TODO Da forma que está, ele só esta validando uma parte do body
    //TODO validateBody && validateSignature

}
