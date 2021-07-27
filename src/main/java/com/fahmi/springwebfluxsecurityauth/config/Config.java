package com.fahmi.springwebfluxsecurityauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(){
        UserDetails user1 = User.builder()
                .username("user1")
                .password(this.passwordEncoder().encode("password"))
                .roles("user")
                .build();

        UserDetails user2 = User.builder()
                .username("user1")
                .password(this.passwordEncoder().encode("password"))
                .roles("admin")
                .build();

        return new MapReactiveUserDetailsService(user1, user2);
    }
}
