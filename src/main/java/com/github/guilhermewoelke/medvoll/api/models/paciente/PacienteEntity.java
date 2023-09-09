package com.github.guilhermewoelke.medvoll.api.models.paciente;

import com.github.guilhermewoelke.medvoll.api.models.valueobjects.EnderecoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="Paciente")
@Table(name="pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded
    private EnderecoEntity endereco;
    private Boolean ativo;

    public PacienteEntity(PacienteInputDTO dados) {
        this.id = null;
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.endereco = new EnderecoEntity(dados.endereco());
        this.ativo = true;
    }

    public void atualizar(PacienteInputDTO input) {
        if (input.nome() != null) {
            this.nome = input.nome();
        }

        if (input.telefone() != null) {
            this.telefone = input.telefone();
        }

        if (input.email() != null) {
            this.email = input.email();
        }

        if (input.cpf() != null) {
            this.cpf = input.cpf();
        }

        if (input.endereco() != null) {
            this.endereco.atualizar(input.endereco());
        }
    }

    public void desativar() {
        this.ativo = false;
    }
}
