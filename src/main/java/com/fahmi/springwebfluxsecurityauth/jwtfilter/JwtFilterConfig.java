package com.fahmi.springwebfluxsecurityauth.jwtfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

@Configuration
public class JwtFilterConfig {

    private ReactiveUserDetailsService userDetailsService;
    private JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager;
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    @Autowired
    public JwtFilterConfig(ReactiveUserDetailsService userDetailsService,
                           @Qualifier("jwt") JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager,
                           JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.userDetailsService = userDetailsService;
        this.jwtReactiveAuthenticationManager = jwtReactiveAuthenticationManager;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    @Bean
    AuthenticationWebFilter jwtAuthenticationWebFilter(){
        AuthenticationWebFilter filter = new AuthenticationWebFilter(this.jwtReactiveAuthenticationManager);

        filter.setServerAuthenticationConverter(this.jwtAuthenticationConverter);

        filter.setAuthenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler((exchange, exception) -> Mono.error(exception));

        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/status/check"));

        return filter;
    }

    @Bean
    @Primary
    ReactiveAuthenticationManager userDetailsRepositoryReactiveAuthenticationManager(){
        return new UserDetailsRepositoryReactiveAuthenticationManager(this.userDetailsService);
    }
}
