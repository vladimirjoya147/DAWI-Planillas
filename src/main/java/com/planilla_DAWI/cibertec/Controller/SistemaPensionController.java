package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.SistemaPensionDTO;
import com.planilla_DAWI.cibertec.Service.SistemaPensionService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistemas-pension")
public class SistemaPensionController {

    @Autowired
    private SistemaPensionService service;

    @GetMapping
    public ResponseEntity<Page<SistemaPensionDTO>> listar(
            @RequestParam(required = false, defaultValue = "TODOS") EstadoEnum estado,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorEstado(estado, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SistemaPensionDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SistemaPensionDTO> crear(@RequestBody SistemaPensionDTO dto) {
        return ResponseEntity.ok(service.insertar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SistemaPensionDTO> actualizar(
            @PathVariable Integer id, @RequestBody SistemaPensionDTO dto) {
        dto.setIdSistemaPension(id);
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @PatchMapping("/{id}/cambiar-estado")
    public ResponseEntity<Integer> cambiarEstado(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cambiarEstado(id));
    }
}
