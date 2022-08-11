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
class GetAllUsuariosUseCaseTest {

    private GetAllUsuariosUseCase useCase;

    @Autowired
    UsuarioMapper mapper;
    @Mock
    iUsuarioRepository repository;

    @BeforeEach
    public void setUp(){
        useCase = new GetAllUsuariosUseCase(repository,mapper);
    }


    @Test
    void getAllUsers(){
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Maria");
        usuario.setCorreo("maria@gmail.com");
        usuario.setContrasena("maria123");
        usuario.setRol("user");
        usuario.setEstaActivo(false);
        usuario.setCorreoVerificado(false);

        Usuario usuario2 = new Usuario();
        usuario2.setId("1");
        usuario2.setNombre("Maria");
        usuario2.setCorreo("maria@gmail.com");
        usuario2.setContrasena("maria123");
        usuario2.setRol("user");
        usuario2.setEstaActivo(false);
        usuario2.setCorreoVerificado(false);

        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(usuario,usuario2));
        Flux<UsuarioDto> usuarioDtoFlux = useCase.getAllUsers();

        StepVerifier.create(usuarioDtoFlux)
                .expectNext(mapper.entityToDto(usuario))
                .expectNext(mapper.entityToDto(usuario2))
                .verifyComplete();
        Mockito.verify(repository).findAll();

    }


    @Test
    void getAllUsersFailed(){
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Maria");
        usuario.setCorreo("maria@gmail.com");
        usuario.setContrasena("maria123");
        usuario.setRol("user");
        usuario.setEstaActivo(false);
        usuario.setCorreoVerificado(false);

        Usuario usuario2 = new Usuario();
        usuario2.setId("1");
        usuario2.setNombre("Maria");
        usuario2.setCorreo("maria@gmail.com");
        usuario2.setContrasena("maria123");
        usuario2.setRol("user");
        usuario2.setEstaActivo(false);
        usuario2.setCorreoVerificado(false);

        Mockito.when(repository.findAll())
                .thenReturn(Flux.error(new Throwable()));
        Flux<UsuarioDto> usuarioDtoFlux = useCase.getAllUsers();

        StepVerifier.create(usuarioDtoFlux)
                .verifyComplete();
        Mockito.verify(repository).findAll();

    }

}