# Roteiro de Slides - TobIAs

## Slide 1 - Capa

**TobIAs**

- Sistema de apoio a gestao academica com recursos de IA
- Equipe: [nomes dos integrantes]
- Disciplina: Programacao Orientada a Objetos
- Professor/orientador: [nome do professor]

## Slide 2 - Visao Geral do Sistema

O TobIAs e um sistema web para organizar disciplinas, materiais, atividades, respostas de alunos e comunicacao entre professor e turma.

Pontos principais:

- Professores criam disciplinas, materiais, atividades, questoes e avisos.
- Alunos entram em disciplinas por codigo, acessam materiais e enviam atividades.
- A IA auxilia na geracao de questoes e relatorios de desempenho.
- O sistema usa Java Web com Servlets/JSP, PostgreSQL, Flyway e arquitetura em camadas.

## Slide 3 - Problema, Objetivo e Funcionalidades

**Problema:** professores precisam centralizar conteudos, atividades, entregas e comunicacao com alunos em um unico ambiente.

**Objetivo:** criar uma aplicacao academica simples, funcional e organizada, aplicando conceitos de Programacao Orientada a Objetos.

**Funcionalidades principais:**

- Cadastro, login e edicao de perfil.
- Criacao e entrada em disciplinas.
- Cadastro de materiais com texto e arquivo.
- Criacao de atividades com questoes abertas e fechadas.
- Envio e visualizacao de respostas.
- Mural de avisos e comentarios.
- Geracao de questoes e relatorios com IA.

## Slide 4 - Diagrama de Casos de Uso

Inserir o diagrama:

`docs/diagramas/UseCaseDiagram1.jpg`

Explicacao:

- **Usuario:** cadastra conta, faz login, edita perfil e acessa o dashboard.
- **Professor:** cria disciplinas, materiais, atividades, questoes, avisos e relatorios.
- **Aluno:** entra em disciplinas, consulta materiais, responde atividades e comenta no mural.
- **IA:** gera questoes a partir de material de apoio e cria relatorio de desempenho.

## Slide 5 - Diagrama de Classes Completo

Inserir o diagrama:

`docs/diagramas/classDiagram.png`

Explicacao:

- As classes de modelo representam as entidades do sistema.
- Os controllers recebem as requisicoes HTTP.
- Os DAOs fazem a persistencia no banco de dados.
- Os servicos de IA concentram a integracao com Gemini.
- A estrutura segue uma separacao proxima ao MVC.

## Slide 6 - POO: Heranca e Polimorfismo em Usuarios

Classes principais:

- `User`
- `Teacher`
- `Student`

Explicacao:

- `Teacher` e `Student` herdam de `User`.
- Atributos comuns ficam em `User`: nome, CPF, email, senha, id e foto.
- Cada subclasse redefine comportamentos conforme seu papel.
- O sistema usa polimorfismo ao tratar professor e aluno como `User`.

Trecho para mostrar:

```java
public class Teacher extends User {
    @Override
    public String getRoleName() { return "PROFESSOR"; }

    @Override
    public boolean canManageDiscipline() { return true; }
}
```

## Slide 7 - POO: Questao Abstrata, Heranca e Polimorfismo

Classes principais:

- `Question`
- `OpenQuestion`
- `ClosedQuestion`

Explicacao:

- `Question` e uma classe abstrata.
- Ela guarda dados comuns: id, peso, enunciado e atividade.
- `OpenQuestion` representa questoes discursivas.
- `ClosedQuestion` representa questoes de multipla escolha.
- O metodo `getType()` e implementado de forma diferente em cada subclasse.

Trecho para mostrar:

```java
public abstract class Question {
    private int id;
    private float weight;
    private String statement;
    private int activityId;

    public abstract String getType();
}
```

## Slide 8 - POO: Encapsulamento, Associacao e Composicao

**Encapsulamento:**

- Os atributos das classes sao privados.
- O acesso ocorre por getters e setters.
- Isso protege os dados internos dos objetos.

**Associacao:**

- `Discipline` se associa a `Teacher`, `Activity`, `Material`, `Warning` e `Student`.
- `Activity` se associa a `Question`.
- `ActivitySubmission` se associa a `SubmissionAnswer`.

**Composicao/agregacao:**

- `Warning` possui uma lista de `Comment`.

Trecho para mostrar:

