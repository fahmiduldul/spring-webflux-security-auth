package com.fahmi.springwebfluxsecurityauth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class WebFluxSecurity {
    @Bean
    public SecurityWebFilterChain chain(ServerHttpSecurity http){
        http
                // allow all path to be accessed
                .authorizeExchange()
                .pathMatchers("/**").permitAll()

                // add AuthenticationWebFilter and set the handler
                .and()
                .formLogin()
                .authenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler())
                .authenticationFailureHandler(((webFilterExchange, exception) -> Mono.error(exception)))

                .and()
                .httpBasic();

        return http.build();
    }
}
