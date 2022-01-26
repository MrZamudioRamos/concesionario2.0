package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.Coche;
import com.concesionario.app.service.dto.CocheDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coche} and its DTO {@link CocheDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CocheMapper extends EntityMapper<CocheDTO, Coche> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CocheDTO toDtoId(Coche coche);
}
