package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.CargoDTO;

import com.planilla_DAWI.cibertec.Service.CargoService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    @Autowired
    private CargoService service;

    @GetMapping("/listar")
    public ResponseEntity<Page<CargoDTO>> listar(
            @RequestParam(required = false, defaultValue = "TODOS") EstadoEnum estado,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorEstado(estado, pageable));
    }

    @GetMapping("/obtenerById/{id}")
    public ResponseEntity<CargoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping("/Insertar")
    public ResponseEntity<CargoDTO> crear(@RequestBody CargoDTO dto) {
        return ResponseEntity.ok(service.insertar(dto));
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<CargoDTO> actualizar(
            @PathVariable Integer id, @RequestBody CargoDTO dto) {
        dto.setIdCargo(id);
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @PatchMapping("/cambiarEstado/{id}")
    public ResponseEntity<Integer> cambiarEstado(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cambiarEstado(id));
    }
}
