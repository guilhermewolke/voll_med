package com.github.guilhermewoelke.medvoll.api.service;

import com.github.guilhermewoelke.medvoll.api.infra.exceptions.ValidacaoException;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaCancelamentoInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaEntity;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.consulta.MotivosCancelamento;
import com.github.guilhermewoelke.medvoll.api.models.consulta.validacao.ValidadorDeConsulta;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoEntity;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteEntity;
import com.github.guilhermewoelke.medvoll.api.repositories.ConsultaRepository;
import com.github.guilhermewoelke.medvoll.api.repositories.MedicoRepository;
import com.github.guilhermewoelke.medvoll.api.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorDeConsulta> validadores;

    public ConsultaEntity agendar(ConsultaInputDTO dados) {
        MedicoEntity medico;

        if (!pacienteRepository.existsById(dados.PacienteID())) {
            throw new ValidacaoException("Paciente com ID informado inexistente");
        }

        if (dados.MedicoID() != null) {
            if (!medicoRepository.existsById(dados.MedicoID())) {
                throw new ValidacaoException("Médico com ID informado inexistente");
            }
            medico = this.medicoRepository.getReferenceById(dados.MedicoID());
        } else {
            medico = escolherMedico(dados);
        }

        validadores.forEach(v -> v.validar(dados));

        System.out.println("Medico: " + medico);
        PacienteEntity paciente = this.pacienteRepository.getReferenceById(dados.PacienteID());

        ConsultaEntity consulta = new ConsultaEntity(null, medico, paciente, dados.data(), null, null, true);

        this.consultaRepository.save(consulta);
        return consulta;
    }

    private MedicoEntity escolherMedico(ConsultaInputDTO dados) {
        MedicoEntity medico;
        if (dados.MedicoID() != null) {
            medico = this.medicoRepository.getReferenceById(dados.MedicoID());
        } else {
            if (dados.especialidade() == null) {
                throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
            }
        }

        return medicoRepository.escolherLivreNaData(dados.especialidade(), dados.data());
    }

    public ConsultaEntity cancelar(ConsultaCancelamentoInputDTO dados) {
        // Validar se o motivo foi devidamente preenchido
        if (dados.motivo() == null) {
            throw new ValidacaoException("Para o cancelamento de consultas, o motivo é obrigatório");
        }

        // Validar se o cancelamento está sendo realizado com a antecedência mínima de 24 horas
        if(!consultaRepository.existsById(dados.id())) {
            throw new ValidacaoException("Consulta inexistente");
        }

        ConsultaEntity consulta = consultaRepository.getReferenceById(dados.id());

        if (LocalDateTime.now().plusHours(24).isAfter(consulta.getData())) {
            throw new ValidacaoException("Só é possível cancelar consultas com mais de 24 horas de antecedência");
        }
        consulta.cancelar(dados);
        consultaRepository.save(consulta);
        return consulta;
    }
}
