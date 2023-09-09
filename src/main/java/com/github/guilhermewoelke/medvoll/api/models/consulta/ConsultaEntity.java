package com.github.guilhermewoelke.medvoll.api.models.consulta;

import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoEntity;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="consulta")
@Entity(name="Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="medico_id")
    private MedicoEntity medico;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="paciente_id")
    private PacienteEntity paciente;

    private LocalDateTime data;
    private LocalDateTime dataCancelamento;

    @Column(name="motivo_cancelamento")
    private String motivo;

    private boolean ativo;

    public void cancelar(ConsultaCancelamentoInputDTO consulta) {
        this.ativo = false;
        this.motivo = consulta.motivo().label;
    }
}
