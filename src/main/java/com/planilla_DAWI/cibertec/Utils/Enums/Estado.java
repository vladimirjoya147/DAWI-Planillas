package com.planilla_DAWI.cibertec.Utils.Enums;

public enum EstadoEnum {
    INACTIVO(0),
    ACTIVO(1),
    TODOS(2);

    private final int valor;

    EstadoEnum(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static EstadoEnum fromValor(int valor) {
        for (EstadoEnum estado : EstadoEnum.values()) {
            if (estado.getValor() == valor) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Valor no válido para EstadoEnum: " + valor);
    }
}
