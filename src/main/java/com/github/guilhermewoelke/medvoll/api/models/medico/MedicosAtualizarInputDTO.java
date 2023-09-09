package com.github.guilhermewoelke.medvoll.api.models.medico;

import com.github.guilhermewoelke.medvoll.api.models.valueobjects.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MedicosAtualizarInputDTO(
        @NotNull
        Long id,
        String nome,
        @Email
        String email,

        String telefone,
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        Especialidade especialidade,
        @Valid
        Endereco endereco
) {}
