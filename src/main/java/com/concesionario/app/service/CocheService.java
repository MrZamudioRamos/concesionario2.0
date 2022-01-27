package com.concesionario.app.service;

import com.concesionario.app.service.dto.CocheDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.concesionario.app.domain.Coche}.
 */
public interface CocheService {
    /**
     * Save a coche.
     *
     * @param cocheDTO the entity to save.
     * @return the persisted entity.
     */
    CocheDTO save(CocheDTO cocheDTO);

    /**
     * Partially updates a coche.
     *
     * @param cocheDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CocheDTO> partialUpdate(CocheDTO cocheDTO);

    /**
     * Get all the coches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CocheDTO> findAll(Pageable pageable);

    /**
     * Get all the coches by exposicion.
     *
     * @param exposicion true
     * @param pageable   the pagination information.
     * @return the list of entities.
     */
    public Page<CocheDTO> findAllByExposicion(Boolean exposicion, Pageable pageable);

    /**
     * Get all the coches by modelo paginados.
     *
     * @param modelo   the pagination information.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CocheDTO> cochesPaginadosPorModelo(String modelo, Pageable pageable);

    /**
     * Get all the coches by color paginados.
     *
     * @param color    the color information.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CocheDTO> cochesPaginadosPorColor(String color, Pageable pageable);

    /**
     * Get the "id" coche.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CocheDTO> findOne(Long id);

    /**
     * Delete the "id" coche.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
