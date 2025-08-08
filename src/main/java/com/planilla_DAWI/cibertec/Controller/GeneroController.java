package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.GeneroDTO;
import com.planilla_DAWI.cibertec.Service.GeneroService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generos")
public class GeneroController {

    @Autowired
    private GeneroService service;

    @GetMapping
    public ResponseEntity<Page<GeneroDTO>> listar(
            @RequestParam(required = false, defaultValue = "TODOS") EstadoEnum estado,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorEstado(estado, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<GeneroDTO> crear(@RequestBody GeneroDTO dto) {
        return ResponseEntity.ok(service.insertar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> actualizar(
            @PathVariable Integer id, @RequestBody GeneroDTO dto) {
        dto.setIdGenero(id);
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @PatchMapping("/{id}/cambiar-estado")
    public ResponseEntity<Integer> cambiarEstado(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cambiarEstado(id));
    }
}