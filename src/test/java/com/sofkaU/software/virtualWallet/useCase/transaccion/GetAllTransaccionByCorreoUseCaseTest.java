package com.sofkaU.software.virtualWallet.useCase.transaccion;

import com.sofkaU.software.virtualWallet.collections.Transaccion;
import com.sofkaU.software.virtualWallet.mapper.Mapper;
import com.sofkaU.software.virtualWallet.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class GetAllTransaccionByCorreoUseCaseTest {

    GetAllTransaccionByCorreoUseCase getAllTransaccionByCorreoUseCase;
    Mapper mapper = new Mapper();

    @Mock
    TransaccionRepository transaccionRepository;

    @BeforeEach
    public void setUp() {
        transaccionRepository = mock(TransaccionRepository.class);
        getAllTransaccionByCorreoUseCase = new GetAllTransaccionByCorreoUseCase(transaccionRepository, mapper);
    }

    @Test
    void GetAllTransaccionByEgresoUseCaseTest(){

        var transaccion = new Transaccion();

        transaccion.setId("qwerty");
        transaccion.setFecha("27-06-2022");
        transaccion.setCorreoOrigen("correoOrigen@correo.com");
        transaccion.setCorreoDestino("correoDestino@correo.com");
        transaccion.setValor(1000L);

        Mockito.when(transaccionRepository.findByCorreoOrigen("correoOrigen@correo.com")).thenReturn(Flux.just(transaccion));

        StepVerifier.create(getAllTransaccionByCorreoUseCase.getAllTransaccionEgreso(transaccion.getCorreoOrigen()))
                .expectNextMatches(transaccionDTO -> {
                    assert transaccionDTO.getId().equals("qwerty");
                    assert transaccionDTO.getFecha().equals("27-06-2022");
                    assert transaccionDTO.getCorreoOrigen().equals("correoOrigen@correo.com");
                    assert transaccionDTO.getCorreoDestino().equals("correoDestino@correo.com");
                    assert transaccionDTO.getValor()==1000L;
                    return true;
                })
                .verifyComplete();

        Mockito.verify(transaccionRepository).findByCorreoOrigen("correoOrigen@correo.com");
    }

    @Test
    void GetAllTransaccionByIngresoUseCaseTest(){

        var transaccion = new Transaccion();

        transaccion.setId("qwerty");
        transaccion.setFecha("27-06-2022");
        transaccion.setCorreoOrigen("correoOrigen@correo.com");
        transaccion.setCorreoDestino("correoDestino@correo.com");
        transaccion.setValor(1000L);

        Mockito.when(transaccionRepository.findByCorreoDestino("correoDestino@correo.com")).thenReturn(Flux.just(transaccion));

        StepVerifier.create(getAllTransaccionByCorreoUseCase.getAllTransaccionIngreso(transaccion.getCorreoDestino()))
                .expectNextMatches(transaccionDTO -> {
                    assert transaccionDTO.getId().equals("qwerty");
                    assert transaccionDTO.getFecha().equals("27-06-2022");
                    assert transaccionDTO.getCorreoOrigen().equals("correoOrigen@correo.com");
                    assert transaccionDTO.getCorreoDestino().equals("correoDestino@correo.com");
                    assert transaccionDTO.getValor()==1000L;
                    return true;
                })
                .verifyComplete();

        Mockito.verify(transaccionRepository).findByCorreoDestino("correoDestino@correo.com");
    }

}