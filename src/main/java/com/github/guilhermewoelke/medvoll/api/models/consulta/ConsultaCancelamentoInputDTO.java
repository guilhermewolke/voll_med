package com.github.guilhermewoelke.medvoll.api.models.consulta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ConsultaCancelamentoInputDTO(
        Long id,
        @JsonProperty("paciente_id")
        Long PacienteID,
        @NotNull
        MotivosCancelamento motivo
        ) {
}
