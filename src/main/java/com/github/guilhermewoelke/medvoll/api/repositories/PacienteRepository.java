package com.github.guilhermewoelke.medvoll.api.repositories;

import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoEntity;
import com.github.guilhermewoelke.medvoll.api.models.paciente.PacienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
    Page<PacienteEntity> findAllByAtivoTrue(Pageable paginacao);

    @Query(
        """
            SELECT
                p.ativo
            FROM
                Paciente p
            WHERE
                p.id = :id
        """
    )
    Boolean findAtivoByID(Long id);
}
