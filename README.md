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

Para desenvolvimento local com volumes de JSP/CSS/JS/classes:

```bash
cd docker
docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build
```

Esse modo de desenvolvimento nao sobrescreve o `ROOT` inteiro, entao nao esconde as dependencias em `WEB-INF/lib`.

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

### Subir em modo desenvolvimento

```bash
docker compose -f docker-compose.yml -f docker-compose.dev.yml up
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

O container final da aplicacao usa Tomcat e nao inclui o binario `mvn`.

Para rebuildar a aplicacao com Maven via Docker:

```bash
cd docker
docker compose build app
docker compose up -d --force-recreate app
```

---

## Observações

- Projeto usa **Servlets/JSP sobre Tomcat**
- Estrutura baseada em **MVC**
- Conexão com banco via **JDBC**
- Migrações com **Flyway**
- Variáveis carregadas via **.env**
- Containers isolam todo o ambiente
