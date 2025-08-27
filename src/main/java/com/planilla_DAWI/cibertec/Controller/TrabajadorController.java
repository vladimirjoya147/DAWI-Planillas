package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.TrabajadorDTO;
import com.planilla_DAWI.cibertec.Entity.Trabajador;
import com.planilla_DAWI.cibertec.Service.TrabajadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Trabajador")
public class TrabajadorController {

    private TrabajadorService trabajadorService;

    @GetMapping("/listarActivo")
    public List<Trabajador> listarTrabajadoresActivos() {
        return trabajadorService.listarTrabajadoresActivos();
    }

    @GetMapping("/listarActivo")
    public List<TrabajadorDTO> listarTrabajadorBusqueda(String busqueda, int estado) {
        return trabajadorService.listarTrabajadorBusqueda(busqueda, estado);
    }
    @GetMapping("/obtenerById/{id}")
    public TrabajadorDTO trabajadorPorId(Integer id) {
        return trabajadorService.trabajadorPorId(id);
    }

    @PostMapping("/Insertar")
    public int insertar(@RequestBody Trabajador trabajador) {
        return trabajadorService.insertar(trabajador);
    }

    @PatchMapping("/cambiarEstado/{id}")
    public Integer cambiarEstado(@PathVariable Integer id) {
        return trabajadorService.cambiarEstado(id);
    }
}
