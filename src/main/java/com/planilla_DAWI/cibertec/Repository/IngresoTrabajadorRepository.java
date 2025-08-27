package com.planilla_DAWI.cibertec.Repository;

import com.planilla_DAWI.cibertec.Entity.IngresoTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngresoTrabajadorRepository extends JpaRepository<IngresoTrabajador, Integer> {
    @Query(value = " SELECT * FROM IngresosTrabajadores WHERE IdTrabajador = :IdTrabajador", nativeQuery = true)
    public IngresoTrabajador IngresoPorTrabajador(@Param(value = "IdTrabajador")Integer idTrabajador);

    @Query(value = "SELECT * FROM IngresosTrabajadores WHERE Activo = true",nativeQuery = true)
    public List<IngresoTrabajador> ListarIngresosActivos();
}
