INSERT INTO disciplinas (nome, codigo) VALUES ('Matemática', 'MAT001');
INSERT INTO disciplinas (nome, codigo) VALUES ('Física', 'FIS001');
INSERT INTO disciplinas (nome, codigo) VALUES ('Programação Orientada a Objetos', 'POO001');
INSERT INTO disciplinas (nome, codigo) VALUES ('Banco de Dados', 'BDD001');
INSERT INTO disciplinas (nome, codigo) VALUES ('Estrutura de Dados', 'ESD001');
INSERT INTO disciplinas (nome, codigo) VALUES ('Cálculo I', 'CAL001');
INSERT INTO disciplinas (nome, codigo) VALUES ('Algoritmos', 'ALG001');

INSERT INTO semestres (data_inicial, data_final) VALUES ('2025-02-01', '2025-06-30');
INSERT INTO semestres (data_inicial, data_final) VALUES ('2025-07-01', '2025-12-15');
INSERT INTO semestres (data_inicial, data_final) VALUES ('2026-02-01', '2026-06-30');
INSERT INTO semestres (data_inicial, data_final) VALUES ('2026-07-01', '2026-12-15');
INSERT INTO semestres (data_inicial, data_final) VALUES ('2027-02-01', '2027-06-30');

INSERT INTO professor (nome, email, senha) VALUES ('Carlos Silva', 'carlos@email.com', 'senha123');
INSERT INTO professor (nome, email, senha) VALUES ('Ana Souza', 'ana@email.com', 'senha123');
INSERT INTO professor (nome, email, senha) VALUES ('Roberto Lima', 'roberto@email.com', 'senha123');
INSERT INTO professor (nome, email, senha) VALUES ('Fernanda Costa', 'fernanda@email.com', 'senha123');
INSERT INTO professor (nome, email, senha) VALUES ('Marcos Oliveira', 'marcos@email.com', 'senha123');

INSERT INTO alunos (nome, email, senha, matricula) VALUES ('João Pedro', 'joao@email.com', 'senha123', '000000001');
INSERT INTO alunos (nome, email, senha, matricula) VALUES ('Maria Lima', 'maria@email.com', 'senha123', '000000002');
INSERT INTO alunos (nome, email, senha, matricula) VALUES ('Lucas Ferreira', 'lucas@email.com', 'senha123', '000000003');
INSERT INTO alunos (nome, email, senha, matricula) VALUES ('Beatriz Santos', 'beatriz@email.com', 'senha123', '000000004');
INSERT INTO alunos (nome, email, senha, matricula) VALUES ('Rafael Mendes', 'rafael@email.com', 'senha123', '000000005');

-- turmas: professor_id(1-5), disciplina_id(1-7), semestre_id(3) = semestre ativo (2026-02-01 a 2026-06-30)
INSERT INTO turmas (professor_id, disciplina_id, semestre_id, sala, horario1, horario2, ativo) VALUES (1, 1, 3, '101', '08:00', '10:00', true);
INSERT INTO turmas (professor_id, disciplina_id, semestre_id, sala, horario1, horario2, ativo) VALUES (2, 3, 3, '102', '10:00', '12:00', true);
INSERT INTO turmas (professor_id, disciplina_id, semestre_id, sala, horario1, horario2, ativo) VALUES (3, 4, 3, '103', '14:00', '16:00', true);
INSERT INTO turmas (professor_id, disciplina_id, semestre_id, sala, horario1, horario2, ativo) VALUES (4, 5, 3, '104', '16:00', '18:00', true);
INSERT INTO turmas (professor_id, disciplina_id, semestre_id, sala, horario1, horario2, ativo) VALUES (5, 2, 3, '105', '18:00', '20:00', true);
INSERT INTO turmas (professor_id, disciplina_id, semestre_id, sala, horario1, horario2, ativo) VALUES (1, 6, 3, '106', '20:00', null, true);
INSERT INTO turmas (professor_id, disciplina_id, semestre_id, sala, horario1, horario2, ativo) VALUES (2, 7, 3, '107', '08:00', null, true);

-- avaliacoes: turma_id(1-7)
INSERT INTO avaliacoes (turma_id, titulo, enunciado, data_entrega, peso) VALUES (1, 'Prova 1', 'Conteúdo: álgebra linear e matrizes.', '2026-04-10 23:59:00', 3);
INSERT INTO avaliacoes (turma_id, titulo, enunciado, data_entrega, peso) VALUES (1, 'Trabalho Final', 'Resolução de problemas de cálculo diferencial.', '2026-06-20 23:59:00', 4);
INSERT INTO avaliacoes (turma_id, titulo, enunciado, data_entrega, peso) VALUES (2, 'Prova 1', 'Fundamentos de orientação a objetos.', '2026-04-15 23:59:00', 3);
INSERT INTO avaliacoes (turma_id, titulo, enunciado, data_entrega, peso) VALUES (3, 'Prova 1', 'Modelagem relacional e SQL básico.', '2026-04-20 23:59:00', 3);
INSERT INTO avaliacoes (turma_id, titulo, enunciado, data_entrega, peso) VALUES (3, 'Projeto Final', 'Implementação de um banco de dados relacional completo.', '2026-06-25 23:59:00', 5);
INSERT INTO avaliacoes (turma_id, titulo, enunciado, data_entrega, peso) VALUES (4, 'Prova 1', 'Listas, filas e pilhas.', '2026-04-18 23:59:00', 3);
INSERT INTO avaliacoes (turma_id, titulo, enunciado, data_entrega, peso) VALUES (5, 'Prova 1', 'Leis de Newton e cinemática.', '2026-04-12 23:59:00', 3);