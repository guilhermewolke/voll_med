package com.github.guilhermewoelke.medvoll.api.controllers;

import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoEntity;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoOutputDTO;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicosAtualizarInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicosCadastrarInputDTO;
import com.github.guilhermewoelke.medvoll.api.repositories.MedicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicosController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid MedicosCadastrarInputDTO input, UriComponentsBuilder uriBuilder) {
        var medico =  new MedicoEntity(input);
        this.repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new MedicoOutputDTO(medico));
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Long id) {
        return ResponseEntity.ok(new MedicoOutputDTO(repository.getReferenceById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<MedicoOutputDTO>> listar(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(MedicoOutputDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid MedicosAtualizarInputDTO input) {
        var medico = repository.getReferenceById(id);
        medico.atualizar(input);
        repository.save(medico);

        return ResponseEntity.ok(new MedicoOutputDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.desativar();
        repository.save(medico);

        return ResponseEntity.noContent().build();
    }
}
