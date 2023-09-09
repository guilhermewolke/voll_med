package com.github.guilhermewoelke.medvoll.api.models.consulta.validacao;

import com.github.guilhermewoelke.medvoll.api.infra.exceptions.ValidacaoException;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaInputDTO;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class AntecedenciaMinimaValidador implements ValidadorDeConsulta {

    public void validar(ConsultaInputDTO dados) {
        LocalDateTime dataConsulta = dados.data();

        if (!dataConsulta.isAfter(LocalDateTime.now().minus(30, ChronoUnit.MINUTES))) {
            throw new ValidacaoException("Antecedência mínima de 30 minutos não foi respeitada");
        }
    }
}
