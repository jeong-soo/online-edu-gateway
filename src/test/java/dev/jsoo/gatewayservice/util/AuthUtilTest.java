package dev.jsoo.gatewayservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

class AuthUtilTest {

    @Test
    void isValid() {
        String secret = "www.codestates.com";
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withClaim("typ", List.of("ADMIN", "USER", "LECTURER"))
                .sign(Algorithm.HMAC256(secret));
        System.out.println(token);
        System.out.println(AuthUtil.isValid(token, secret, "ADMIN"));
        System.out.println(
                AuthUtil.isValid("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0eXAiOlsiQURNSU4iLCJVU0VSIiwiTEVDVFVSRVIiXSwiZXhwIjoxNjUwOTA0NzAyfQ.6NxrTUCl5f4IF96qBQTLHtiKrHrua5TIwUEtk7OeUck", secret, "ADMIN"));
    }
}