package com.wf.br.aa;

import com.wf.br.model.Societe;
import com.wf.br.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/societe")
public class SocieteController {

    @Autowired
    SocieteService service;

    @GetMapping
    List<Societe> getAllSociete() {
        return service.getAllSociete();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Societe> getAllSocieteStream() {
        return service.getAllSocieteStream();
    }

    @GetMapping(value = "/get/{id}")
    Mono<SocieteDto> getSociete(@PathVariable String id) {
        return service.getSociete(id);
    }

    @PostMapping("/save")
    public Mono<SocieteDto> save(@RequestBody Mono<SocieteDto> societeDtoMono) {
        System.out.println("Controller call..........................");
        return service.saveSociete(societeDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<SocieteDto> update(@RequestBody Mono<SocieteDto> societeDtoMono, @PathVariable String id) {
        return service.updateSociete(societeDtoMono, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return service.deleteSociete(id);
    }

    @GetMapping("/range-transaction")
    public Flux<Transaction> getTransactionRange(@RequestParam double min, @RequestParam double max) {
        return service.getTransactionRange(min, max);
    }

    //*******************************Autres test rective
    @GetMapping("/msg")
    private Mono<String>msg3(){
        return msg1().zipWith(msg2())
                 .map(value -> {
                     return value.getT1()+" "+value.getT2();
                 });
    }

    private Mono<String>msg1(){
        return Mono.just("Arouna").delayElement(Duration.ofSeconds(3));
    }

    private Mono<String>msg2(){
        return Mono.just("Hadriel").delayElement(Duration.ofSeconds(3));
    }
}
