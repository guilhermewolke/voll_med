package com.github.guilhermewoelke.medvoll.api.models.consulta.validacao;

import com.github.guilhermewoelke.medvoll.api.infra.exceptions.ValidacaoException;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaInputDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class HorarioFuncionamentoValidador  implements ValidadorDeConsulta{

    public void validar(ConsultaInputDTO dados) {
        LocalDateTime dataConsulta = dados.data();

        boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesAbertura = dataConsulta.getHour() < 7;
        boolean depoisFechamento = dataConsulta.getHour() > 18;

        if (domingo || antesAbertura || depoisFechamento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
