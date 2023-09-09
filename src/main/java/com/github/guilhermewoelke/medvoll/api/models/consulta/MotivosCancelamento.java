package com.github.guilhermewoelke.medvoll.api.models.consulta;

public enum MotivosCancelamento {
    PACIENTE_DESISTIU("O paciente desistiu"),
    MEDICO_CANCELOU("O Médico cancelou"),
    OUTROS("Outros");

    public final String label;

    private MotivosCancelamento(String label) {
        this.label = label;
    }
}
