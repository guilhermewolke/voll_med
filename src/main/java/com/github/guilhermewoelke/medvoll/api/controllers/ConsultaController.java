package com.github.guilhermewoelke.medvoll.api.controllers;

import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaCancelamentoInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaEntity;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaOutputDTO;
import com.github.guilhermewoelke.medvoll.api.service.AgendaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    @Autowired
    private AgendaService agendaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid ConsultaInputDTO dados) {
        ConsultaEntity consulta = this.agendaService.agendar(dados);
        return ResponseEntity.ok(new ConsultaOutputDTO(
                consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId(),
                consulta.getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                null,
                consulta.getMedico().getEspecialidade(),
                null,
                consulta.isAtivo()
            ));
    }

    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid ConsultaCancelamentoInputDTO dados) {
        ConsultaEntity consulta = this.agendaService.cancelar(dados);
        String dataCancelamento = null;

        if (consulta.getDataCancelamento() != null) {
            dataCancelamento = consulta.getDataCancelamento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        return ResponseEntity.ok(new ConsultaOutputDTO(
                consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId(),
                consulta.getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                dataCancelamento,
                consulta.getMedico().getEspecialidade(),
                consulta.getMotivo(),
                consulta.isAtivo()));
    }
}
