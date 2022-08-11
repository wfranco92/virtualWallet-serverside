package com.sofkaU.software.virtualWallet.useCase.cuenta;

import com.sofkaU.software.virtualWallet.collections.Cuenta;
import com.sofkaU.software.virtualWallet.dto.CuentaDto;
import com.sofkaU.software.virtualWallet.mapper.CuentaMapper;
import com.sofkaU.software.virtualWallet.repository.ICuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class PostCuentaUseCaseTest {

    PostCuentaUseCase postCuenta;

    CuentaMapper mapper = new CuentaMapper();

    @Mock
    ICuentaRepository cuentaRepo;

    @BeforeEach
    void setUp(){
        postCuenta = new PostCuentaUseCase(cuentaRepo, mapper);
    }

    @Test
    void postCta(){

        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setId("c3");
        cuentaDto.setCorreoUsuario("correoPrueba@correo.com");
        cuentaDto.setMonto(50L);

        Cuenta cuenta = new Cuenta();
        cuenta.setId("c3");
        cuenta.setCorreoUsuario("correoPrueba@correo.com");
        cuenta.setMonto(50L);

        Mockito.when(cuentaRepo.save(cuenta)).thenReturn(Mono.just(cuenta));

        StepVerifier.create(postCuenta.crearCuenta(cuentaDto))
                .expectNext(cuentaDto)
                .verifyComplete();

        Mockito.verify(cuentaRepo).save(cuenta);
    }

}