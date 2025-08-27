package loans.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/contact-support")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<String> contatSupport() {
        return Mono.just("An error occurred, please try after some time or contact support. Thank you.");
    }
}
