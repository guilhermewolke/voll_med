package com.github.guilhermewoelke.medvoll.api.controllers;

import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaEntity;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaOutputDTO;
import com.github.guilhermewoelke.medvoll.api.models.medico.Especialidade;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoEntity;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicosCadastrarInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteEntity;
import com.github.guilhermewoelke.medvoll.api.models.valueobjects.Endereco;
import com.github.guilhermewoelke.medvoll.api.models.valueobjects.EnderecoEntity;
import com.github.guilhermewoelke.medvoll.api.service.AgendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private JacksonTester<ConsultaInputDTO> input;

    @Autowired
    private JacksonTester<ConsultaOutputDTO> output;

    @MockBean
    private AgendaService agendaService;

    @Test
    @DisplayName("Agendar Cenário - Deveria retornar Erro 400 quando informações estão inválidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var response = mock.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void cancelar() {
    }
}