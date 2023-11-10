package com.wf.br.repo;

import com.wf.br.model.Transaction;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction,String> {
    Flux<Transaction> findByPriceBetween(Range<Double> priceBetween);
}
