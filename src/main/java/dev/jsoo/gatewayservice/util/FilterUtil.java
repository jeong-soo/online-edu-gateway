package dev.jsoo.gatewayservice.util;

import dev.jsoo.gatewayservice.type.AuthType;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static dev.jsoo.gatewayservice.util.AuthUtil.isValid;

public class FilterUtil {
    public static GatewayFilter getFilter(String secret, AuthType... types) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return getError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if(!isValid(authorizationHeader.replace("Bearer ", ""), secret, types))
                return getError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            return chain.filter(exchange);
        });
    }

    public static Mono<Void> getError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response =  exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
