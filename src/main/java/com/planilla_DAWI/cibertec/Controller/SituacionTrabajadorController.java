package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.SituacionTrabajadorDTO;
import com.planilla_DAWI.cibertec.Service.SituacionTrabajadorService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/situaciones-trabajador")
public class SituacionTrabajadorController {

    @Autowired
    private SituacionTrabajadorService service;

    @GetMapping("/listar")
    public ResponseEntity<Page<SituacionTrabajadorDTO>> listar(
            @RequestParam(required = false, defaultValue = "TODOS") EstadoEnum estado,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorEstado(estado, pageable));
    }

    @GetMapping("/obtenerById/{id}")
    public ResponseEntity<SituacionTrabajadorDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping("/Insertar")
    public ResponseEntity<SituacionTrabajadorDTO> crear(@RequestBody SituacionTrabajadorDTO dto) {
        return ResponseEntity.ok(service.insertar(dto));
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<SituacionTrabajadorDTO> actualizar(
            @PathVariable Integer id, @RequestBody SituacionTrabajadorDTO dto) {
        dto.setIdSituacion(id);
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @PatchMapping("/cambiarEstado/{id}")
    public ResponseEntity<Integer> cambiarEstado(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cambiarEstado(id));
    }
}
