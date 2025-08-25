package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.SituacionTrabajadorDTO;
import com.planilla_DAWI.cibertec.Entity.SituacionTrabajador;
import com.planilla_DAWI.cibertec.Mappers.SituacionTrabajadorMapper;
import com.planilla_DAWI.cibertec.Mappers.TipoDocumentoMapper;
import com.planilla_DAWI.cibertec.Repository.SituacionTrabajadorRepository;
import com.planilla_DAWI.cibertec.Service.SituacionTrabajadorService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class SituacionTrabajadorServiceImpl implements SituacionTrabajadorService {

    @Autowired
    private SituacionTrabajadorRepository repository;

    @Override
    public Page<SituacionTrabajadorDTO> buscarPorEstado(EstadoEnum estado, String texto, Pageable pageable) {
        return repository.findByEstado(estado.getValor(), texto, pageable).map(SituacionTrabajadorMapper::toDTO);
    }

    @Override
    @Transactional
    public SituacionTrabajadorDTO insertar(SituacionTrabajadorDTO dto) {
        SituacionTrabajador entity = SituacionTrabajadorMapper.toEntity(dto);
        entity.setActivo(true);
        entity.setFecCreacion(LocalDateTime.now());
        return SituacionTrabajadorMapper.toDTO( repository.save(entity));
    }

    @Override
    @Transactional
    public SituacionTrabajadorDTO actualizar(SituacionTrabajadorDTO dto) {
     repository.findById(dto.getIdSituacion())
                .orElseThrow(() -> new RuntimeException("Situación de trabajador no encontrada"));
        SituacionTrabajador entity = SituacionTrabajadorMapper.toEntity(dto);
         entity.setFecUltimaModificacion(LocalDateTime.now());
        return SituacionTrabajadorMapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public int cambiarEstado(Integer id) {
        return repository.cambiarEstado(id, LocalDateTime.now());
    }

    @Override
    public SituacionTrabajadorDTO obtenerPorId(Integer id) {
        return repository.findById(id).map(SituacionTrabajadorMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Situación de trabajador no encontrada"));
    }
}
