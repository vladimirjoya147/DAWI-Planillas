package com.planilla_DAWI.cibertec.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SistemaPensiones")
public class SistemaPension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSistemaPension;

    @Column(nullable = false, length = 50)
    private String nombre;

    private BigDecimal aporte;
    private BigDecimal comision;
    private BigDecimal prima;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private LocalDateTime fecCreacion = LocalDateTime.now();

    private LocalDateTime fecUltimaModificacion;
}
