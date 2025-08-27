package com.planilla_DAWI.cibertec.Service;

import com.planilla_DAWI.cibertec.Dto.TrabajadorDTO;
import com.planilla_DAWI.cibertec.Entity.IngresoTrabajador;
import com.planilla_DAWI.cibertec.Entity.Trabajador;

import java.util.List;

public interface TrabajadorService {
    List<Trabajador> listarTrabajadoresActivos ();
    List<TrabajadorDTO>listarTrabajadorBusqueda(String busqueda, int  estado);
    TrabajadorDTO trabajadorPorId(Integer id);
    Integer insertar(Trabajador Trabajador);
    Integer cambiarEstado(Integer id);
}
