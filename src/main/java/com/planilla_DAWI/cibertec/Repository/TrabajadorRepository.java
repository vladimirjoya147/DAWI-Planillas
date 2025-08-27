package com.planilla_DAWI.cibertec.Repository;

import com.planilla_DAWI.cibertec.Dto.TrabajadorDTO;
import com.planilla_DAWI.cibertec.Entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    @Query(value = """
            SELECT * FROM Trabajadores WHERE Activo = true
            """,nativeQuery = true)
    public List<Trabajador> listarTrabajadoresActivos ();

    @Query(value = """
            SELECT 
                t.IdTrabajador, t.IdTipoDocumento, t.Documento, t.Nombres,
                t.ApellidoPaterno, t.ApellidoMaterno, t.IdGenero, t.IdEstadoCivil,
                t.Direccion, t.Email, t.Hijos, t.IdCargo, t.FecNacimiento, t.FecIngreso,
                t.IdSituacion, t.IdSistemaPension, t.Activo
                td.nombre as desTipoDocumento, g.nombre as desGenero,
                e.nombre as desEstadoCivil, c.nombre as desCargo,
                s.nombre as desSituacion,   p.nombre as desSistemaPension
            from trabajadores t
                inner join tipodocumentos 		td on td.IdTipoDocumento = t.IdTipoDocumento
                inner join generos 				g  on g.IdGenero = t.IdGenero
                inner join estadosciviles 		e  on e.IdEstadoCivil = t.IdEstadoCivil
                inner join cargos 				c  on c.IdCargo = t.IdCargo
                inner join situaciontrabajador 	s  on c.IdSituacion = t.IdSituacion
                inner join sistemapensiones 	p  on p.IdSistemaPension = t.IdSistemaPension
            WHERE (t.ApellidoPaterno LIKE CONCAT('%', :busqueda, '%')
                OR t.ApellidoMaterno LIKE CONCAT('%', :busqueda, '%')
                OR t.Nombres LIKE CONCAT('%', :busqueda, '%')
                OR t.Documento LIKE CONCAT('%', :busqueda, '%'))
                AND (:estado = -1 OR Activo = :estado)
            """,nativeQuery = true)
    public List<TrabajadorDTO> listarTrabajadorBusqueda(String busqueda, int  estado);

    @Query(value = """
            SELECT 
                t.IdTrabajador, t.IdTipoDocumento, t.Documento, t.Nombres,
                t.ApellidoPaterno, t.ApellidoMaterno, t.IdGenero, t.IdEstadoCivil,
                t.Direccion, t.Email, t.Hijos, t.IdCargo, t.FecNacimiento, t.FecIngreso,
                t.IdSituacion, t.IdSistemaPension, t.Activo
                td.nombre as desTipoDocumento, g.nombre as desGenero,
                e.nombre as desEstadoCivil, c.nombre as desCargo,
                s.nombre as desSituacion,   p.nombre as desSistemaPension
            from trabajadores t
                inner join tipodocumentos 		td on td.IdTipoDocumento = t.IdTipoDocumento
                inner join generos 				g  on g.IdGenero = t.IdGenero
                inner join estadosciviles 		e  on e.IdEstadoCivil = t.IdEstadoCivil
                inner join cargos 				c  on c.IdCargo = t.IdCargo
                inner join situaciontrabajador 	s  on c.IdSituacion = t.IdSituacion
                inner join sistemapensiones 	p  on p.IdSistemaPension = t.IdSistemaPension
            WHERE (t.IdTrabajador = :id)
            """,nativeQuery = true)
    TrabajadorDTO trabajadorPorId(@Param("id")Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE trabajadores t SET t.activo = CASE WHEN t.activo = true THEN false ELSE true END, t.fecUltimaModificacion = :fecha WHERE t.IdTrabajador = :id",nativeQuery = true)
    int cambiarEstado(@Param("id") Integer id, @Param("fecha") Date fecha);
 }
