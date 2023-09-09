package com.github.guilhermewoelke.medvoll.api.controllers;

import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteEntity;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteOutputDTO;
import com.github.guilhermewoelke.medvoll.api.repositories.PacienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacientesController {

    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public ResponseEntity<Page<PacienteOutputDTO>> listar(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(PacienteOutputDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPorID(@PathVariable Long id) {
        return ResponseEntity.ok(new PacienteOutputDTO(repository.getReferenceById(id)));
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid PacienteInputDTO input, UriComponentsBuilder uriBuilder) {
        var paciente = new PacienteEntity(input);
        this.repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteOutputDTO(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid PacienteInputDTO input) {
        var paciente = repository.getReferenceById(id);
        paciente.atualizar(input);
        repository.save(paciente);

        return ResponseEntity.ok(new PacienteOutputDTO(paciente));
    }

    public ResponseEntity remover(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.desativar();
        repository.save(paciente);

        return ResponseEntity.noContent().build();
    }

}
