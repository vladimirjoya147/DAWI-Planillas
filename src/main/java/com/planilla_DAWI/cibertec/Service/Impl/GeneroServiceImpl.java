package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.GeneroDTO;
import com.planilla_DAWI.cibertec.Entity.Genero;
import com.planilla_DAWI.cibertec.Mappers.GeneroMapper;
import com.planilla_DAWI.cibertec.Repository.GeneroRepository;
import com.planilla_DAWI.cibertec.Service.GeneroService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository repository;

    @Override
    public Page<GeneroDTO> buscarPorEstado(EstadoEnum estado, Pageable pageable) {
        return repository.findByEstado(estado.getValor(), pageable).map(GeneroMapper::toDTO);
    }

    @Override
    @Transactional
    public GeneroDTO insertar(GeneroDTO dto) {
        Genero entity = GeneroMapper.toEntity(dto);
        entity.setActivo(true);
        entity.setFecCreacion(LocalDateTime.now());
        return GeneroMapper.toDTO( repository.save(entity));
    }

    @Override
    @Transactional
    public GeneroDTO actualizar(GeneroDTO dto) {
       repository.findById(dto.getIdGenero())
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));
       Genero entity =GeneroMapper.toEntity(dto);
        entity.setNombre(dto.getNombre());
        entity.setFecUltimaModificacion(LocalDateTime.now());
        return GeneroMapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public int cambiarEstado(Integer id) {
        return repository.cambiarEstado(id, new Date());
    }

    @Override
    public GeneroDTO obtenerPorId(Integer id) {
        return repository.findById(id).map(GeneroMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));
    }
}