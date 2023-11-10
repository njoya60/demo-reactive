package com.wf.br.repo;

import com.wf.br.model.Societe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocieteRepository extends ReactiveMongoRepository<Societe, String> {
}
