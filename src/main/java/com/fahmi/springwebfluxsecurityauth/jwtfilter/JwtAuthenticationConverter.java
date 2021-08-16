package com.fahmi.springwebfluxsecurityauth.jwtfilter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authorization = exchange.getRequest()
                .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        assert authorization != null;
        if(!authorization.startsWith("Bearer ")){
            return Mono.empty();
        }

        return Mono.just(new JwtAuthenticationToken(authorization.substring(7)));
    };
}
