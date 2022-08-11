package com.sofkaU.software.virtualWallet.useCase.usuario;

import com.sofkaU.software.virtualWallet.bcrypt.BcryptMapper;
import com.sofkaU.software.virtualWallet.collections.Usuario;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.mapper.UsuarioMapper;
import com.sofkaU.software.virtualWallet.repository.iUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostUsuarioUseCaseTest {


    PostUsuarioUseCase useCase;

    private UsuarioMapper mapper= new UsuarioMapper();
    @Mock
    private BcryptMapper bcrypt;

    @Mock
    iUsuarioRepository repository;

    @BeforeEach
    void setUp() {
        useCase = new PostUsuarioUseCase(repository, mapper, bcrypt);

    }


    @Test
    void postUser(){
        UsuarioDto dto = new UsuarioDto();
        dto.setId("1");
        dto.setNombre("Maria");
        dto.setCorreo("maria@gmail.com");
        dto.setContrasena("maria123");
        dto.setRol("user");
        dto.setEstaActivo(false);
        dto.setCorreoVerificado(false);


        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNombre("Maria");
        usuario.setCorreo("maria@gmail.com");
        usuario.setContrasena("maria123");
        usuario.setRol("user");
        usuario.setEstaActivo(false);
        usuario.setCorreoVerificado(false);

        Mockito.when(repository.findByCorreo("maria@gmail.com"))
                .thenReturn(Mono.empty());
        Mockito.when(repository.save(usuario))
                .thenReturn(Mono.just(usuario));
        Mono<UsuarioDto> usuarioDtoMono = useCase.createUsuario(dto);

        StepVerifier.create(usuarioDtoMono)
                .expectNext(dto)
                .verifyComplete();

        Mockito.verify(repository).save(usuario);

    }

}