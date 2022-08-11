package com.sofkaU.software.virtualWallet.useCase.usuario;

import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.mapper.UsuarioMapper;
import com.sofkaU.software.virtualWallet.repository.iUsuarioRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindByCorreoUseCase {

    private final iUsuarioRepository repository;
    private final UsuarioMapper mapper;

    public FindByCorreoUseCase(iUsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Método para buscar usuario por su correo electrónico
     * @param correo
     * @return un Mono de usuario luego de haber sido buscado por correo electrónico. Si es encontrado,
     * dicha información es convertida a DTO para ser visualizada por el usuario
     */

    public Mono<UsuarioDto> findByCorreo(String correo){

        return repository.findByCorreo(correo)
                .switchIfEmpty(Mono.error(()->new IllegalStateException("Usuario no encontrado. Correo: " + correo)))
                .map(entity ->{
                    UsuarioDto usuarioDto = mapper.entityToDto(entity);
                    usuarioDto.setContrasena("Forbidden");
                    return usuarioDto;
                });
   }
}
