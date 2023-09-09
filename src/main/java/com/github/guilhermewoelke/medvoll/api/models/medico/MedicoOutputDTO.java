package com.github.guilhermewoelke.medvoll.api.models.medico;

import com.github.guilhermewoelke.medvoll.api.models.valueobjects.Endereco;

public record MedicoOutputDTO(Long id, String nome, String email, String crm, Especialidade especialidade, Endereco endereco, String telefone, boolean ativo) {
    public MedicoOutputDTO(MedicoEntity medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco().getEndereco(), medico.getTelefone(), medico.getAtivo());
    }
}
