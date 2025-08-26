package com.planilla_DAWI.cibertec.Mappers;

import com.planilla_DAWI.cibertec.Dto.TrabajadorDTO;
import com.planilla_DAWI.cibertec.Entity.Trabajador;

public class TrabajadorMapper {
    public static TrabajadorDTO toDTO(Trabajador entity) {
        if (entity == null) {
            return null;
        }
        TrabajadorDTO dto = new TrabajadorDTO();
        dto.setIdTrabajador(entity.getIdTrabajador());
        dto.setIdTipoDocumento(entity.getTipoDocumento().getIdTipoDocumento());
        dto.setDesTipoDocumento(entity.getTipoDocumento().getNombre());
        dto.setNombres(entity.getNombres());
        dto.setApellidoPaterno(entity.getApellidoPaterno());
        dto.setApellidoMaterno(entity.getApellidoMaterno());
        dto.setIdGenero(entity.getGenero().getIdGenero());
        dto.setDesGenero(entity.getGenero().getNombre());
        dto.setIdEstadoCivil(entity.getEstadoCivil().getIdEstadoCivil());
        dto.setDesEstadoCivil(entity.getEstadoCivil().getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setEmail(entity.getEmail());
        dto.setHijos(entity.getHijos());
        dto.setIdCargo(entity.getCargo().getIdCargo());
        dto.setDesCargo(entity.getCargo().getNombre());
        dto.setFecNacimiento(entity.getFecNacimiento());
        dto.setFecIngreso(entity.getFecIngreso());
        dto.setIdSituacion(entity.getSituacion().getIdSituacion());
        dto.setDesSituacion(entity.getSituacion().getNombre());
        dto.setIdSistemaPension(entity.getSistemaPension().getIdSistemaPension());
        dto.setDesSistemaPension(entity.getSistemaPension().getNombre());
        return dto;
    }
}
