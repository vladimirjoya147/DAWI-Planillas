package com.planilla_DAWI.cibertec.Service.Impl;

import com.planilla_DAWI.cibertec.Dto.PlanillaMensualDTO;
import com.planilla_DAWI.cibertec.Dto.PlanillaMensualResponse;
import com.planilla_DAWI.cibertec.Dto.PlanillaPorDocumentoDTO;
import com.planilla_DAWI.cibertec.Entity.*;
import com.planilla_DAWI.cibertec.Mappers.PlanillaMapper;
import com.planilla_DAWI.cibertec.Repository.*;
import com.planilla_DAWI.cibertec.Service.PlanillaMensualService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlanillaMensualServiceImpl implements PlanillaMensualService {

    private final PlanillaRepository planillaRepository;
    private final ParametroRepository parametroRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final IngresoTrabajadorRepository ingresoTrabajadorRepository;
    private final AsistenciaTrabajadorRepository asistenciaTrabajadorRepository;
    private final SistemaPensionRepository sistemaPensionRepository;

    public PlanillaMensualServiceImpl(PlanillaRepository planillaRepository, ParametroRepository parametroRepository, TrabajadorRepository trabajadorRepository, IngresoTrabajadorRepository ingresoTrabajadorRepository, AsistenciaTrabajadorRepository asistenciaTrabajadorRepository, SistemaPensionRepository sistemaPensionRepository) {
        this.planillaRepository = planillaRepository;
        this.parametroRepository = parametroRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.ingresoTrabajadorRepository = ingresoTrabajadorRepository;
        this.asistenciaTrabajadorRepository = asistenciaTrabajadorRepository;
        this.sistemaPensionRepository = sistemaPensionRepository;
    }

    @Override
    public List<PlanillaMensualDTO> listarPlanillas(Integer anio, Integer mes) {
       List<PlanillaMensual> planillaMensual = planillaRepository.findByAnioAndMes(anio, mes);
        return PlanillaMapper.INSTANCE.PlanillaListToDTO(planillaMensual);
    }

    @Override
    public PlanillaPorDocumentoDTO ObtenerPlanillaPorDocumento(String documento, Integer anio, Integer mes) {
        PlanillaMensual planillaMensual = planillaRepository.findPlanillaActivaPorDocumento(anio, mes , documento);
        return PlanillaMapper.INSTANCE.DocumentoToDTO(planillaMensual);
    }

    @Override
    public List<PlanillaMensualResponse> calcularPlanilla(Integer anio, Integer mes) {

        Parametro parametro = parametroRepository.findParametro();
        List<Trabajador> trabajadores = trabajadorRepository.listarTrabajadoresActivos();

        List<IngresoTrabajador> ingresosActivos = ingresoTrabajadorRepository.ListarIngresosActivos();
        List<AsistenciaTrabajador> listarAsistencia = asistenciaTrabajadorRepository.findByPeriodoEntidad(anio, mes);
        List<SistemaPension> sistemaPensiones = sistemaPensionRepository.findAll();

        int diasMes = YearMonth.of(anio, mes).lengthOfMonth();
        List<PlanillaMensual> planillas = new ArrayList<>();

        for (Trabajador t : trabajadores) {

            PlanillaMensual pla = new PlanillaMensual();

            AsistenciaTrabajador itemAsistencia = listarAsistencia.stream()
                    .filter(a -> a.getTrabajador().getIdTrabajador().equals(t.getIdTrabajador()))
                    .findFirst()
                    .orElse(null);

            IngresoTrabajador itemIngreso = ingresosActivos.stream()
                    .filter(i -> i.getTrabajador().getIdTrabajador().equals(t.getIdTrabajador()))
                    .findFirst()
                    .orElse(null);

            int diasCalculo = (itemAsistencia != null ? itemAsistencia.getDiasLaborales() : 0)
                    + (itemAsistencia != null ? itemAsistencia.getDiasDescanso() : 0);

            pla.setAnio(anio);
            pla.setMes(mes);
            pla.setTrabajador(t);
            pla.setSituacion(t.getSituacion());
            pla.setCargo(t.getCargo());
            pla.setApellido(t.getApellidoPaterno() + " " + t.getApellidoMaterno());
            pla.setNombre(t.getNombres());
            pla.setSistemaPension(t.getSistemaPension());
            pla.setEstadoCivil(t.getEstadoCivil());
            pla.setHijos((short) t.getHijos());
            pla.setFechaIngreso(t.getFecIngreso());

            pla.setSueldoBasico(itemIngreso != null ? itemIngreso.getRemuneracion() : BigDecimal.ZERO);

            pla.setPorcHoraExtra1(parametro.getPorcExtra1());
            pla.setPorcHoraExtra2(parametro.getPorcExtra2());
            pla.setPorcDescansoTrab(BigDecimal.valueOf(itemAsistencia.getDiasDescanso()));
            pla.setPorcFeriadoTrab(BigDecimal.valueOf(itemAsistencia.getDiasFeriados()));
            pla.setPorcAsigFamiliar(parametro.getPorcAsigancionFamiliar());

            pla.setNHorasNormal(itemAsistencia != null
                    ? BigDecimal.valueOf(itemAsistencia.getDiasLaborales() * 8)
                    : BigDecimal.ZERO);

            pla.setNHorasExtra1(itemAsistencia != null ? itemAsistencia.getHorasExtra25() : BigDecimal.ZERO);
            pla.setNHorasExtra2(itemAsistencia != null ? itemAsistencia.getHorasExtra35() : BigDecimal.ZERO);
            pla.setNDiasTrab((short) (itemAsistencia != null ? itemAsistencia.getDiasLaborales() : 0));
            pla.setNDiasDescansos((short) (itemAsistencia != null ? itemAsistencia.getDiasDescanso() : 0));
            pla.setNFeriadosTrab((short) (itemAsistencia != null ? itemAsistencia.getDiasFeriados() : 0));
            pla.setNDescansosTrab((short) 0);
            pla.setNDiasInasistencias((short) (itemAsistencia != null ? itemAsistencia.getDiasInasistencia() : 0));

            BigDecimal sueldoBasico = itemIngreso != null ? itemIngreso.getRemuneracion() : BigDecimal.ZERO;
            BigDecimal vale = itemIngreso != null ? itemIngreso.getVale() : BigDecimal.ZERO;

            BigDecimal haberBasico = sueldoBasico
                    .divide(BigDecimal.valueOf(diasMes), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(diasCalculo));

            BigDecimal valesEmpleado = vale
                    .divide(BigDecimal.valueOf(diasMes), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(diasCalculo));

            BigDecimal valorHora = sueldoBasico
                    .divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_UP);

            BigDecimal vHorasExtra1 = valorHora.multiply(
                            BigDecimal.ONE.add(parametro.getPorcExtra1()))
                    .multiply(pla.getNHorasExtra1());

            BigDecimal vHorasExtra2 = valorHora.multiply(
                            BigDecimal.ONE.add(parametro.getPorcExtra2()))
                    .multiply(pla.getNHorasExtra2());

            BigDecimal vAsigFamiliar = t.getHijos() > 0
                    ? sueldoBasico.multiply(parametro.getPorcAsigancionFamiliar())
                    : BigDecimal.ZERO;

            BigDecimal vFeriadoTrab = valorHora.multiply(BigDecimal.valueOf(8))
                    .multiply(BigDecimal.valueOf(2));

            pla.setHaberBasico(haberBasico);
            pla.setValesEmpleado(valesEmpleado);
            pla.setVHorasExtra1(vHorasExtra1);
            pla.setVHorasExtra2(vHorasExtra2);
            pla.setVAsigFamiliar(vAsigFamiliar);
            pla.setVFeriadoTrab(vFeriadoTrab);

            pla.setTotalIngreso(pla.getHaberBasico().add(pla.getValesEmpleado()).add(pla.getVHorasExtra2()).add(pla.getVHorasExtra1())
                    .add(pla.getVAsigFamiliar()).add(pla.getVFeriadoTrab()));

            SistemaPension sp = sistemaPensiones.stream()
                    .filter(s -> s.getIdSistemaPension().equals(t.getSistemaPension().getIdSistemaPension()))
                    .findFirst()
                    .orElse(null);

            if (sp != null) {
                pla.setPorcAporte(sp.getAporte());
                pla.setAporte(pla.getTotalIngreso().multiply(sp.getAporte().divide(BigDecimal.valueOf(100))));

                pla.setPorcComision(sp.getComision());
                pla.setComision(pla.getTotalIngreso().multiply(sp.getComision().divide(BigDecimal.valueOf(100))));

                pla.setPorcPrima(sp.getPrima());
                pla.setPrima(pla.getTotalIngreso().multiply(sp.getPrima().divide(BigDecimal.valueOf(100))));
            }

            planillas.add(pla);
        }

        // 🔄 Convertimos a Response con el mapper
        return PlanillaMapper.INSTANCE.toDtoList(planillas);
    }

    @Override
    public boolean insertarListaCalculo(List<PlanillaMensualResponse> planResponse) {
        if (planResponse == null || planResponse.isEmpty()) {
            return false;
        }
        int rows=planillaRepository.eliminarPlanillas(planResponse.get(0).getAnio(),planResponse.get(0).getMes());
        System.out.println("Registros eliminados: " + rows);
        List<PlanillaMensual> entidades =PlanillaMapper.INSTANCE.toEntityList(planResponse);
        planillaRepository.saveAll(entidades);
        return true;
    }

}
