package com.sofkaU.software.virtualWallet.useCase.transaccion;

import com.sofkaU.software.virtualWallet.collections.Transaccion;
import com.sofkaU.software.virtualWallet.dto.TransaccionDTO;
import com.sofkaU.software.virtualWallet.mapper.Mapper;
import com.sofkaU.software.virtualWallet.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CreateTransaccionUseCaseTest {

    CreateTransaccionUseCase createTransaccionUseCase;
    Mapper mapper = new Mapper();

    @Mock
    TransaccionRepository transaccionRepository;

    @BeforeEach
    public void setup() {
        transaccionRepository = mock(TransaccionRepository.class);
        createTransaccionUseCase = new CreateTransaccionUseCase(mapper, transaccionRepository);
    }

    @Test
    void createTransacciontest(){

        var transaccion = new Transaccion();

        transaccion.setId("qwerty");
        transaccion.setFecha("27-06-2022");
        transaccion.setCorreoOrigen("correoOrigen@correo.com");
        transaccion.setCorreoDestino("correoDestino@correo.com");
        transaccion.setValor(1000L);

        var transaccionDTO = new TransaccionDTO();

        transaccionDTO.setId("qwerty");
        transaccionDTO.setFecha("27-06-2022");
        transaccionDTO.setCorreoOrigen("correoOrigen@correo.com");
        transaccionDTO.setCorreoDestino("correoDestino@correo.com");
        transaccionDTO.setValor(1000L);

        Mockito.when(transaccionRepository.save(transaccion)).thenReturn(Mono.just(transaccion));

        StepVerifier.create(createTransaccionUseCase.apply(transaccionDTO))
                .expectNext(transaccionDTO).verifyComplete();

        Mockito.verify(transaccionRepository).save(transaccion);
    }

}