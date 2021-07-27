package com.fahmi.springwebfluxsecurityauth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<String>> login(ServerWebExchange exchange){
        return Mono.just(
                ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, "token=token")
                        .body("success login")
        );
    }
}
