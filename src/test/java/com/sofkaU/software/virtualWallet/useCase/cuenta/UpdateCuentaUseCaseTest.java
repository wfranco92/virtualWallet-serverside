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
class UpdateCuentaUseCaseTest {


   UpdateCuentaUseCase updateCuenta;

   CuentaMapper mapper = new CuentaMapper();

   @Mock
    ICuentaRepository cuentaRepo;

   @BeforeEach
   void setUp(){
       updateCuenta = new UpdateCuentaUseCase(cuentaRepo, mapper);
   }

    @Test
    public void actualizarCuenta(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setId("c5");
        cuentaDto.setCorreoUsuario("testCorreo@correo.com");
        cuentaDto.setMonto(90L);

        Cuenta cuenta = new Cuenta();
        cuenta.setId("c5");
        cuenta.setCorreoUsuario("testCorreo@correo.com");
        cuenta.setMonto(90L);

        Mockito.when(cuentaRepo.save(cuenta)).thenReturn(Mono.just(cuenta));

        Mono<CuentaDto> cuentaDtoMono = updateCuenta.updateCuenta(cuentaDto);

        StepVerifier.create(cuentaDtoMono)
                .expectNext(cuentaDto)
                .verifyComplete();

        Mockito.verify(cuentaRepo).save(cuenta);
    }

}