package com.github.guilhermewoelke.medvoll.api.models.valueobjects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Endereco(
        @NotBlank
        String logradouro,
        @NotBlank
        String bairro,
        @NotBlank
        @Pattern(regexp="\\d{8}")
        String cep,
        @NotBlank
        String cidade,

        @NotBlank
        @Pattern(regexp="\\w{2}")
        String uf,
        @NotBlank
        String numero,
        String complemento
) {}
