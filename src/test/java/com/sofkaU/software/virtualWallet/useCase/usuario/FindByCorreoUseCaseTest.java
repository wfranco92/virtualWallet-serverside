package com.sofkaU.software.virtualWallet.useCase.usuario;

import com.sofkaU.software.virtualWallet.collections.Usuario;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.mapper.UsuarioMapper;
import com.sofkaU.software.virtualWallet.repository.iUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindByCorreoUseCaseTest {

    private FindByCorreoUseCase useCase;

    @Autowired
    UsuarioMapper mapper;
    @Mock
    iUsuarioRepository repository;

    @BeforeEach
    void setUp(){
        useCase = new FindByCorreoUseCase(repository,mapper);

    }
    @Test
    void getOneUser(){
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Maria");
        usuario.setCorreo("maria@gmail.com");
        usuario.setContrasena("maria123");
        usuario.setRol("user");
        usuario.setEstaActivo(false);
        usuario.setCorreoVerificado(false);

        Mockito.when(repository.findByCorreo(Mockito.any(String.class)))
                .thenReturn(Mono.just(usuario));
        Mono<UsuarioDto> usuarioDtoMono = useCase.findByCorreo("maria@gmail.com");

        StepVerifier.create(usuarioDtoMono)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).findByCorreo("maria@gmail.com");

    }

}