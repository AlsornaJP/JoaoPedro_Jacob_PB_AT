# Dicionário do Projeto — SRS (Sistema de Registro)

> **Pacote raiz:** `com.jacob.jp.srs`
> **Stack:** Java 17 · Spring Boot · Spring Data JPA · H2 (in-memory) · Lombok

---

## Sumário

1. [Entidades](#1-entidades)
2. [Enums](#2-enums)
3. [DTOs](#3-dtos)
4. [Services](#4-services)
5. [Repositórios](#5-repositórios)
6. [Exceções Customizadas](#6-exceções-customizadas)

---

## 1. Entidades

Todas as entidades ficam em `com.jacob.jp.srs.models`. As que estendem `Usuario` herdam seus campos via `@MappedSuperclass`.

---

### Usuario *(abstract)*

Superclasse mapeada (`@MappedSuperclass`) que centraliza os campos comuns a `Aluno` e `Professor`. Não gera tabela própria no banco.

| Campo  | Tipo      | Coluna  | Restrições             | Descrição                          |
|--------|-----------|---------|------------------------|------------------------------------|
| `id`   | `Integer` | `id`    | PK, auto-increment     | Identificador único do usuário     |
| `nome` | `String`  | `nome`  | NOT NULL, max 50 chars | Nome completo do usuário           |
| `email`| `String`  | `email` | NOT NULL, max 50, UNIQUE | Endereço de e-mail (login)       |
| `senha`| `String`  | `senha` | NOT NULL               | Senha em texto plano               |

---

### Aluno

Tabela: `alunos` | Estende: `Usuario`

Representa um estudante registrado no sistema. Além dos campos herdados, possui matrícula única gerada automaticamente pelo `GeradorDeMatricula`.

| Campo      | Tipo      | Coluna      | Restrições              | Descrição                              |
|------------|-----------|-------------|-------------------------|----------------------------------------|
| `matricula`| `String`  | `matricula` | NOT NULL, max 9, UNIQUE | Código de matrícula gerado pelo sistema|

---

### Professor

Tabela: `professor` | Estende: `Usuario`

Representa um docente. Não possui atributos além dos herdados de `Usuario`.

*(Herda: `id`, `nome`, `email`, `senha`)*

---

### Disciplina

Tabela: `disciplinas`

Representa uma matéria ofertada pela instituição. É um dado de referência pré-populado via `data.sql` e não é criado pelo fluxo da aplicação.

| Campo    | Tipo      | Coluna   | Restrições             | Descrição                              |
|----------|-----------|----------|------------------------|----------------------------------------|
| `id`     | `Integer` | `id`     | PK, auto-increment     | Identificador único da disciplina      |
| `nome`   | `String`  | `nome`   | NOT NULL               | Nome completo da disciplina            |
| `codigo` | `String`  | `codigo` | NOT NULL, UNIQUE       | Código alfanumérico (ex: `POO001`)     |

---

### Semestre

Tabela: `semestres`

Define o período letivo ativo. O sistema sempre opera sobre o semestre cujo intervalo de datas engloba a data atual.

| Campo         | Tipo        | Coluna          | Restrições | Descrição                        |
|---------------|-------------|-----------------|------------|----------------------------------|
| `id`          | `Integer`   | `id`            | PK, auto-increment | Identificador único do semestre |
| `dataInicial` | `LocalDate` | `data_inicial`  | NOT NULL   | Início do período letivo         |
| `dataFinal`   | `LocalDate` | `data_final`    | NOT NULL   | Fim do período letivo            |

---

### Turma

Tabela: `turmas`

Representa uma oferta de disciplina por um professor em um semestre. Contém dois horários opcionais e um flag de estado `ativo`. Duas constraints únicas separadas impedem conflito de horário do professor no mesmo semestre (uma para `horario1`, outra para `horario2`, pois `horario2` é nullable).

| Campo       | Tipo           | Coluna        | Restrições               | Descrição                                        |
|-------------|----------------|---------------|--------------------------|--------------------------------------------------|
| `id`        | `Integer`      | `id`          | PK, auto-increment       | Identificador único da turma                     |
| `professor` | `Professor`    | `professor_id`| FK, NOT NULL             | Professor responsável                            |
| `disciplina`| `Disciplina`   | `disciplina_id`| FK, NOT NULL            | Disciplina ofertada                              |
| `semestre`  | `Semestre`     | `semestre_id` | FK, NOT NULL             | Semestre em que a turma é ofertada               |
| `sala`      | `String`       | `sala`        | NOT NULL, max 20 chars   | Identificação da sala                            |
| `horario1`  | `LocalTime`    | `horario1`    | NOT NULL                 | Horário principal da aula                        |
| `horario2`  | `LocalTime`    | `horario2`    | nullable                 | Horário secundário (opcional)                    |
| `ativo`     | `boolean`      | `ativo`       | NOT NULL, default `true` | Indica se a turma está aberta para inscrições    |
| `alunos`    | `List<TurmaAluno>` | —        | OneToMany (EAGER)        | Lista de inscrições associadas à turma           |

**Métodos de domínio:**

`fechar()` — Define `ativo = false`, impedindo novas inscrições. Chamado por `GestaoTurmaService.fecharTurma()`.

---

### TurmaAluno

Tabela: `turma_aluno`

Tabela de junção entre `Turma` e `Aluno`. Registra a inscrição de um aluno em uma turma e seu status atual. A constraint única em `(aluno_id, turma_id)` impede inscrições duplicadas no nível do banco.

| Campo    | Tipo      | Coluna     | Restrições                      | Descrição                              |
|----------|-----------|------------|---------------------------------|----------------------------------------|
| `id`     | `Integer` | `id`       | PK, auto-increment              | Identificador único da inscrição       |
| `aluno`  | `Aluno`   | `aluno_id` | FK, NOT NULL                    | Aluno inscrito                         |
| `turma`  | `Turma`   | `turma_id` | FK, NOT NULL                    | Turma em que o aluno está inscrito     |
| `status` | `Status`  | `status`   | NOT NULL, `@Enumerated(STRING)` | Estado atual da inscrição              |

**Constraint única:** `uc_turma_aluno` → `(aluno_id, turma_id)`

**Métodos de domínio:**

`trancar()` — Muda `status` para `Status.TRANCADO`. Chamado por `GestaoMatriculaService.solicitarTrancamento()`.

---

### Avaliacao

Tabela: `avaliacoes`

Representa uma atividade avaliativa criada por um professor para uma turma. Define o prazo de entrega e o peso relativo da avaliação.

| Campo         | Tipo            | Coluna          | Restrições                   | Descrição                             |
|---------------|-----------------|-----------------|------------------------------|---------------------------------------|
| `id`          | `Integer`       | `id`            | PK, auto-increment           | Identificador único da avaliação      |
| `turma`       | `Turma`         | `turma_id`      | FK, NOT NULL                 | Turma à qual a avaliação pertence     |
| `titulo`      | `String`        | `titulo`        | NOT NULL, max 100 chars      | Título descritivo da avaliação        |
| `enunciado`   | `String`        | `enunciado`     | NOT NULL, TEXT               | Descrição completa do que deve ser feito |
| `dataEntrega` | `LocalDateTime` | `data_entrega`  | NOT NULL                     | Prazo máximo de entrega               |
| `peso`        | `int`           | `peso`          | NOT NULL                     | Peso da avaliação no cálculo de desempenho |

---

### AvaliacaoAluno

Tabela: `avaliacao_aluno`

Tabela de junção entre `Avaliacao` e `Aluno`. Representa a entrega de um aluno para uma avaliação. Registra a data de entrega, possível atraso, anotações, nota e estado de correção. A constraint única em `(aluno_id, avaliacao_id)` garante uma entrega por aluno por avaliação no banco — re-entregas são tratadas como atualização via `atualizarEntrega()`.

| Campo           | Tipo            | Coluna           | Restrições                         | Descrição                                          |
|-----------------|-----------------|------------------|------------------------------------|----------------------------------------------------|
| `id`            | `Integer`       | `id`             | PK, auto-increment                 | Identificador único da entrega                     |
| `aluno`         | `Aluno`         | `aluno_id`       | FK, NOT NULL                       | Aluno que realizou a entrega                       |
| `avaliacao`     | `Avaliacao`     | `avaliacao_id`   | FK, NOT NULL                       | Avaliação à qual a entrega pertence                |
| `nota`          | `double`        | `nota`           | nullable (0 enquanto não corrigido)| Nota atribuída pelo professor (0–10)               |
| `dataEntrega`   | `LocalDateTime` | `data_entrega`   | NOT NULL                           | Momento em que o aluno realizou a entrega          |
| `dataAlteracao` | `LocalDateTime` | `data_alteracao` | nullable                           | Momento da última re-entrega                       |
| `anotacoes`     | `String`        | `anotacoes`      | nullable, TEXT                     | Observações opcionais do aluno na entrega          |
| `atraso`        | `boolean`       | `atraso`         | NOT NULL                           | `true` se entregue após `avaliacao.dataEntrega`    |
| `corrigido`     | `boolean`       | `corrigido`      | NOT NULL, default `false`          | `true` após nota ser lançada pelo professor        |

**Constraint única:** `uc_avaliacao_aluno` → `(aluno_id, avaliacao_id)`

**Métodos de domínio:**

`lancarNota(double nota)` — Valida que `nota` está entre 0 e 10 (lança `NotaInvalidaException` caso contrário), atribui a nota e define `corrigido = true`.

`atualizarEntrega(LocalDateTime dataAlteracao, String anotacoes, boolean atraso)` — Atualiza os campos da entrega em caso de re-entrega pelo aluno.

---

## 2. Enums

---

### Status

Pacote: `com.jacob.jp.srs.models`

Representa o estado de uma inscrição de aluno em uma turma (`TurmaAluno`). Persiste como `String` no banco via `@Enumerated(EnumType.STRING)`.

| Valor       | Descrição                                        |
|-------------|--------------------------------------------------|
| `CURSANDO`  | Inscrição ativa; aluno está frequentando a turma |
| `TRANCADO`  | Inscrição suspensa via solicitação de trancamento|
| `CONCLUIDO` | Turma encerrada (reservado para uso futuro)      |

---

## 3. DTOs

Todos os DTOs ficam em `com.jacob.jp.srs.models.DTO`. São objetos sem lógica de negócio usados para transferir dados entre a camada de UI e a camada de serviço, evitando exposição direta das entidades JPA.

---

### AlunoDTO

Espelho de `Aluno`. Usado no login, no registro e como referência em outros DTOs.

| Campo       | Tipo      | Descrição                          |
|-------------|-----------|------------------------------------|
| `id`        | `Integer` | Identificador do aluno             |
| `nome`      | `String`  | Nome completo                      |
| `email`     | `String`  | E-mail de login                    |
| `senha`     | `String`  | Senha                              |
| `matricula` | `String`  | Código de matrícula gerado         |

---

### ProfessorDTO

Espelho de `Professor`. Usado no login, no registro e como referência dentro de `TurmaDTO`.

| Campo   | Tipo      | Descrição              |
|---------|-----------|------------------------|
| `id`    | `Integer` | Identificador          |
| `nome`  | `String`  | Nome completo          |
| `email` | `String`  | E-mail de login        |
| `senha` | `String`  | Senha                  |

---

### DisciplinaDTO

Espelho de `Disciplina`. Usado em listagens e como referência dentro de `TurmaDTO`.

| Campo    | Tipo      | Descrição                          |
|----------|-----------|------------------------------------|
| `id`     | `Integer` | Identificador                      |
| `nome`   | `String`  | Nome da disciplina                 |
| `codigo` | `String`  | Código alfanumérico (ex: `POO001`) |

---

### SemestreDTO

Espelho de `Semestre`. Usado dentro de `TurmaDTO` e em `GestaoMatriculaService.abrirSemestre()`.

| Campo         | Tipo        | Descrição             |
|---------------|-------------|-----------------------|
| `id`          | `Integer`   | Identificador         |
| `dataInicial` | `LocalDate` | Início do semestre    |
| `dataFinal`   | `LocalDate` | Fim do semestre       |

---

### TurmaDTO

Espelho de `Turma`. Possui dois construtores: o padrão inclui a lista de alunos; o construtor `(Turma, boolean semAlunos)` deixa `alunos = null`, usado dentro de `TurmaAlunoDTO` para evitar referência circular.

| Campo       | Tipo                  | Descrição                                    |
|-------------|-----------------------|----------------------------------------------|
| `id`        | `Integer`             | Identificador da turma                       |
| `professor` | `ProfessorDTO`        | Professor responsável                        |
| `disciplina`| `DisciplinaDTO`       | Disciplina ofertada                          |
| `semestre`  | `SemestreDTO`         | Semestre da turma                            |
| `sala`      | `String`              | Sala de aula                                 |
| `horario1`  | `LocalTime`           | Horário principal                            |
| `horario2`  | `LocalTime`           | Horário secundário (pode ser `null`)         |
| `ativo`     | `boolean`             | Estado da turma                              |
| `alunos`    | `List<TurmaAlunoDTO>` | Inscrições (null quando dentro de TurmaAlunoDTO) |

---

### TurmaAlunoDTO

Espelho de `TurmaAluno`. Representa a inscrição de um aluno em uma turma. Internamente usa `TurmaDTO(turma, true)` para evitar recursão circular com a lista de alunos da turma.

| Campo    | Tipo        | Descrição                          |
|----------|-------------|------------------------------------|
| `id`     | `Integer`   | Identificador da inscrição         |
| `aluno`  | `AlunoDTO`  | Aluno inscrito                     |
| `turma`  | `TurmaDTO`  | Turma (sem lista de alunos interna)|
| `status` | `Status`    | Estado atual da inscrição          |

---

### AvaliacaoDTO

Espelho de `Avaliacao`. Usado para criação de avaliações e em listagens para o aluno e o professor.

| Campo         | Tipo            | Descrição                           |
|---------------|-----------------|-------------------------------------|
| `id`          | `Integer`       | Identificador                       |
| `turma`       | `TurmaDTO`      | Turma à qual a avaliação pertence   |
| `titulo`      | `String`        | Título da avaliação                 |
| `enunciado`   | `String`        | Enunciado completo                  |
| `dataEntrega` | `LocalDateTime` | Prazo de entrega                    |
| `peso`        | `int`           | Peso da avaliação                   |

---

### AvaliacaoAlunoDTO

Espelho de `AvaliacaoAluno`. Usado no fluxo de entrega de atividades, lançamento de notas e visualização de desempenho.

| Campo           | Tipo            | Descrição                                  |
|-----------------|-----------------|--------------------------------------------|
| `id`            | `Integer`       | Identificador da entrega                   |
| `aluno`         | `AlunoDTO`      | Aluno que entregou                         |
| `avaliacao`     | `AvaliacaoDTO`  | Avaliação referenciada                     |
| `nota`          | `double`        | Nota recebida (0 se ainda não corrigido)   |
| `dataEntrega`   | `LocalDateTime` | Data/hora da entrega                       |
| `dataAlteracao` | `LocalDateTime` | Data/hora da última re-entrega             |
| `anotacoes`     | `String`        | Observações do aluno                       |
| `atraso`        | `boolean`       | Indica se a entrega foi feita com atraso   |
| `corrigido`     | `boolean`       | Indica se a nota já foi lançada            |

---

## 4. Services

Todos os services ficam em `com.jacob.jp.srs.service` e são anotados com `@Service`.

---

### GestaoContaService

Responsável por criação de contas e autenticação de usuários. Delega validações de e-mail aos validators e a geração de matrícula ao `GeradorDeMatricula`.

| Método | Parâmetros | Retorno | Descrição |
|--------|------------|---------|-----------|
| `registrarAluno` | `AlunoDTO` | `void` | Valida e-mail, gera matrícula e persiste o aluno |
| `registrarProfessor` | `ProfessorDTO` | `void` | Valida e-mail e persiste o professor |
| `loginAluno` | `String email, String senha` | `AlunoDTO` | Autentica o aluno via validator e retorna seus dados |
| `loginProfessor` | `String email, String senha` | `ProfessorDTO` | Autentica o professor via validator e retorna seus dados |

---

### GestaoTurmaService

Responsável pelo ciclo de vida das turmas: abertura, fechamento e listagens. Inclui validação de conflito de horário e resolução de semestre ativo.

| Método | Parâmetros | Retorno | Descrição |
|--------|------------|---------|-----------|
| `abrirTurma` | `TurmaDTO` | `TurmaDTO` | Resolve professor, disciplina e semestre ativo; valida conflito de horário; persiste e retorna a turma criada |
| `fecharTurma` | `Integer id` | `void` | Busca a turma, chama `turma.fechar()` e salva |
| `listarTurmasPorProfessor` | `Integer professorId` | `List<TurmaDTO>` | Retorna todas as turmas de um professor |
| `listarDisciplinas` | — | `List<DisciplinaDTO>` | Retorna todas as disciplinas cadastradas |
| `buscarDisciplinaPorCodigo` | `String codigo` | `DisciplinaDTO` | Busca disciplina pelo código; lança `DisciplinaNotFoundException` se não existir |
| `listarTurmasDisponiveisPorAluno` | `Integer alunoId` | `List<TurmaDTO>` | Retorna turmas ativas nas quais o aluno ainda não está inscrito |
| `buscarSemestreAtivo` *(private)* | — | `Semestre` | Resolve o semestre cujo intervalo engloba `LocalDate.now()`; lança `SemestreNotFoundException` se não houver |

---

### GestaoMatriculaService

Responsável por inscrições e trancamentos de alunos em turmas, além da visualização da grade e da gestão de semestres.

| Método | Parâmetros | Retorno | Descrição |
|--------|------------|---------|-----------|
| `abrirSemestre` | `SemestreDTO` | `void` | Persiste um novo semestre |
| `solicitarInscricao` | `TurmaAlunoDTO` | `void` | Resolve aluno e turma; valida se turma está ativa e se não há inscrição duplicada; salva `TurmaAluno` com `Status.CURSANDO` |
| `solicitarTrancamento` | `TurmaAlunoDTO` | `void` | Resolve aluno e turma; busca inscrição existente; chama `turmaAluno.trancar()` e salva |
| `visualizarGrade` | `Integer alunoId` | `List<TurmaAlunoDTO>` | Retorna todas as inscrições do aluno |
| `buscarSemestreAtivo` | — | `Semestre` | Retorna o semestre ativo atual |

---

### GestaoAtividadesService

Responsável pela criação de avaliações, entrega de atividades pelos alunos, lançamento de notas e consulta de desempenho.

| Método | Parâmetros | Retorno | Descrição |
|--------|------------|---------|-----------|
| `criarAvaliacao` | `AvaliacaoDTO` | `AvaliacaoDTO` | Busca a turma; valida se está ativa e se o professor tem acesso; persiste e retorna a avaliação |
| `entregarAtividade` | `AvaliacaoAlunoDTO` | `void` | Busca aluno e avaliação; verifica se o aluno está inscrito na turma; calcula atraso; cria nova entrega ou atualiza existente |
| `lancarNota` | `AvaliacaoAlunoDTO` | `void` | Busca a entrega pelo id; chama `avaliacaoAluno.lancarNota(nota)` e salva |
| `listarEntregasPorAvaliacao` | `Integer avaliacaoId` | `List<AvaliacaoAlunoDTO>` | Retorna todas as entregas de uma avaliação |
| `visualizarDesempenho` | `Integer alunoId` | `List<AvaliacaoAlunoDTO>` | Retorna todas as entregas do aluno em todas as avaliações |
| `listarAvaliacoesPorTurma` | `Integer turmaId` | `List<AvaliacaoDTO>` | Retorna todas as avaliações de uma turma |
| `listarAvaliacoesDisponiveisPorAluno` | `Integer alunoId` | `List<AvaliacaoDTO>` | Retorna avaliações de turmas em que o aluno está com status `CURSANDO` |

---

## 5. Repositórios

Todos os repositórios ficam em `com.jacob.jp.srs.repositories` e estendem `JpaRepository<Entidade, Integer>`, herdando os métodos CRUD padrão (`findById`, `findAll`, `save`, `deleteById`, etc.).

Apenas os métodos customizados são listados abaixo.

---

### AlunoRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `existsByEmail(String email)` | `boolean` | Verifica se já existe aluno com o e-mail |
| `existsByMatricula(String matricula)` | `boolean` | Verifica se já existe aluno com a matrícula |
| `findByEmail(String email)` | `Aluno` | Busca aluno pelo e-mail |

---

### ProfessorRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `existsByEmail(String email)` | `boolean` | Verifica se já existe professor com o e-mail |
| `findByEmail(String email)` | `Professor` | Busca professor pelo e-mail |

---

### DisciplinaRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `existsByCodigo(String codigo)` | `boolean` | Verifica se existe disciplina com o código |
| `findByCodigo(String codigo)` | `Disciplina` | Busca disciplina pelo código |

---

### SemestreRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `findByDataInicialLessThanEqualAndDataFinalGreaterThanEqual(LocalDate d1, LocalDate d2)` | `Optional<Semestre>` | Busca o semestre ativo para uma data (ambos os parâmetros recebem `LocalDate.now()`) |

---

### TurmaRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `existsByProfessorIdAndSemestreIdAndHorario1AndAtivoTrue(...)` | `boolean` | Verifica conflito no horário principal do professor no semestre |
| `existsByProfessorIdAndSemestreIdAndHorario2AndAtivoTrue(...)` | `boolean` | Verifica conflito no horário secundário do professor no semestre |
| `findAllByProfessorId(Integer professorId)` | `List<Turma>` | Lista todas as turmas de um professor |
| `findAllAtivasNaoInscritasPorAluno(Integer alunoId)` *(JPQL)* | `List<Turma>` | Retorna turmas ativas em que o aluno ainda não está inscrito |

---

### TurmaAlunoRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `existsByAlunoIdAndTurmaId(Integer alunoId, Integer turmaId)` | `boolean` | Verifica se já existe inscrição do aluno na turma |
| `findByAlunoIdAndTurmaId(Integer alunoId, Integer turmaId)` | `Optional<TurmaAluno>` | Busca a inscrição específica |
| `findAllByAlunoId(Integer alunoId)` | `List<TurmaAluno>` | Lista todas as inscrições de um aluno |

---

### AvaliacaoRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `findAllByTurmaId(Integer turmaId)` | `List<Avaliacao>` | Lista todas as avaliações de uma turma |
| `findAllByTurmaIdIn(List<Integer> turmaIds)` | `List<Avaliacao>` | Lista avaliações de múltiplas turmas (usado em `listarAvaliacoesDisponiveisPorAluno`) |

---

### AvaliacaoAlunoRepository

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `existsByAlunoIdAndAvaliacaoId(Integer alunoId, Integer avaliacaoId)` | `boolean` | Verifica se o aluno já entregou a avaliação |
| `findByAlunoIdAndAvaliacaoId(Integer alunoId, Integer avaliacaoId)` | `AvaliacaoAluno` | Recupera a entrega existente para re-entrega |
| `findAllByAlunoId(Integer alunoId)` | `List<AvaliacaoAluno>` | Lista todas as entregas de um aluno |
| `findAllByAvaliacaoId(Integer avaliacaoId)` | `List<AvaliacaoAluno>` | Lista todas as entregas de uma avaliação |

---

## 6. Exceções Customizadas

Todas ficam em `com.jacob.jp.srs.exception` e estendem `RuntimeException`. Cada uma possui dois construtores: um sem argumentos (mensagem padrão) e um com `String message` (mensagem customizável).

| Exceção | Mensagem padrão | Quando é lançada |
|---------|-----------------|------------------|
| `EmailAlreadyExistsException` | `"Esse endereço de email já está em uso!"` | Ao registrar aluno ou professor com e-mail já cadastrado |
| `EmailNotFoundException` | *(definida na classe)* | Ao tentar login com e-mail não cadastrado |
| `AlunoNotFoundException` | `"Aluno não encontrado."` | Ao buscar aluno por ID inexistente |
| `ProfessorNotFoundException` | `"Professor não encontrado."` | Ao buscar professor por ID inexistente |
| `DisciplinaNotFoundException` | *(definida na classe)* | Ao buscar disciplina por código inexistente |
| `SemestreNotFoundException` | `"Não há semestre ativo para a data atual."` | Ao tentar operar sem semestre ativo cadastrado |
| `TurmaNotFoundException` | `"Turma não encontrada."` | Ao buscar turma por ID inexistente |
| `TurmaInativaException` | *(definida na classe)* | Ao tentar inscrever aluno ou criar avaliação em turma fechada |
| `TurmaAcessoNegadoException` | `"Você não tem permissão para acessar essa turma."` | Ao professor tentar criar avaliação em turma que não é sua |
| `HorarioConflictException` | `"Professor já possui uma turma nesse horário."` | Ao abrir turma com horário já ocupado pelo professor no semestre |
| `InscricaoDuplicadaException` | `"Aluno já inscrito nessa turma."` | Ao tentar inscrever aluno já inscrito na mesma turma |
| `InscricaoNotFoundException` | `"Inscrição não encontrada."` | Ao tentar trancar ou entregar atividade sem inscrição ativa |
| `AvaliacaoNotFoundException` | *(definida na classe)* | Ao buscar avaliação por ID inexistente |
| `AvaliacaoAlunoNotFoundException` | *(definida na classe)* | Ao buscar entrega por ID inexistente para lançar nota |
| `NotaInvalidaException` | `"Nota deve ser um valor entre 0 e 10."` | Ao lançar nota fora do intervalo `[0, 10]` |
