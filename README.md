# LearnAI

Projeto simples usando **Java puro**, **PostgreSQL** e **PgAdmin**, rodando em containers com **Docker Compose**.

---

# Tecnologias

* Java (OpenJDK 21)
* PostgreSQL
* PgAdmin
* Docker
* Docker Compose

---

# Estrutura do Projeto

```
tobias/
│
├── docker/
│   ├── docker-compose.yml
│   ├── Dockerfile
│   └── .dockerignore
│
├── src/
│   ├── model/
│   ├── controller/
│   ├── view/
│   ├── repository/
│   ├── database/
│   └── Main.java
│
├── .env
└── README.md
```

Descrição:

* `src/` → código fonte Java organizado em MVC
* `docker/` → arquivos de infraestrutura Docker
* `docker-compose.yml` → define os containers do projeto
* `Dockerfile` → container da aplicação Java
* `.env` → variáveis de ambiente do banco
* `README.md` → documentação do projeto

---

# Pré-requisitos

Antes de rodar o projeto você precisa ter instalado:

* Docker
* Docker Compose

Verificar instalação:

```
docker --version
docker compose version
```

---

# Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```
DB_NAME=learn_db
DB_USER=learn_user
DB_PASSWORD=learn_pass

PGADMIN_EMAIL=admin@admin.com
PGADMIN_PASSWORD=admin
```

Essas variáveis configuram:

* banco PostgreSQL
* login do PgAdmin

---

# Como Rodar o Projeto

Entre na pasta **docker**:

```
cd docker
```

Depois execute:

```
docker compose up --build
```

O comando irá:

1. Baixar as imagens necessárias
2. Criar os containers
3. Compilar o código Java
4. Executar a aplicação

---

# Containers Criados

| Container   | Função                     |
| ----------- | -------------------------- |
| java_app    | aplicação Java             |
| postgres_db | banco PostgreSQL           |
| pgadmin     | interface gráfica do banco |

---

# Acessar o Banco de Dados

O banco roda dentro do container **postgres_db**.

Dados de conexão:

```
Host: db
Port: 5432
Database: learn_db
User: learn_user
Password: learn_pass
```

String de conexão Java:

```
jdbc:postgresql://db:5432/learn_db
```

Importante:

Dentro do Docker o host **não é localhost**, é **db**.

---

# Acessar o PgAdmin

Abra no navegador:

```
http://localhost:5050
```

Login:

```
Email: admin@admin.com
Senha: admin
```

---

# Conectar o Banco no PgAdmin

Depois de logar:

Clique em **Add New Server**

Aba General

```
Name: PostgreSQL
```

Aba Connection

```
Host: db
Port: 5432
Database: learn_db
Username: learn_user
Password: learn_pass
```

Clique em **Save**.

---

# Parar o Projeto

Para parar os containers:

```
docker compose down
```

---

# Remover containers e banco

Se quiser apagar tudo (inclusive o banco):

```
docker compose down -v
```

---

# Persistência de Dados

Os dados do PostgreSQL ficam em um **volume Docker**, então não são perdidos ao reiniciar os containers.

Volume utilizado:

```
postgres_data
```

---

# Reiniciar o Projeto

Para subir novamente:

```
docker compose up
```

---

# Observações

* O código Java deve ficar dentro da pasta `src`
* O banco usa o hostname `db`
* O PgAdmin é apenas uma interface gráfica para gerenciar o banco
* Os containers são gerenciados a partir da pasta `docker`
