package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.CargoDTO;
import com.planilla_DAWI.cibertec.Entity.Cargo;
import com.planilla_DAWI.cibertec.Mappers.CargoMapper;
import com.planilla_DAWI.cibertec.Repository.CargoRepository;
import com.planilla_DAWI.cibertec.Service.CargoService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoRepository repository;

    @Override
    public Page<CargoDTO> buscarPorEstado(EstadoEnum estado,String texto ,Pageable pageable) {
        return repository.findByEstado(estado.getValor(),texto, pageable).map(CargoMapper::toDTO);
    }

    @Override
    @Transactional
    public CargoDTO insertar(CargoDTO dto) {
        Cargo entity = CargoMapper.toEntity(dto);
        entity.setActivo(true);
        entity.setFecCreacion(LocalDateTime.now());
        return CargoMapper.toDTO( repository.save(entity));
    }

    @Override
    @Transactional
    public CargoDTO actualizar(CargoDTO dto) {
          repository.findById(dto.getIdCargo())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));
        Cargo entity=CargoMapper.toEntity(dto);
        entity.setFecUltimaModificacion(LocalDateTime.now());
        return CargoMapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public int cambiarEstado(Integer id) {
        return repository.cambiarEstado(id, LocalDateTime.now());
    }

    @Override
    public CargoDTO obtenerPorId(Integer id) {
        return repository.findById(id).map(CargoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));
    }
}