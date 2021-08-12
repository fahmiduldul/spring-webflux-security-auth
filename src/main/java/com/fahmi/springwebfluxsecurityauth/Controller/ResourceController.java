package com.fahmi.springwebfluxsecurityauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public Mono<String> getAdmin(Authentication authentication){
        UserDetails userDetails = (User) authentication.getPrincipal();
        log.info("{}", userDetails.getAuthorities());
        return Mono.just("Hi " + userDetails.getUsername() + ". this is admin resource");
    }

    @GetMapping("/user")
    public Mono<String> getUser(Authentication authentication){
        UserDetails userDetails = (User) authentication.getPrincipal();
        Set<String> authorities = AuthorityUtils.authorityListToSet(userDetails.getAuthorities());
        log.info("{}", authorities);
        if(authorities.contains("ROLE_USER")){
            return Mono.just("hi, this is user resource");
        } else {
            throw new BadCredentialsException("bad credentials");
        }
    }
}
