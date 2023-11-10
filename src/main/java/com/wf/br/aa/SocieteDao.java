package com.wf.br.aa;

import com.wf.br.model.Societe;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class SocieteDao {

    private static void sleepExecution(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Societe>getSociete(){
        return IntStream.rangeClosed(1, 20)
                .peek(value -> sleepExecution())
                .peek(value -> System.out.println("count value : "+value))
                .mapToObj(value -> new Societe("" + value, "SOCIETE " + value, 100 * Math.random()))
                .collect(Collectors.toList());
    }

    public Flux<Societe> getSocieteStream(){

        Flux<Societe> just1 = Flux.range(1, 20)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(value -> System.out.println("count value : "+value))
                .map(value -> new Societe("" + value, "SOCIETE " + value, 100 * Math.random()));

//        Flux<Stream<Societe>> just = Flux.just(IntStream.rangeClosed(1, 20)
//                .mapToObj(value -> new Societe("" + value, "SOCIETE " + value, 100 * Math.random()))
//        );

        //        return IntStream.rangeClosed(1, 20)
////                .peek(value -> sleepExecution())
//                .peek(value -> System.out.println("count value : "+value))
//                .mapToObj(value -> new Societe("" + value, "SOCIETE " + value, 100 * Math.random()))
//                .collect(Collectors.toList());


        return just1;
    }

    public Flux<Societe> getSocieteList(){

        Flux<Societe> just1 = Flux.range(1, 20)
                .doOnNext(value -> System.out.println("count value : "+value))
                .map(value -> new Societe("" + value, "SOCIETE " + value, 10 * Math.random()));

        return just1;
    }
}
