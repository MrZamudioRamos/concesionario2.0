package com.concesionario.app.service.impl;

import com.concesionario.app.domain.Coche;
import com.concesionario.app.domain.Empleado;
import com.concesionario.app.domain.Venta;
import com.concesionario.app.repository.CocheRepository;
import com.concesionario.app.repository.EmpleadoRepository;
import com.concesionario.app.repository.VentaRepository;
import com.concesionario.app.service.VentaService;
import com.concesionario.app.service.dto.VentaDTO;
import com.concesionario.app.service.mapper.VentaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Venta}.
 */
@Service
@Transactional // Si no esta aqui arriba hay que ponerla en un metodo especifico.
public class VentaServiceImpl implements VentaService {

    private final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final CocheRepository cocheRepository;

    private final VentaMapper ventaMapper;

    public VentaServiceImpl(
        VentaRepository ventaRepository,
        VentaMapper ventaMapper,
        EmpleadoRepository empleadoRepository,
        CocheRepository cocheRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.empleadoRepository = empleadoRepository;
        this.cocheRepository = cocheRepository;
    }

    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO); // Instancia venta, con mapper la convierte en Entity con el DTO.
        venta = ventaRepository.save(venta);

        // Cuando se hace una venta al empleado el numero de ventas sube +1
        // Hay que llamar al repositorio de empleado, importarlo y agregarlo al
        // constructor
        if (null != venta.getEmpleado()) { // Validamos para ver si hay empleado en la venta
            Empleado empleado = venta.getEmpleado();

            if (null == empleado.getNumeroVentas()) { // Mira si las ventas no son nulas, si lo es, le asigna 0
                empleado.setNumeroVentas(0); // Es prefereible poner el null primero por temas de nullPointer
            }
            empleado.setNumeroVentas(empleado.getNumeroVentas() + 1); // Le sumamos una venta, para despues hacer temas
            // de comision por ej.
            empleadoRepository.save(empleado);
        }

        if (null != venta.getCoches()) { // Validamos si hay coche en la venta
            Coche coche = venta.getCoches(); // Cogemos el coche

            coche.setExposicion(false); // cambiamos su estado de exposicion porque ha sido comprado

            cocheRepository.save(coche);
        }

        return ventaMapper.toDto(venta);
    }

    @Override
    public Optional<VentaDTO> partialUpdate(VentaDTO ventaDTO) {
        log.debug("Request to partially update Venta : {}", ventaDTO);

        return ventaRepository
            .findById(ventaDTO.getId())
            .map(existingVenta -> {
                ventaMapper.partialUpdate(existingVenta, ventaDTO);

                return existingVenta;
            })
            .map(ventaRepository::save)
            .map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable).map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findById(id).map(ventaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }
}
