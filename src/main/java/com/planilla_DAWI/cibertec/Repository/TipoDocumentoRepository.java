package com.planilla_DAWI.cibertec.Repository;

import com.planilla_DAWI.cibertec.Entity.TipoDocumento;
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
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

    @Query("SELECT t FROM TipoDocumento t WHERE " +
            "(:estado = 2 OR t.activo = CASE WHEN :estado = 1 THEN true ELSE false END) " +
            "AND (:texto IS NULL OR :texto = '' OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', :texto, '%')))")
    Page<TipoDocumento> findByEstado(@Param("estado") int estado, @Param("texto") String texto, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE TipoDocumento t SET t.activo = CASE WHEN t.activo = true THEN false ELSE true END, t.fecUltimaModificacion = :fecha WHERE t.idTipoDocumento = :id")
    int cambiarEstado(@Param("id") Integer id, @Param("fecha") LocalDateTime fecha);
}
