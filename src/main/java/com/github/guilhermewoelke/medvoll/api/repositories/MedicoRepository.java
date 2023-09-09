package com.github.guilhermewoelke.medvoll.api.repositories;

import com.github.guilhermewoelke.medvoll.api.models.medico.Especialidade;
import com.github.guilhermewoelke.medvoll.api.models.medico.MedicoEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
    Page<MedicoEntity> findAllByAtivoTrue(Pageable paginacao);

    @Query(
            """
                SELECT
                    m
                FROM
                    Medico m
                WHERE
                    m.ativo = true
                    AND m.especialidade = :especialidade
                    AND m.id NOT IN (
                        SELECT
                            c.medico.id FROM Consulta c
                            WHERE
                            c.data = :data
                    )
                ORDER BY
                    RAND()
                LIMIT
                    1
            """)
    MedicoEntity escolherLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query(
        """
            SELECT
                m.ativo
            FROM
                Medico m
            WHERE
                m.id = :id
        """
    )
    boolean findAtivoByID(Long id);
}
