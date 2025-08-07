package com.planilla_DAWI.cibertec.Controller;

import com.planilla_DAWI.cibertec.Dto.EstadoCivilDTO;
import com.planilla_DAWI.cibertec.Service.EstadoCivilService;
import com.planilla_DAWI.cibertec.Utils.Enums.EstadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estados-civiles")
public class EstadosCivilesController {

    private final EstadoCivilService estadoCivilService;

    @Autowired
    public EstadosCivilesController(EstadoCivilService estadoCivilService) {
        this.estadoCivilService = estadoCivilService;
    }

    @GetMapping
    public String listarEstadosCiviles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "TODOS") EstadoEnum estado,
            Model model) {

        Page<EstadoCivilDTO> estadosCivilesPage = estadoCivilService.listarEstadosCiviles(page, estado);

        model.addAttribute("estadosCiviles", estadosCivilesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", estadosCivilesPage.getTotalPages());
        model.addAttribute("pageSize", estadosCivilesPage.getSize());
        model.addAttribute("totalItems", estadosCivilesPage.getTotalElements());
        model.addAttribute("estado", estado);

        return "estados-civiles/list";
    }

    @GetMapping("/manage")
    public String mostrarFormulario(
            @RequestParam(required = false) Integer id,
            Model model) {

        if (id == null || id == 0) {
            model.addAttribute("estadoCivil", new EstadoCivilDTO());
        } else {
            EstadoCivilDTO estadoCivilDTO = estadoCivilService.obtenerPorId(id);
            if (estadoCivilDTO == null) {
                return "redirect:/estados-civiles";
            }
            model.addAttribute("estadoCivil", estadoCivilDTO);
        }

        return "estados-civiles/manage";
    }

    @PostMapping("/manage")
    public String guardarEstadoCivil(
            @ModelAttribute("estadoCivil") EstadoCivilDTO estadoCivilDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "estados-civiles/manage";
        }

        try {
            if (estadoCivilDTO.getIdEstadoCivil() == null || estadoCivilDTO.getIdEstadoCivil() == 0) {
                estadoCivilService.crear(estadoCivilDTO);
                redirectAttributes.addFlashAttribute("successMessage", "Estado civil creado exitosamente");
            } else {
                estadoCivilService.actualizar(estadoCivilDTO);
                redirectAttributes.addFlashAttribute("successMessage", "Estado civil actualizado exitosamente");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "estados-civiles/manage";
        }

        return "redirect:/estados-civiles";
    }

    @PostMapping("/delete")
    public String cambiarEstado(
            @RequestParam Integer id,
            RedirectAttributes redirectAttributes) {

        try {
            estadoCivilService.cambiarEstado(id);
            redirectAttributes.addFlashAttribute("successMessage", "Estado del estado civil cambiado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/estados-civiles";
    }
}