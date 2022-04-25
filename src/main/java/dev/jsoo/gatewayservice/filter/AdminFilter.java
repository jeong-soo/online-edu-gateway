package dev.jsoo.gatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import static dev.jsoo.gatewayservice.util.AuthUtil.isValid;

@Component
@Slf4j
public class AdminFilter extends AbstractGatewayFilterFactory<AdminFilter.Config> {
    private Environment env;
    private final static String type = "ADMIN";

    public AdminFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if(!isValid(authorizationHeader.replace("Bearer ", ""), env.getProperty("token.secret"), type))
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response =  exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        return response.setComplete();
    }
}
