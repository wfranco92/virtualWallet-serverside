package com.sofkaU.software.virtualWallet.repository;

import com.sofkaU.software.virtualWallet.collections.Cuenta;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICuentaRepository extends ReactiveMongoRepository<Cuenta, String> {
}
