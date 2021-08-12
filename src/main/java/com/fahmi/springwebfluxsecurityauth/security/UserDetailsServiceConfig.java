package com.fahmi.springwebfluxsecurityauth.security;

import com.fahmi.springwebfluxsecurityauth.config.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableReactiveMethodSecurity
public class UserDetailsServiceConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(){
        UserDetails user1 = User.builder()
                .username("user1")
                .password(this.passwordEncoder().encode("password"))
                .roles(Roles.USER)
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(this.passwordEncoder().encode("password"))
                .roles(Roles.ADMIN)
                .build();

        return new MapReactiveUserDetailsService(user1, user2);
    }
}
