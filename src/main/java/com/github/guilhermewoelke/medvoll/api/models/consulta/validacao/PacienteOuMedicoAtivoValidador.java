package com.github.guilhermewoelke.medvoll.api.models.consulta.validacao;

import com.github.guilhermewoelke.medvoll.api.infra.exceptions.ValidacaoException;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteEntity;
import com.github.guilhermewoelke.medvoll.api.repositories.MedicoRepository;
import com.github.guilhermewoelke.medvoll.api.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteOuMedicoAtivoValidador implements ValidadorDeConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(ConsultaInputDTO dados) {
        if (!pacienteRepository.findAtivoByID(dados.PacienteID())) {
            throw new ValidacaoException("O paciente está inativo");
        }

        if (dados.MedicoID() == null) {
            return;
        } else if(!medicoRepository.findAtivoByID(dados.PacienteID())) {
            throw new ValidacaoException("O médico está inativo");
        }
    }
}
