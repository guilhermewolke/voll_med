ALTER TABLE consulta ADD COLUMN `data_cancelamento` DATETIME NULL AFTER `data`,
                      ADD COLUMN `motivo_cancelamento` VARCHAR(255) NULL AFTER `data_cancelamento`,
                      ADD COLUMN `ativo` VARCHAR(45) NOT NULL DEFAULT '1' AFTER `motivo_cancelamento`;
