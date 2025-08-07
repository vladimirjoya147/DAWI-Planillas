package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.EstadoCivilDTO;
import com.planilla_DAWI.cibertec.Entity.EstadoCivil;
import com.planilla_DAWI.cibertec.Repository.EstadoCivilRepository;
import com.planilla_DAWI.cibertec.Service.EstadoCivilService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class EstadoCivilServiceImpl implements EstadoCivilService {
    private static final int PAGE_SIZE = 10;
    private final EstadoCivilRepository repository;

    @Autowired
    public EstadoCivilServiceImpl(EstadoCivilRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<EstadoCivilDTO> listarEstadosCiviles(int page, EstadoEnum estado) {



        return repository.findByEstado(estado.getValor(), PageRequest.of(page - 1, PAGE_SIZE))
                .map(this::convertToDto);
    }

    @Override
    public EstadoCivilDTO obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Transactional
    @Override
    public EstadoCivilDTO crear(EstadoCivilDTO dto) {
        EstadoCivil entity = convertToEntity(dto);
        entity.setActivo(true);
        entity.setFecCreacion(LocalDateTime.now());
        return convertToDto(repository.save(entity));
    }

    @Transactional
    @Override
    public EstadoCivilDTO actualizar(EstadoCivilDTO dto) {
        EstadoCivil entity = convertToEntity(dto);
        entity.setFecUltimaModificacion(LocalDateTime.now());
        return convertToDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void cambiarEstado(Integer id) {
        repository.cambiarEstado(id,new Date());
    }

    private EstadoCivilDTO convertToDto(EstadoCivil entity) {
        EstadoCivilDTO dto = new EstadoCivilDTO();
        dto.setIdEstadoCivil(entity.getIdEstadoCivil());
        dto.setNombre(entity.getNombre());
        dto.setActivo(entity.getActivo());
        dto.setFecCreacion(convertToDate( entity.getFecCreacion()));
        dto.setFecUltimaModificacion(convertToDate( entity.getFecUltimaModificacion()));
        return dto;
    }

    private EstadoCivil convertToEntity(EstadoCivilDTO dto) {
        EstadoCivil entity = new EstadoCivil();
        entity.setIdEstadoCivil(dto.getIdEstadoCivil());
        entity.setNombre(dto.getNombre());
        entity.setActivo(dto.getActivo());
        entity.setFecCreacion( convertToLocalDateTime(dto.getFecCreacion()));
        entity.setFecUltimaModificacion(convertToLocalDateTime(dto.getFecUltimaModificacion()));
        return entity;
    }
    // Métodos de ayuda para la conversión
    private Date convertToDate(LocalDateTime localDateTime) {
        return localDateTime != null ?
                Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) :
                null;
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date != null ?
                LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()) :
                null;
    }
}