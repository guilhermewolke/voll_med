package com.github.guilhermewoelke.medvoll.api.models.paciente;

import com.github.guilhermewoelke.medvoll.api.models.valueobjects.Endereco;

public record PacienteOutputDTO(Long id, String nome, String email, String cpf, String telefone, Endereco endereco, Boolean ativo) {
    public PacienteOutputDTO(PacienteEntity paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco().getEndereco(), paciente.getAtivo());
    }
}
