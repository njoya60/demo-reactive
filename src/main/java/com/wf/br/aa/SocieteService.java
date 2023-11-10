package com.wf.br.aa;

import com.wf.br.model.Societe;
import com.wf.br.model.Transaction;
import com.wf.br.repo.SocieteRepository;
import com.wf.br.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocieteService {

    private final SocieteDao dao;
    private final SocieteRepository societeRepository;
    private final TransactionRepository transactionRepository;

    public List<Societe>getAllSociete(){
        long start = System.currentTimeMillis();
        List<Societe> societes = dao.getSociete();
        long end = System.currentTimeMillis();
        System.out.println("Temps d'exécution total : "+(end-start));
        return societes;
    }

    public Flux<Societe> getAllSocieteStream(){
        long start = System.currentTimeMillis();
        Flux<Societe> societes = dao.getSocieteStream() ;
        long end = System.currentTimeMillis();
        System.out.println("Temps d'exécution total : "+(end-start));
        return societes;
    }

    public Flux<Transaction>getTransactionRange(double min, double max){
        return transactionRepository.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<SocieteDto> saveSociete(Mono<SocieteDto> societeMono){
        System.out.println("Service call....................");
        return societeMono.map(AppUtils::dtoToEntity)
                .flatMap(societeRepository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<SocieteDto> updateSociete(Mono<SocieteDto> societeMono,String id){
        return societeRepository.findById(id)
                .flatMap(ste -> societeMono.map(AppUtils::dtoToEntity))
                .doOnNext(ste -> ste.setId(id))
                .flatMap(societeRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteSociete(String id){
//        return societeRepository.deleteById(id);

        return societeRepository.findById(id)
                .doOnNext(societeRepository::delete).then();
    }


    public Mono<SocieteDto> getSociete(String id) {
        return societeRepository.findById(id)
                .map(AppUtils::entityToDto);
    }
}
