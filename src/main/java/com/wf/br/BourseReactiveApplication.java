package com.wf.br;

import com.wf.br.model.Societe;
import com.wf.br.model.Transaction;
import com.wf.br.repo.SocieteRepository;
import com.wf.br.repo.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class BourseReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(BourseReactiveApplication.class, args);
    }

//    @Bean
    CommandLineRunner start(SocieteRepository societeRepository, TransactionRepository transactionRepository) {
        return args -> {
            Stream.of("ADF", "BBC", "RTF", "RFI").forEach(s -> {
                societeRepository.save(new Societe(s, s, 100 + Math.random() * 100))
                        .subscribe(societe -> {
                            System.out.println(societe.toString());
                        });
//                System.out.println("*********************inside*************************");
            });
            Stream.of("ADF", "BBC", "RTF", "RFI").forEach(s -> {
                        societeRepository.findById(s).subscribe(societe -> {
                            for (int i = 0; i < 30; i++) {
                                transactionRepository.save(new Transaction("" + i, Instant.now(),
                                        societe.getPrice()*(1 + Math.random() * 100), societe))
                                        .subscribe(transaction -> {
                                            System.out.println(transaction.toString());
                                        });
                            }
                        });
                    }
            );
            System.out.println("*********************ouside*************************");
        };
    }
}
