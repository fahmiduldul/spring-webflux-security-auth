package com.fahmi.springwebfluxsecurityauth.Controller;

import com.fahmi.springwebfluxsecurityauth.jwtfilter.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController("/")
public class Controller {

    private JwtUtil jwtUtil;

    public Controller(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/status/check")
    public Mono<String> statusCheck(){
        return Mono.just("I'm On!");
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(Authentication authentication){
        String username = ((User) authentication.getPrincipal()).getUsername();

        String token = this.jwtUtil.encode(username);

        // option 1 to return username
        User userDetails = (User) authentication.getPrincipal();
        return Mono.just(
                ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, "token=" + token)
                        .body("Hi " + userDetails.getUsername() + ", you are just logged in")
        );

        // option 2 to get username
        //return exchange.getFormData()
        //        .mapNotNull(form -> form.getFirst("username"))
        //        .map(username -> ResponseEntity
        //                .ok()
        //                .header(HttpHeaders.SET_COOKIE, "token="+token)
        //                .body("Hi " + username + ", you are just logged in"));
    }
}
