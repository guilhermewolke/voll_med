CREATE TABLE consulta (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    data datetime NOT NULL
);