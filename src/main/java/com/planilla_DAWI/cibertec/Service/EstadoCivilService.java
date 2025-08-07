package com.planilla_DAWI.cibertec.Service;

import com.planilla_DAWI.cibertec.Dto.EstadoCivilDTO;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.data.domain.Page;

public interface EstadoCivilService {
    Page<EstadoCivilDTO> listarEstadosCiviles(int page, EstadoEnum estado);
    EstadoCivilDTO obtenerPorId(Integer id);
    EstadoCivilDTO crear(EstadoCivilDTO estadoCivilDTO);
    EstadoCivilDTO actualizar(EstadoCivilDTO estadoCivilDTO);
    void cambiarEstado(Integer id);
}