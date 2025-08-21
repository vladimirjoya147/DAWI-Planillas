package com.planilla_DAWI.cibertec.Repository;

import com.planilla_DAWI.cibertec.Entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    @Query(value = """
            SELECT * FROM Trabajadores WHERE Activo = true
            """,nativeQuery = true)
    public List<Trabajador> listarTrabajadoresActivos ();
 }
