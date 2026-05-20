ALTER TABLE relatorio
    ALTER COLUMN aluno_id DROP NOT NULL;

ALTER TABLE relatorio
    ADD COLUMN IF NOT EXISTS atividade_id INT REFERENCES atividade(id) ON DELETE CASCADE;

CREATE UNIQUE INDEX IF NOT EXISTS idx_relatorio_atividade_id
    ON relatorio(atividade_id)
    WHERE atividade_id IS NOT NULL;
