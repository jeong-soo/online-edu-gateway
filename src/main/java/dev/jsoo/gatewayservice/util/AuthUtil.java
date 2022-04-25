package dev.jsoo.gatewayservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Arrays;

public class AuthUtil {
    private AuthUtil(){};
    public static boolean isValid(final String token, final String secret, final String type) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String[] typs = jwt.getClaim("typ").asArray(String.class);
            if(!Arrays.stream(typs).map(String::toUpperCase).anyMatch((s) -> s.equals(type))) return false;
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwt);
        } catch (Exception exception){
            return false;
        }
        return true;
    }
}
