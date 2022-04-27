package dev.jsoo.gatewayservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.jsoo.gatewayservice.type.AuthType;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

public class AuthUtil {
    private AuthUtil(){};
    public static boolean isValid(final String token, final String secret, final AuthType... types) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            AuthType[] typs = Arrays.stream(jwt.getClaim("typ").asArray(String.class))
                .map((s) -> AuthType.getAuthType(s))
                .toArray(AuthType[]::new);
            if(!Arrays.stream(typs).anyMatch((s) -> ArrayUtils.contains(types, s))) return false;
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwt);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
