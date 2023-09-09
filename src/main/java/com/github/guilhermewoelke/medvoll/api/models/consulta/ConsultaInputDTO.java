package com.github.guilhermewoelke.medvoll.api.models.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guilhermewoelke.medvoll.api.models.medico.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaInputDTO(
        Long id,
        @JsonProperty("medico_id")
        Long MedicoID,
        @NotNull
        @JsonProperty("paciente_id")
        Long PacienteID,
        @NotNull
        @Future
        LocalDateTime data,
        Especialidade especialidade,

        LocalDateTime dataCancelamento,

        MotivosCancelamento motivo) {
}
