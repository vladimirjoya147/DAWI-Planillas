package com.planilla_DAWI.cibertec.Repository;

import com.planilla_DAWI.cibertec.Entity.SituacionTrabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

public interface SituacionTrabajadorRepository extends JpaRepository<SituacionTrabajador, Integer> {

    @Query("SELECT s FROM SituacionTrabajador s WHERE " +
            "(:estado = 2 OR s.activo = CASE WHEN :estado = 1 THEN true ELSE false END) " +
            "AND (:texto IS NULL OR :texto = '' OR LOWER(s.nombre) LIKE LOWER(CONCAT('%', :texto, '%')))")
    Page<SituacionTrabajador> findByEstado(@Param("estado") int estado, @Param("texto") String texto, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE SituacionTrabajador s SET s.activo = CASE WHEN s.activo = true THEN false ELSE true END, s.fecUltimaModificacion = :fecha WHERE s.idSituacion = :id")
    int cambiarEstado(@Param("id") Integer id, @Param("fecha") LocalDateTime fecha);
}
