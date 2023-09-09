package com.github.guilhermewoelke.medvoll.api.models.paciente;

import com.github.guilhermewoelke.medvoll.api.models.valueobjects.Endereco;

public record PacienteInputDTO(Long id, String nome, String email, String cpf, Endereco endereco, String telefone, boolean ativo) {
    public PacienteInputDTO(PacienteEntity paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getEndereco().getEndereco(), paciente.getTelefone(), paciente.getAtivo());
    }
}
