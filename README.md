# TobIAs

Projeto simples usando **Java puro**, **PostgreSQL** e **PgAdmin**, rodando com **Docker Compose**.

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

```
tobias/
│
├── docker/
│   ├── docker-compose.yml
│   ├── Dockerfile
│   └── .dockerignore
│
├── src/
│   └── main/java/
│       ├── application/
│       ├── util/
│       ├── model/
│       ├── controller/
│       ├── repository/
│       └── view/
│
├── .env
├── pom.xml
└── README.md
```

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
4. Executar o `application.Main`

---

## Containers

| Container    | Função            |
|-------------|-------------------|
| java_app    | aplicação Java    |
| postgres_db | banco PostgreSQL  |
| pgadmin     | interface gráfica |

---

## Conexão com o Banco (Java)

```
jdbc:postgresql://db:5432/tobiasdb
```

---

## Importante

Dentro do Docker:

- Não use `localhost`
- Use `db` como host do banco

---

## Acessar PgAdmin

Abra no navegador:

```
http://localhost:5051
```

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
Database: tobiasdb
Username: tobias
Password: P0mbadosertao
```

Clique em **Save**

---

## Comandos úteis

### Subir projeto

```bash
docker compose up
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

## Executar comandos Maven dentro do container

Caso queira rodar comandos manualmente na aplicação Java, é possível acessar o container.

### Entrar no container da aplicação

```bash
docker exec -it java_app bash
```

### Compilar

```bash
mvn clean compile
```

### Rodar aplicação

```bash
mvn exec:java -Dexec.mainClass="application.Main"
```

### Gerar arquivo `.jar`

```bash
mvn clean package
```

### Executar o `.jar`

```bash
java -jar target/tobias-1.0-SNAPSHOT.jar
```

---

## Observações

- Projeto usa **Java puro (sem framework)**
- Estrutura baseada em **MVC**
- Conexão com banco via **JDBC**
- Variáveis carregadas via **.env**
- Containers isolam todo o ambiente