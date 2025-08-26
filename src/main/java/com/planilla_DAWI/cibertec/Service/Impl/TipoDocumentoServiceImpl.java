package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.TipoDocumentoDTO;
import com.planilla_DAWI.cibertec.Entity.TipoDocumento;
import com.planilla_DAWI.cibertec.Mappers.TipoDocumentoMapper;
import com.planilla_DAWI.cibertec.Repository.TipoDocumentoRepository;
import com.planilla_DAWI.cibertec.Service.TipoDocumentoService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository repository;

    @Override
    public Page<TipoDocumentoDTO> buscarPorEstado(EstadoEnum estado, String texto, Pageable pageable) {
        return repository.findByEstado(estado.getValor(), texto, pageable).map(TipoDocumentoMapper::toDTO);
    }

    @Override
    @Transactional
    public TipoDocumentoDTO insertar(TipoDocumentoDTO dto) {
        TipoDocumento entity = TipoDocumentoMapper.toEntity(dto);
        entity.setActivo(true);
        entity.setFecCreacion(LocalDateTime.now());
        return TipoDocumentoMapper.toDTO( repository.save(entity));
    }

    @Override
    @Transactional
    public TipoDocumentoDTO actualizar(TipoDocumentoDTO dto) {
        repository.findById(dto.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        TipoDocumento entity =TipoDocumentoMapper.toEntity(dto);
        entity.setNombre(dto.getNombre());
        entity.setFecUltimaModificacion(LocalDateTime.now());
        return TipoDocumentoMapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public int cambiarEstado(Integer id) {
        return repository.cambiarEstado(id, LocalDateTime.now());
    }

    @Override
    public TipoDocumentoDTO obtenerPorId(Integer id) {
        return repository.findById(id).map(TipoDocumentoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
    }
}
