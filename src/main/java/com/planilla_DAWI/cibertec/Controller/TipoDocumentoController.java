package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.TipoDocumentoDTO;
import com.planilla_DAWI.cibertec.Service.TipoDocumentoService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipos-documento")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService service;

    @GetMapping("/listar")
    public ResponseEntity<Page<TipoDocumentoDTO>> listar(
            @RequestParam(required = false, defaultValue = "TODOS") EstadoEnum estado,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorEstado(estado, pageable));
    }

    @GetMapping("/obtenerById/{id}")
    public ResponseEntity<TipoDocumentoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping("/Insertar")
    public ResponseEntity<TipoDocumentoDTO> crear(@RequestBody TipoDocumentoDTO dto) {
        return ResponseEntity.ok(service.insertar(dto));
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<TipoDocumentoDTO> actualizar(
            @PathVariable Integer id, @RequestBody TipoDocumentoDTO dto) {
        dto.setIdTipoDocumento(id);
        return ResponseEntity.ok(service.actualizar(dto));
    }

    @PatchMapping("/cambiarEstado/{id}")
    public ResponseEntity<Integer> cambiarEstado(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cambiarEstado(id));
    }
}