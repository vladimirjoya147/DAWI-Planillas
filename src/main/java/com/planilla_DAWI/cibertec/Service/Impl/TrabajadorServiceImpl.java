package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.TrabajadorDTO;
import com.planilla_DAWI.cibertec.Entity.Trabajador;
import com.planilla_DAWI.cibertec.Repository.TrabajadorRepository;
import com.planilla_DAWI.cibertec.Service.TrabajadorService;

import java.util.Date;
import java.util.List;

public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorServiceImpl(TrabajadorRepository trabajadorRepository) {
        this.trabajadorRepository = trabajadorRepository;
    }

    @Override
    public List<Trabajador> listarTrabajadoresActivos() {
        return trabajadorRepository.listarTrabajadoresActivos();
    }

    @Override
    public List<TrabajadorDTO> listarTrabajadorBusqueda(String busqueda, int  estado) {
        return trabajadorRepository.listarTrabajadorBusqueda(busqueda, estado);
    }

    @Override
    public TrabajadorDTO trabajadorPorId(Integer id) {
        return trabajadorRepository.trabajadorPorId(id);
    }

    @Override
    public Integer insertar(Trabajador trabajador) {
        return trabajadorRepository.save(trabajador).getIdTrabajador();
    }

    @Override
    public Integer cambiarEstado(Integer id) {
        return trabajadorRepository.cambiarEstado(id, new Date());
    }
}
