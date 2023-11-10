package com.wf.br.handler;

import com.wf.br.aa.SocieteDao;
import com.wf.br.model.Societe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SocieteHandler {

    @Autowired
    SocieteDao dao;

    public Mono<ServerResponse> loadSocietes(ServerRequest request){
        Flux<Societe>societeFlux=dao.getSocieteList();
        return ServerResponse.ok()
                .body(societeFlux,Societe.class );
    }

    public Mono<ServerResponse> getSocietesStream(ServerRequest request){
        Flux<Societe> societeStream = dao.getSocieteStream();
        return ServerResponse.ok().body(societeStream,Societe.class);
    }

    public Mono<ServerResponse> findBySocietes(ServerRequest request){
        String input = request.pathVariable("input");

//        Flux<Societe>societeFlux=dao.getSocieteList().filter(societe -> societe.getId().equals(input));
        Mono<Societe>societeFlux=dao.getSocieteList().filter(societe -> societe.getId().equals(input)).take(1).single();
//        Mono<Societe>societeFlux=dao.getSocieteList().filter(societe -> societe.getId().equals(input)).next();
        return ServerResponse.ok()
                .body(societeFlux,Societe.class );
    }

    public Mono<ServerResponse> saveSociete(ServerRequest serverRequest) {
        Mono<Societe> societeMono = serverRequest.bodyToMono(Societe.class);
        Mono<String> stringMono = societeMono.map(societe -> societe.getId() + " : " + societe.getName() + " : " + societe.getPrice());
        return ServerResponse.ok().body(stringMono,Societe.class);
    }
}
