package com.wf.br;

import com.wf.br.aa.SocieteController;
import com.wf.br.aa.SocieteDto;
import com.wf.br.aa.SocieteService;
import com.wf.br.model.Societe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebFluxTest(SocieteController.class)
public class SpringReactiveMongoCrudTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SocieteService service;

    @Test
    public void addProductTest() {
        Mono<SocieteDto> societeDtoMono = Mono.just(new SocieteDto("1", "RFI", 1500));
        when(service.saveSociete(societeDtoMono)).thenReturn(societeDtoMono);

        webTestClient.post()
                .uri("/societe/save")
                .body(Mono.just(societeDtoMono), Societe.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getAllSocieteStreamTest() {
        Societe societe1 = new Societe("1", "xxx", 1200);
        Societe societe2 = new Societe("2", "yyyy", 1600);
        Societe societe3 = new Societe("3", "zzzz", 1900);
        Flux<Societe> societeFlux = Flux.just(societe1, societe2, societe3);
        when(service.getAllSocieteStream()).thenReturn(societeFlux);
        Flux<Societe> responseBody = webTestClient.get()
                .uri("/societe/stream")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Societe.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(societe1)
                .expectNext(societe2)
                .expectNext(societe3)
                .verifyComplete();
    }

    @Test
    public void getSocieteTest() {
        Mono<SocieteDto> societeMono = Mono.just(new SocieteDto("1", "RFI", 1500));
        when(service.getSociete(any())).thenReturn(societeMono);
        Flux<SocieteDto> responseBody = webTestClient.get()
                .uri("/societe/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(SocieteDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(societeDto -> societeDto.name().equals("RFI"))
                .verifyComplete();

    }

    @Test
    public void updateTest() {
        Mono<SocieteDto> societeDtoMono=Mono.just(new SocieteDto("1", "RFI", 1500));
        when(service.updateSociete(societeDtoMono,"1")).thenReturn(societeDtoMono);
        webTestClient.put()
                .uri("/societe/update/10")
                .body(Mono.just(societeDtoMono),SocieteDto.class)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void deleteTest(){
        given(service.deleteSociete(any())).willReturn(Mono.empty());
        webTestClient.delete()
                .uri("/societe/delete/1")
                .exchange()
                .expectStatus().isOk();
    }

}
