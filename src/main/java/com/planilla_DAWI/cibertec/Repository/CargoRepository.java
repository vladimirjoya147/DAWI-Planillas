package com.planilla_DAWI.cibertec.Repository;

import com.planilla_DAWI.cibertec.Entity.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    @Query("SELECT c FROM Cargo c WHERE (:estado = 2 OR c.activo = CASE WHEN :estado = 1 THEN true ELSE false END)")
    Page<Cargo> findByEstado(@Param("estado") int estado, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Cargo c SET c.activo = CASE WHEN c.activo = true THEN false ELSE true END, c.fecUltimaModificacion = :fecha WHERE c.idCargo = :id")
    int cambiarEstado(@Param("id") Integer id, @Param("fecha") Date fecha);
}