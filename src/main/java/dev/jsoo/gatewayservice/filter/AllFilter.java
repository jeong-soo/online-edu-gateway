package dev.jsoo.gatewayservice.filter;

import dev.jsoo.gatewayservice.type.AuthType;
import dev.jsoo.gatewayservice.util.FilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AllFilter extends AbstractGatewayFilterFactory<AllFilter.Config> {
    private Environment env;

    public AllFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return FilterUtil.getFilter(env.getProperty("token.secret"), AuthType.USER, AuthType.LECTURER, AuthType.ADMIN);
    }
}
