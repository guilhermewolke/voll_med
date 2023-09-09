package com.github.guilhermewoelke.medvoll.api.repositories;

import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaEntity;
import com.github.guilhermewoelke.medvoll.api.models.medico.Especialidade;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoEntity;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicosCadastrarInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteEntity;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.valueobjects.Endereco;
import com.github.guilhermewoelke.medvoll.api.models.valueobjects.EnderecoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Escolher médico aleatório - Deve devolver null quando o único médico cadastrado não está disponível na data")
    void escolherLivreNaDataCenario1() {
        LocalDateTime proximaSegundaAs10 = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .toLocalDate().atTime(10, 0);

        var medico = cadastrarMedico(dadosMedico("Medico 1", "medico1@email.com", "112358", Especialidade.CARDIOLOGIA));
        var paciente = cadastrarPaciente(dadosPaciente("Paciente 1", "paciente1@email.com", "33366699910", "telefone", true));
        var consulta = cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        MedicoEntity medicoLivre = medicoRepository.escolherLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoLivre).isNull();

    }

    @Test
    @DisplayName("Escolher médico aleatório - Deve devolver médico quando o médico está disponível na data")
    void escolherLivreNaDataCenario2() {
        LocalDateTime proximaSegundaAs10 = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .toLocalDate().atTime(10, 0);

        var medico = cadastrarMedico(dadosMedico("Medico 1", "medico1@email.com", "112358", Especialidade.CARDIOLOGIA));

        MedicoEntity medicoLivre = medicoRepository.escolherLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        assertThat(medicoLivre).isEqualTo(medico);

    }

    private ConsultaEntity cadastrarConsulta(MedicoEntity medico, PacienteEntity paciente, LocalDateTime data) {
        ConsultaEntity consulta = new ConsultaEntity(null, medico, paciente, data, null, null, true);
        em.persist(consulta);
        return consulta;
    }

    private MedicoEntity cadastrarMedico(MedicosCadastrarInputDTO input) {
        MedicoEntity medico = new MedicoEntity(input);
        em.persist(medico);
        return medico;
    }

    private PacienteEntity cadastrarPaciente(PacienteInputDTO input) {
        var paciente = new PacienteEntity(input);
        em.persist(paciente);
        return paciente;
    }

    private MedicosCadastrarInputDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new MedicosCadastrarInputDTO(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private PacienteInputDTO dadosPaciente(String nome, String email, String cpf, String telefone, Boolean ativo) {
        return new PacienteInputDTO(null, nome, email, cpf, dadosEndereco(), telefone, ativo);
    }

    private Endereco dadosEndereco() {
        return new Endereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                "1112",
                null
        );
    }
}