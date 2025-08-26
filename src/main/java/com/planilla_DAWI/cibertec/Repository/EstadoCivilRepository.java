package com.planilla_DAWI.cibertec.Repository;

import com.planilla_DAWI.cibertec.Entity.EstadoCivil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Integer> {

    @Query("SELECT e FROM EstadoCivil e WHERE " +
            "(:estado = 2 OR e.activo = CASE WHEN :estado = 1 THEN true ELSE false END) " +
            "AND (:texto IS NULL OR :texto = '' OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :texto, '%')))")
    Page<EstadoCivil> findByEstado(@Param("estado") int estado, @Param("texto") String texto, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE EstadoCivil e SET e.activo = CASE WHEN e.activo = true THEN false ELSE true END, " +
            "e.fecUltimaModificacion = :fecha WHERE e.idEstadoCivil = :id")
    int cambiarEstado(@Param("id") Integer id, @Param("fecha") LocalDateTime fecha);
}
