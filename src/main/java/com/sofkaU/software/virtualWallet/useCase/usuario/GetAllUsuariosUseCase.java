package com.sofkaU.software.virtualWallet.useCase.usuario;

/**
 * Esta clase GetAllCuentasUseCase es el caso de uso que permite mostrar en pantalla todas los usuarios
 * que han sido creadas
 * @author: Maria Lamilla
 * */

import com.sofkaU.software.virtualWallet.collections.Usuario;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.mapper.UsuarioMapper;
import com.sofkaU.software.virtualWallet.repository.iUsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class GetAllUsuariosUseCase {

    private final iUsuarioRepository repository;
    private final UsuarioMapper mapper;

    public GetAllUsuariosUseCase(iUsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * El método getAllUsers retorna un Flux de DTO con todos los usuarios
     * luego de convertir cada colección de usuario en DTO
     * */

    public Flux<UsuarioDto> getAllUsers(){
        return repository.findAll()
                .map(entity -> {
            UsuarioDto usuarioDto = mapper.entityToDto(entity);
            usuarioDto.setContrasena("Hidden");
            return usuarioDto;
        }).onErrorResume(error-> {
                    log.error(error.getMessage());
                    return Flux.empty();
                });
    }
}
