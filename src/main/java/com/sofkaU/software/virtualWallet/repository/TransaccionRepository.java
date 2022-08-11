package com.sofkaU.software.virtualWallet.repository;

import com.sofkaU.software.virtualWallet.collections.Transaccion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransaccionRepository extends ReactiveCrudRepository<Transaccion, String> {

    Flux<Transaccion> findByCorreoOrigen(String correoOrigen);
    Flux<Transaccion> findByCorreoDestino(String correoDestino);
}
