package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.AsistenciaTrabajadorDTO;
import com.planilla_DAWI.cibertec.Dto.AsistenciaTrabajadorResponse;
import com.planilla_DAWI.cibertec.Service.AsistenciaTrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaTrabajadorController {

    @Autowired
    private AsistenciaTrabajadorService service;

    @GetMapping
    public ResponseEntity<List<AsistenciaTrabajadorResponse>> cargarAsistencia(
            @RequestParam(required = false) Integer año,
            @RequestParam(required = false) Integer mes) {

        if (año == null) año = java.time.Year.now().getValue();
        if (mes == null) mes = java.time.MonthDay.now().getMonthValue();

        return ResponseEntity.ok(service.buscarPorPeriodo(año, mes));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AsistenciaTrabajadorResponse>> buscarAsistencia(
            @RequestParam Integer año,
            @RequestParam Integer mes) {
        return ResponseEntity.ok(service.buscarPorPeriodo(año, mes));
    }

    @GetMapping("/descargar-excel")
    public ResponseEntity<byte[]> descargarExcel(
            @RequestParam Integer año,
            @RequestParam Integer mes) {

        List<AsistenciaTrabajadorResponse> datos = service.buscarPorPeriodo(año, mes);
        byte[] excelBytes = service.generarExcel(año, mes, datos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=asistencias_" + año + "_" + mes + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelBytes);
    }

    @PostMapping("/cargar-excel")
    public ResponseEntity<List<AsistenciaTrabajadorResponse>> cargarExcel(
            @RequestParam Integer año,
            @RequestParam Integer mes,
            @RequestParam("archivo") MultipartFile archivo) {

        return ResponseEntity.ok(service.procesarExcel(archivo, año, mes));
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> grabarAsistencia(
            @RequestBody List<AsistenciaTrabajadorDTO> asistencias) {

        boolean resultado = service.guardarAsistencias(asistencias);
        if (resultado) {
            return ResponseEntity.ok("Asistencias guardadas correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo guardar las asistencias");
        }
    }
}