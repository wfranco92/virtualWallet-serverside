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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllCuentasUseCaseTest {

    private GetAllCuentasUseCase allCuentas;

    CuentaMapper mapper = new CuentaMapper();

    @Mock
    ICuentaRepository cuentaRepo;

    @BeforeEach
    public void setUp(){
        allCuentas = new GetAllCuentasUseCase(cuentaRepo, mapper);
    }

    @Test
    void getAllCuentas(){

        Cuenta cuenta = new Cuenta();
        cuenta.setId("c1");
        cuenta.setCorreoUsuario("userEmail@correo.com");
        cuenta.setMonto(100L);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setId("c2");
        cuenta2.setCorreoUsuario("user2@correo.com");
        cuenta2.setMonto(200L);

        Mockito.when(cuentaRepo.findAll())
                .thenReturn(Flux.just(cuenta, cuenta2));
        Flux<CuentaDto> cuentaDtoFlux = allCuentas.todasLasCuentas();

        StepVerifier.create(cuentaDtoFlux)
                .expectNext(mapper.toCuentaDto(cuenta))
                .expectNext(mapper.toCuentaDto(cuenta2))
                .verifyComplete();
        Mockito.verify(cuentaRepo).findAll();
    }

    @Test
    void getAllCuentasFailed(){

        Cuenta cuenta = new Cuenta();
        cuenta.setId("c1");
        cuenta.setCorreoUsuario("userEmail@correo.com");
        cuenta.setMonto(100L);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setId("c2");
        cuenta2.setCorreoUsuario("user2@correo.com");
        cuenta2.setMonto(200L);

        Mockito.when(cuentaRepo.findAll())
                .thenReturn(Flux.error(new Throwable()));
        Flux<CuentaDto> cuentaDtoFlux = allCuentas.todasLasCuentas();

        StepVerifier.create(cuentaDtoFlux)
                .verifyComplete();
        Mockito.verify(cuentaRepo).findAll();
    }

}