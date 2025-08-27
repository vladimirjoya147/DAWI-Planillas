package com.planilla_DAWI.cibertec.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Trabajadores")
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrabajador;
    @ManyToOne
    @JoinColumn(name = "idTipoDocumento", nullable = false)
    private TipoDocumento tipoDocumento;
    @Column(nullable = false, length = 11)
    private String documento;
    @Column(nullable = false, length = 50)
    private String nombres;
    @Column(nullable = false, length = 50)
    private String apellidoPaterno;
    @Column(nullable = false, length = 50)
    private String apellidoMaterno;
    @ManyToOne
    @JoinColumn(name = "idGenero", nullable = false)
    private Genero genero;
    @ManyToOne
    @JoinColumn(name = "idEstadoCivil", nullable = false)
    private EstadoCivil estadoCivil;
    @Column(nullable = false, length = 120)
    private String direccion;
    @Column(length = 120)
    private String email;
    @Column(nullable = false)
    private int hijos = 0;

    @ManyToOne
    @JoinColumn(name = "idCargo", nullable = false)
    private Cargo cargo;

    @Column(nullable = false)
    private LocalDate fecNacimiento;

    @Column(nullable = false)
    private LocalDate fecIngreso;

    @ManyToOne
    @JoinColumn(name = "idSituacion", nullable = false)
    private SituacionTrabajador situacion;

    @ManyToOne
    @JoinColumn(name = "idSistemaPension", nullable = false)
    private SistemaPension sistemaPension;

    @Lob
    private byte[] foto;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private LocalDateTime fecCreacion = LocalDateTime.now();

    private LocalDateTime fecUltimaModificacion;
}
