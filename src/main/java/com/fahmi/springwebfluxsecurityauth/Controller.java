package com.fahmi.springwebfluxsecurityauth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController("/")
public class Controller {

    @GetMapping("/status/check")
    public Mono<String> statusCheck(){
        return Mono.just("I'm On!");
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(ServerWebExchange exchange, Authentication authentication){
        // option 1 to return username
        User userDetails = (User) authentication.getPrincipal();
        return Mono.just(
                ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, "token=jwttoken")
                        .body("Hi " + userDetails.getUsername() + ", you are just logged in")
        );

        // option 2 to get username
        //return exchange.getFormData()
        //        .mapNotNull(form -> form.getFirst("username"))
        //        .map(username -> ResponseEntity
        //                .ok()
        //                .header(HttpHeaders.SET_COOKIE, "token=jwttoken")
        //                .body("Hi " + username + ", you are just logged in"));
    }
}
