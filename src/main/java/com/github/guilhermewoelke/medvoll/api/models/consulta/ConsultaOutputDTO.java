package com.github.guilhermewoelke.medvoll.api.models.consulta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guilhermewoelke.medvoll.api.models.medico.Especialidade;

public record ConsultaOutputDTO(
        Long id,
        @JsonProperty("medico_id")
        Long MedicoID,
        @JsonProperty("paciente_id")
        Long PacienteID,
        String data,
        @JsonProperty("data_cancelamento")
        String dataCancelamento,
        Especialidade especialidade,
        String motivo,
        Boolean ativo) {
}
