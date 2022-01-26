package com.concesionario.app.service.mapper;

import com.concesionario.app.domain.Venta;
import com.concesionario.app.service.dto.VentaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring", uses = { CocheMapper.class, EmpleadoMapper.class, ClienteMapper.class })
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {
    @Mapping(target = "coches", source = "coches", qualifiedByName = "id")
    @Mapping(target = "empleado", source = "empleado", qualifiedByName = "id")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "id")
    VentaDTO toDto(Venta s);
}
