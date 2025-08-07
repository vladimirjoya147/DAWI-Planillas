package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.EstadoCivilDTO;
import com.planilla_DAWI.cibertec.Entity.EstadoCivil;
import com.planilla_DAWI.cibertec.Repository.EstadoCivilRepository;
import com.planilla_DAWI.cibertec.Service.EstadoCivilService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class EstadoCivilServiceImpl implements EstadoCivilService {

    private final EstadoCivilRepository estadoCivilRepository;
    private final ModelMapper modelMapper;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public EstadoCivilServiceImpl(EstadoCivilRepository estadoCivilRepository, ModelMapper modelMapper) {
        this.estadoCivilRepository = estadoCivilRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<EstadoCivilDTO> listarEstadosCiviles(int page, EstadoEnum estado) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);
        Page<EstadoCivil> estadosCivilesPage = estadoCivilRepository.findByEstado(estado.getValor(), pageable);

        return estadosCivilesPage.map(estadoCivil -> modelMapper.map(estadoCivil, EstadoCivilDTO.class));
    }

    @Override
    public EstadoCivilDTO obtenerPorId(Integer id) {
        Optional<EstadoCivil> estadoCivil = estadoCivilRepository.findById(id);
        return estadoCivil.map(ec -> modelMapper.map(ec, EstadoCivilDTO.class)).orElse(null);
    }

    @Override
    @Transactional
    public EstadoCivilDTO crear(EstadoCivilDTO estadoCivilDTO) {
        EstadoCivil estadoCivil = modelMapper.map(estadoCivilDTO, EstadoCivil.class);
        estadoCivil.setActivo(true);
        estadoCivil.setFecCreacion(new Date());
        estadoCivil = estadoCivilRepository.save(estadoCivil);
        return modelMapper.map(estadoCivil, EstadoCivilDTO.class);
    }

    @Override
    @Transactional
    public EstadoCivilDTO actualizar(EstadoCivilDTO estadoCivilDTO) {
        EstadoCivil estadoCivil = modelMapper.map(estadoCivilDTO, EstadoCivil.class);
        estadoCivil.setFecUltimaModificacion(new Date());
        estadoCivil = estadoCivilRepository.save(estadoCivil);
        return modelMapper.map(estadoCivil, EstadoCivilDTO.class);
    }

    @Override
    @Transactional
    public void cambiarEstado(Integer id) {
        estadoCivilRepository.cambiarEstado(id, new Date());
    }
}
