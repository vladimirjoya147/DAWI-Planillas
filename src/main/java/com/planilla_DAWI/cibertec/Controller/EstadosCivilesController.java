package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.EstadoCivilDTO;
import com.planilla_DAWI.cibertec.Service.EstadoCivilService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/estados-civiles")
@CrossOrigin(origins = "*")
public class EstadosCivilesController {

    private final EstadoCivilService estadoCivilService;

    @Autowired
    public EstadosCivilesController(EstadoCivilService estadoCivilService) {
        this.estadoCivilService = estadoCivilService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarEstadosCiviles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "TODOS") EstadoEnum estado) {

        try {
            Page<EstadoCivilDTO> estadosCivilesPage = estadoCivilService.listarEstadosCiviles(page, estado);

            Map<String, Object> response = new HashMap<>();
            response.put("content", estadosCivilesPage.getContent());
            response.put("currentPage", page);
            response.put("totalPages", estadosCivilesPage.getTotalPages());
            response.put("pageSize", estadosCivilesPage.getSize());
            response.put("totalItems", estadosCivilesPage.getTotalElements());
            response.put("estado", estado);
            response.put("success", true);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al obtener los estados civiles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerEstadoCivil(@PathVariable Integer id) {
        try {
            EstadoCivilDTO estadoCivilDTO = estadoCivilService.obtenerPorId(id);
            Map<String, Object> response = new HashMap<>();

            if (estadoCivilDTO == null) {
                response.put("success", false);
                response.put("message", "Estado civil no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("success", true);
            response.put("data", estadoCivilDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al obtener el estado civil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearEstadoCivil(
            @Valid @RequestBody EstadoCivilDTO estadoCivilDTO,
            BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            response.put("success", false);
            response.put("message", "Datos de entrada inválidos");
            response.put("errors", result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            EstadoCivilDTO nuevoEstadoCivil = estadoCivilService.crear(estadoCivilDTO);
            response.put("success", true);
            response.put("message", "Estado civil creado exitosamente");
            response.put("data", nuevoEstadoCivil);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear el estado civil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEstadoCivil(
            @PathVariable Integer id,
            @Valid @RequestBody EstadoCivilDTO estadoCivilDTO,
            BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            response.put("success", false);
            response.put("message", "Datos de entrada inválidos");
            response.put("errors", result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            // Verificar si existe el estado civil
            EstadoCivilDTO estadoCivilExistente = estadoCivilService.obtenerPorId(id);
            if (estadoCivilExistente == null) {
                response.put("success", false);
                response.put("message", "Estado civil no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Asegurar que el ID del path coincida con el del objeto
            estadoCivilDTO.setIdEstadoCivil(id);

            EstadoCivilDTO estadoCivilActualizado = estadoCivilService.actualizar(estadoCivilDTO);
            response.put("success", true);
            response.put("message", "Estado civil actualizado exitosamente");
            response.put("data", estadoCivilActualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el estado civil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Map<String, Object>> cambiarEstado(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Verificar si existe el estado civil
            EstadoCivilDTO estadoCivilExistente = estadoCivilService.obtenerPorId(id);
            if (estadoCivilExistente == null) {
                response.put("success", false);
                response.put("message", "Estado civil no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            estadoCivilService.cambiarEstado(id);
            response.put("success", true);
            response.put("message", "Estado del estado civil cambiado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al cambiar el estado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarEstadoCivil(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Verificar si existe el estado civil
            EstadoCivilDTO estadoCivilExistente = estadoCivilService.obtenerPorId(id);
            if (estadoCivilExistente == null) {
                response.put("success", false);
                response.put("message", "Estado civil no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Si tu servicio tiene un método de eliminación física, úsalo aquí
            // estadoCivilService.eliminar(id);

            // Por ahora, usamos cambiar estado como eliminación lógica
            estadoCivilService.cambiarEstado(id);
            response.put("success", true);
            response.put("message", "Estado civil eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar el estado civil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}