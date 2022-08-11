package com.sofkaU.software.virtualWallet.repository;

import com.sofkaU.software.virtualWallet.collections.Usuario;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface iUsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

    Mono<Usuario> findByCorreo(String correo);
}
