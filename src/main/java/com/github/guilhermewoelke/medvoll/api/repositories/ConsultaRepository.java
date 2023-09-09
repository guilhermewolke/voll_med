package com.github.guilhermewoelke.medvoll.api.repositories;

import com.github.guilhermewoelke.medvoll.api.models.consulta.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
}
