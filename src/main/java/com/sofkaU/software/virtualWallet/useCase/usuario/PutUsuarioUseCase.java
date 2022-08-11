package com.sofkaU.software.virtualWallet.useCase.usuario;

import com.sofkaU.software.virtualWallet.bcrypt.BcryptMapper;
import com.sofkaU.software.virtualWallet.dto.UsuarioDto;
import com.sofkaU.software.virtualWallet.mapper.UsuarioMapper;
import com.sofkaU.software.virtualWallet.repository.iUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
public class PutUsuarioUseCase {

    private final iUsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final BcryptMapper bcrypt;

    public PutUsuarioUseCase(iUsuarioRepository repository, UsuarioMapper mapper, BcryptMapper bcrypt) {
        this.repository = repository;
        this.mapper = mapper;
        this.bcrypt = bcrypt;
    }

    /**
     * Método para actualizar el usuario
     * @param dto
     * @return un Mono después de haber filtrado la información por correo electrónico
     * Y hacer la conversión a un DTO
     */

    public Mono<UsuarioDto> updateUsuarioUseCase(UsuarioDto dto){

        dto.setContrasena(bcrypt.encode(dto.getContrasena()));
        return repository.findByCorreo(dto.getCorreo())
                .switchIfEmpty(Mono.error(()-> new IllegalStateException("Usuario con correo " + dto.getCorreo() + " no encontrado")))
                .map(element -> {
                    dto.setId(element.getId());
                    dto.setContrasena(element.getContrasena());
                    dto.setNombre(element.getNombre());
                    dto.setRol(element.getRol());
                    dto.setCorreo(element.getCorreo());
                    return dto;
                })
                .flatMap(userDto -> repository.save(mapper.dtoToEntity(dto)))
                .map(entity ->{
                    UsuarioDto usuarioDto = mapper.entityToDto(entity);
                    usuarioDto.setContrasena("Forbidden");
                    return usuarioDto;
                });
    }
}
