package com.github.guilhermewoelke.medvoll.api.models.medico;

import com.github.guilhermewoelke.medvoll.api.models.valueobjects.EnderecoEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="Medico")
@Table(name="medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private EnderecoEntity endereco;

    private Boolean ativo;

    public MedicoEntity(MedicosCadastrarInputDTO dados) {
        this.ativo = true;
        this.id = null;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new EnderecoEntity(dados.endereco());
    }

    public void atualizar(MedicosAtualizarInputDTO input) {
        if (input.nome() != null) {
            this.nome = input.nome();
        }

        if (input.telefone() != null) {
            this.telefone = input.telefone();
        }

        if (input.email() != null) {
            this.email = input.email();
        }

        if (input.crm() != null) {
            this.crm = input.crm();
        }

        if (input.especialidade() != null) {
            this.especialidade = input.especialidade();
        }

        if (input.endereco() != null) {
            this.endereco.atualizar(input.endereco());
        }
    }

    public void desativar() {
        this.ativo = false;
    }

}
