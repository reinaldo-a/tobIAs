# TobIAs

Projeto web usando **Java com Servlets/JSP**, **PostgreSQL**, **Flyway** e **PgAdmin**, rodando com **Docker Compose**.

- [Trilha de Aprendizagem](docs/trilha_aprendizagem.md)

---

## Tecnologias

- Java (OpenJDK 21)
- PostgreSQL
- PgAdmin
- Docker
- Docker Compose
- Maven

---

## Estrutura do Projeto

A estrutura do projeto foi mantida simples para facilitar o entendimento. Para projetos pequenos, o importante é separar claramente:

- configuração de infraestrutura (`docker/`)
- código da aplicação (`src/main/java/`)
- views JSP e assets web (`src/main/webapp/`)
- documentação (`docs/`)

Estrutura atual:

```
tobias/
├── docker/
│   ├── docker-compose.yml
│   ├── Dockerfile
│   └── .dockerignore
├── docs/
│   ├── documento_de_requisitos/
│   ├── documento_de_visão/
│   └── padroes-dev/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/tobias/
│       │       ├── application/
│       │       ├── config/
│       │       └── controller/
│       ├── resources/
│       │   └── bd/migration/
│       └── webapp/
│           ├── WEB-INF/
│           └── assets/
├── .env
├── pom.xml
└── README.md
```

> Para projetos pequenos, usamos pacotes simples como `com.tobias`. A organizacao atual separa entrada da aplicacao (`application`), configuracoes (`config`) e servlets (`controller`).

---

## Pré-requisitos

- Docker
- Docker Compose

Verificar instalação:

```bash
docker --version
docker compose version
```

---

## Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
POSTGRES_USER=tobias_user
POSTGRES_PASSWORD=Pombadosertao
POSTGRES_DB=tobias_db
DB_HOST=db
DB_PORT=5432

PGADMIN_DEFAULT_EMAIL=admin@admin.com
PGADMIN_DEFAULT_PASSWORD=admin

# IA local gratuita para testes, sem chave de API
OLLAMA_BASE_URL=http://ollama:11434
OLLAMA_MODEL=llama3.2:1b

# Opcional: se existir chave, o sistema usa OpenAI no lugar do Ollama
OPENAI_API_KEY=
OPENAI_MODEL=gpt-4.1-mini
```

---

## Como Rodar o Projeto

```bash
cd docker
docker compose up --build
```

Esse comando vai:

1. Baixar as imagens
2. Criar os containers
3. Compilar o Java com Maven
4. Publicar a aplicacao no Tomcat

Depois do primeiro build, para subir novamente:

```bash
cd docker
docker compose up
```

O `docker-compose.yml` ja inclui os volumes de desenvolvimento. Arquivos JSP, CSS e JS montados por volume atualizam sem rebuild da imagem.

### IA local gratuita para testes

Para gerar relatórios sem chave de API, suba o Ollama e baixe um modelo pequeno:

```bash
cd docker
docker compose --profile ai up -d ollama
docker compose exec ollama ollama pull llama3.2:1b
docker compose restart app
```

Com `OPENAI_API_KEY` vazio, a aplicação usa o Ollama em `OLLAMA_BASE_URL`.

Quando alterar classes Java, recompile dentro do container:

```bash
docker compose exec app mvn -f /workspace/pom.xml compile
docker compose restart app
```

O comando precisa apontar para `/workspace/pom.xml` porque, dentro do container, o Tomcat roda em `/usr/local/tomcat/webapps`, mas o projeto Maven fica montado em `/workspace`.

---

## Containers

| Container    | Função            |
|-------------|-------------------|
| java_app    | aplicação Java    |
| postgres_db | banco PostgreSQL  |
| pgadmin     | interface gráfica |

---

## Conexão com o Banco (Java)

`jdbc:postgresql://db:5432/tobias_db`

---

## Importante

Dentro do Docker:

- Não use `localhost`
- Use `db` como host do banco

---

## Acessar PgAdmin

Abra no navegador:

`http://localhost:5050`

Login:

```
Email: admin@admin.com
Senha: admin
```

---

## Configurar o Banco no PgAdmin

Após logar:

Clique em **Add New Server**

### Aba General

```
Name: PostgreSQL
```

### Aba Connection

```
Host: db
Port: 5432
Database: tobias_db
Username: tobias_user
Password: Pombadosertao
```

Clique em **Save**

---

## Comandos úteis

### Subir projeto

```bash
docker compose up
```

### Rebuildar a imagem da aplicação

```bash
docker compose up -d --build --force-recreate app
```

### Parar containers

```bash
docker compose down
```

### Apagar tudo (inclui banco)

```bash
docker compose down -v
```

---

## Persistência de Dados

Os dados do PostgreSQL ficam armazenados em um volume Docker:

```
postgres_data
```

Isso garante que os dados não sejam perdidos ao reiniciar os containers.

---

## Maven

O container da aplicacao inclui Maven para facilitar o desenvolvimento.

Para compilar classes Java dentro do container:

```bash
cd docker
docker compose exec app mvn -f /workspace/pom.xml compile
```

Em seguida, reinicie o Tomcat:

```bash
docker compose restart app
```

Se quiser limpar classes antigas antes de compilar:

```bash
docker compose exec app mvn -f /workspace/pom.xml clean compile
docker compose restart app
```

Nao rode `mvn compile` dentro de `/usr/local/tomcat/webapps`, porque essa pasta nao tem `pom.xml`. Use sempre:

```bash
mvn -f /workspace/pom.xml compile
```

---

## Observações

- Projeto usa **Servlets/JSP sobre Tomcat**
- Estrutura baseada em **MVC**
- Conexão com banco via **JDBC**
- Migrações com **Flyway**
- Variáveis carregadas via **.env**
- Containers isolam todo o ambiente
