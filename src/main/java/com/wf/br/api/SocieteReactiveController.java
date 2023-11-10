package com.wf.br.api;

import com.wf.br.model.Societe;
import com.wf.br.repo.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
public class SocieteReactiveController {

    @Autowired
    SocieteRepository societeRepository;

    @GetMapping("/societes")
    Flux<Societe>getSociete(){
        return societeRepository.findAll();
    }

    @GetMapping("/societe/{id}")
    Mono<Societe>getSocieteById(@PathVariable String id){
        return societeRepository.findById(id);
    }
}
