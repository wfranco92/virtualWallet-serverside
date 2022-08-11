package com.sofkaU.software.virtualWallet.useCase.usuario;

/**
 * La clase CreateTransaccionUseCase es el caso de uso que permite crear un usuario
 * @author: Maria Lamilla
 * */

import com.sofkaU.software.virtualWallet.bcrypt.BcryptMapper;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.mapper.UsuarioMapper;
import com.sofkaU.software.virtualWallet.repository.iUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
@Validated
public class PostUsuarioUseCase {

    private final iUsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final BcryptMapper bcrypt;

    public PostUsuarioUseCase(iUsuarioRepository repository, UsuarioMapper mapper, BcryptMapper bcrypt) {
        this.repository = repository;
        this.mapper = mapper;
        this.bcrypt = bcrypt;
    }

    /**
     *el método createUsuario permite la creación de un usuario
     * @param dto
     * @return un Mono de un usuario luego de realizar la conversión de colección a DTO
     */

    public Mono<UsuarioDto> createUsuario(@Valid UsuarioDto dto) {

        return repository.findByCorreo(dto.getCorreo()).flatMap(usuario -> Mono.error(new IllegalArgumentException("Correo ya existe")))
                .then(repository.save(mapper.dtoToEntity(dto))
                        .map(entity -> {
                                UsuarioDto usuarioDto = mapper.entityToDto(entity);
                                usuarioDto.setContrasena("Hidden");
                                return usuarioDto;}));
    }
}
