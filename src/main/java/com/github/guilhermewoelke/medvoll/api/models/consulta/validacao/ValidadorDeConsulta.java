package com.github.guilhermewoelke.medvoll.api.models.consulta.validacao;

import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaInputDTO;

public interface ValidadorDeConsulta {
    void validar(ConsultaInputDTO dados);
}
