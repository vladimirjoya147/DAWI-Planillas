package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.PlanillaMensualDTO;
import com.planilla_DAWI.cibertec.Dto.PlanillaMensualResponse;
import com.planilla_DAWI.cibertec.Service.PlanillaMensualService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planillamensual")
public class PlanillaController {

    private final PlanillaMensualService planillaMensualService;

    public PlanillaController(PlanillaMensualService planillaMensualService) {
        this.planillaMensualService = planillaMensualService;
    }

    @GetMapping
    public ResponseEntity<List<PlanillaMensualDTO>> listarPlanillaMensual(@RequestParam(value = "anio") Integer anio,
                                                                          @RequestParam(value = "mes")  Integer mes){
        return ResponseEntity.ok(planillaMensualService.listarPlanillas(anio,mes));
    }

    @GetMapping("/buscar/")
    public ResponseEntity<?> ObtenerPlanillaPorDocumento(@RequestParam(value = "año") Integer anio,
                                                         @RequestParam(value = "mes")  Integer mes,
                                                         @RequestParam(value="documento") String documento){
        return ResponseEntity.ok(planillaMensualService.ObtenerPlanillaPorDocumento(documento, anio,mes));
    }

    @GetMapping("/calculo/")
    public ResponseEntity<List<PlanillaMensualResponse>> calcularPlanillas (@RequestParam(value = "año")Integer anio,
                                                                            @RequestParam(value = "mes") Integer mes){
        return ResponseEntity.ok(planillaMensualService.calcularPlanilla(anio, mes));
    }

    @PostMapping("/ingresar")
    public  ResponseEntity<String> insertarPlaneillas (@RequestBody List<PlanillaMensualResponse> planResponse){
        boolean resultado = planillaMensualService.insertarListaCalculo(planResponse);

        if (resultado) {
            return ResponseEntity.ok("Lista de planillas insertada correctamente");
        } else {
            return ResponseEntity.badRequest().body("La lista está vacía o es nula");
        }
    }
}