```java
public class Warning {
    private List<Comment> comments = new ArrayList<>();
}
```

## Slide 9 - DER: Modelagem do Banco de Dados

Entidades principais:

- `usuario`
- `professor`
- `aluno`
- `disciplina`
- `matricula`
- `material`
- `atividade`
- `questao`
- `dissertativa`
- `multipla_escolha`
- `submissao`
- `resposta_questao`
- `aviso`
- `comentario_aviso`

Relacionamentos principais:

- `usuario 1:1 professor`
- `usuario 1:1 aluno`
- `professor 1:N disciplina`
- `aluno N:N disciplina`, por meio de `matricula`
- `disciplina 1:N atividade`
- `atividade 1:N questao`
- `atividade 1:N submissao`
- `submissao 1:N resposta_questao`
- `aviso 1:N comentario_aviso`

## Slide 10 - Organizacao do Codigo

Pacotes principais:

- `model`: classes de dominio.
- `controller`: Servlets que controlam as rotas e fluxos.
- `dao`: acesso ao banco de dados com JDBC.
- `service.ai`: integracao com IA.
- `config`: banco, Flyway, senha e configuracoes de IA.
- `filter`: controle de autenticacao.
- `WEB-INF/templates`: telas JSP.

Fluxo geral:

1. O usuario acessa uma rota.
2. O controller recebe a requisicao.
3. O DAO busca ou salva dados no banco.
4. O model representa os dados.
5. A JSP exibe a resposta na interface.

## Slide 11 - Trecho de Codigo: Criacao de Atividades

Explicacao:

- Apenas professores podem criar atividades.
- O sistema valida a permissao antes de salvar.
- Depois de criar a atividade, salva as questoes ligadas a ela.
- A soma dos pesos das questoes nao pode ultrapassar o peso da atividade.

Trecho para mostrar:

```java
if (!isProfessor(request, disciplineId)) {
    FlashMessage.set(request, "danger", "Somente o professor pode criar atividades.");
    return;
}

Integer activityId = dao.createActivity(activity);

if (activityId != null) {
    saveQuestionsFromRequest(request, activityId);
}
```

## Slide 12 - Trecho de Codigo: Envio de Respostas

Explicacao:

- Apenas alunos matriculados podem enviar respostas.
- O sistema impede envio duplicado.
- A submissao e as respostas sao salvas juntas.
- O DAO usa transacao para manter consistencia.

Trecho para mostrar:

```java
if (submissionDAO.hasSubmission(activityId, studentId)) {
    FlashMessage.set(request, "danger", "Voce ja enviou essa atividade.");
    return;
}

Integer submissionId = submissionDAO.createSubmission(activityId, studentId, answers);
```

## Slide 13 - Trecho de Codigo: Uso de IA

Explicacao:

- O controller `/Ai` recebe uma requisicao para gerar questoes.
- `QuestionGenerationService` escolhe o prompt correto.
- `AiClient` envia o prompt para a API Gemini.
- A resposta volta em JSON e pode preencher questoes abertas ou fechadas.

Trecho para mostrar:

```java
GeneratedQuestionsResponse aiResponse = aiService.generate(aiRequest);
mapper.writeValue(response.getWriter(), aiResponse);
```

## Slide 14 - Desafios e Aprendizados

**Desafios:**

- Separar corretamente responsabilidades entre controller, DAO, model e view.
- Modelar professor e aluno com permissoes diferentes.
- Implementar questoes abertas e fechadas com heranca e polimorfismo.
- Garantir consistencia ao salvar submissao e respostas.
- Integrar o sistema com uma API externa de IA.

**Aprendizados:**

- Aplicacao pratica de POO em um sistema real.
- Uso de Servlets, JSP, JDBC, PostgreSQL e Flyway.
- Organizacao em camadas.
- Controle de autenticacao e permissao.
- Integração entre Java e servicos externos.

## Slide 15 - Melhorias Futuras e Encerramento

Melhorias futuras:

- Correcao automatica mais avancada para questoes abertas.
- Painel de notas e historico de desempenho.
- Relatorios com graficos.
- Notificacoes para novas atividades e avisos.
- Anexos nas respostas dos alunos.
- Testes automatizados.

Conclusao:

O TobIAs aplica conceitos de Programacao Orientada a Objetos em um sistema academico funcional, com gestao de disciplinas, atividades, materiais, mural, submissao de respostas e recursos de IA.
