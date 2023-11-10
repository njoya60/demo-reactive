package com.wf.br;

import com.wf.br.model.Societe;
import com.wf.br.repo.SocieteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

//@SpringBootTest
public class TestWebflux {

    @Autowired
    static SocieteRepository societeRepository;

    @Test
    void test_back(){
        // transforme d'abord la liste des flux en mono et puis block
        List<Societe> societes1=societeRepository.findAll().collectList().block();
        for (Societe societe : societes1) {
            System.out.println(societe);
        }
    }

    @Test
    void test_mono(){
        Mono<String>stringMono=Mono.just("Arouna").log();
        stringMono.subscribe(System.out::println,throwable -> System.out.println(throwable.getMessage()));
    }

    @Test
    void test_mono2(){
        Mono<?>stringMono=Mono.just("Arouna")
                .then(Mono.error(new RuntimeException("Erreur survenue")))
                .log();
        stringMono.subscribe(System.out::println,throwable -> System.out.println(throwable.getMessage()));
    }

    @Test
    void test_flux(){
        Flux<?> flux=Flux.just("Arouna","Njoya","Hadriel")
                .concatWithValues("AWS")
                .concatWith(Flux.error(new RuntimeException("Erreur survenue")))
                .log();
        flux.subscribe(System.out::println,throwable -> System.out.println(throwable.getMessage()));
    }
}
