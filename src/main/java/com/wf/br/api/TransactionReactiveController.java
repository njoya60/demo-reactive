package com.wf.br.api;

import com.wf.br.model.Societe;
import com.wf.br.model.Transaction;
import com.wf.br.repo.SocieteRepository;
import com.wf.br.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
@CrossOrigin("*")
public class TransactionReactiveController {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    SocieteRepository societeRepository;

    @GetMapping("/transactions")
    Flux<Transaction>getTransaction(){
        return transactionRepository.findAll();
    }

    @GetMapping("/transaction/{id}")
    Mono<Transaction>getTransactionById(@PathVariable String id){
        return transactionRepository.findById(id);
    }

    @GetMapping(value = "/transactions-stream",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    Flux<Transaction>getTransactionStream(){
        return transactionRepository.findAll();
    }

//    @GetMapping(value = "/transactions-streaming",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//@GetMapping(value = "/transactions-streaming",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
@GetMapping(value = "/transactions-streaming")
    Flux<Transaction>getTransactionStreamE(){
        return societeRepository.findById("RTF")
                .flatMapMany(soc -> {
                    Flux<Long> instant=Flux.interval(Duration.ofMillis(1000));
                    Flux<Transaction>transactionFlux=Flux.fromStream(Stream.generate(() -> {
                        Transaction trx=new Transaction();
                        trx.setId(String.valueOf(10+Math.random()));
                        trx.setInstant(Instant.now());
                        trx.setPrice(soc.getPrice()*(1 + Math.random() * 100));
                        trx.setSociete(soc);
                        return trx;
                    }));
                    return Flux.zip(instant,transactionFlux)
                            .map(data -> {
                                return data.getT2();
                            });
                });
    }
}
