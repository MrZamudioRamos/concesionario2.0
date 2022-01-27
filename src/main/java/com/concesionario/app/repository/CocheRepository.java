package com.concesionario.app.repository;

import com.concesionario.app.domain.Coche;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Coche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CocheRepository extends JpaRepository<Coche, Long> {
    Page<Coche> findByColor(String color, Pageable pageable);

    @Query("SELECT c FROM Coche c where c.modelo like :modelo%")
    Page<Coche> cochesPaginadosPorModelo(@Param("modelo") String modelo, Pageable pageable);

    @Query("SELECT c FROM Coche c where c.exposicion = :exposicion")
    Page<Coche> cochesPaginadosPorExposicion(@Param("exposicion") Boolean exposicion, Pageable pageable);
}
