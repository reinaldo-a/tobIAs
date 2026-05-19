ALTER TABLE relatorio
    ADD COLUMN IF NOT EXISTS submissao_id INT REFERENCES submissao(id) ON DELETE CASCADE;

CREATE UNIQUE INDEX IF NOT EXISTS idx_relatorio_submissao_id
    ON relatorio(submissao_id)
    WHERE submissao_id IS NOT NULL;
