package com.wf.br.model;

import com.wf.br.repo.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test {

    @Autowired
    static SocieteRepository societeRepository;

    public static void transform_list_to_flux(){
        List<Societe>societes=new ArrayList<>();
        societes.add(new Societe("1","ADX",1000));
        societes.add(new Societe("2","RFI",4100));
        societes.add(new Societe("3","MTN",3000));

        Flux<List<Societe>>stringFlux=Flux.just(societes);
        stringFlux.subscribe(stes -> {
            for (Societe ste:stes) {
                System.out.println(ste);
            }
        });
    }

    public static void transform_listFlux_to_flux(){
        List<Flux<Societe>> fluxList = new ArrayList<>();
        fluxList.add(Flux.just(new Societe("1", "ADX", 1000)));
        fluxList.add(Flux.just(new Societe("2", "RFI", 4100)));
        fluxList.add(Flux.just(new Societe("3", "MTN", 3000)));

        Mono<List<Societe>> listFlux = Flux.merge(fluxList) // Fusionne les flux en un seul flux
                .collectList(); // Collecte les éléments du flux dans une liste

        listFlux.subscribe(list -> {
            for (Societe societe : list) {
                System.out.println(societe);
            }
        });

        List<Societe> societes = listFlux.block();

        for (Societe societe : societes) {
            System.out.println(societe);
        }

    }

//    @Test
    void dd(){
        // transforme d'abord la liste des flux en mono et puis block
        List<Societe> societes1=societeRepository.findAll().collectList().block();
        for (Societe societe : societes1) {
            System.out.println(societe);
        }
    }

    public static int processFlux(Flux<Integer> flux) {
        return flux
                .reduce(0, (accumulator, value) -> accumulator + value)
                .block(); // Bloquer pour obtenir le résultat (à utiliser avec prudence dans une application réactive réelle)
    }

    public static void main(String[] args) {

        transform_listFlux_to_flux();
        System.out.println("********************************************************");
        List<String>list=List.of("Java","Standard","Edition");
        Flux<String>stringFlux= Flux.fromIterable(list)
//                .concatWith(Flux.error(new RuntimeException("Une erreur est survenue !!!")))
                .concatWith(Flux.just("après erreur"))   // n'execute pas cette partie
                .log();

        System.out.println(
                stringFlux.subscribe(System.out::println,
                        (e) ->System.err.println("Exception is : "+e),
                        () -> System.out.println("On Completed "))
        );

        Flux<String>stringFlux1=Flux.just("Y","D","S","Z");


        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);
        // Appel de la méthode statique
        int sum = processFlux(flux);
        System.out.println("Sum: " + sum);

        System.out.println("********************************************************");

    }
}
