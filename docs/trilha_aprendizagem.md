# Trilha de Aprendizagem para o projeto TobIAs

Este guia é para quem conhece pouco sobre o tema e quer entender o sistema passo a passo.
Ele mostra os conceitos básicos, as tecnologias usadas e os links de documentação oficiais, além dos vídeos sugeridos.

---

## 1. Objetivo

A ideia é aprender o suficiente para:
- entender como este projeto funciona
- executar o sistema localmente
- identificar as partes principais do código
- fazer pequenas mudanças com segurança

---

## 2. O que você já deve saber

Antes de seguir este guia, é útil ter alguma familiaridade com:
- programação básica
- conceitos de variáveis, funções e classes
- usar o terminal do sistema

Mas se você ainda não sabe tudo isso, não tem problema: os links abaixo ajudam a começar.

---

## 3. Aprendizado passo a passo

### 3.1 Java básico e Programação Orientada a Objetos

Este projeto é feito com Java puro.

- Java oficial: https://docs.oracle.com/en/java/
- Tutorial Java para iniciantes: https://www.oracle.com/java/technologies/javase/jdk11-docs.html
- Conceitos de POO: classes, objetos, herança, encapsulamento e polimorfismo

### 3.2 Maven

O Maven é usado para compilar e empacotar o projeto.

- Documentação do Maven: https://maven.apache.org/guides/index.html
- Primeiro projeto Maven: https://maven.apache.org/guides/getting-started/

### 3.3 Docker e Docker Compose

Este sistema usa Docker para isolar a aplicação Java e o banco PostgreSQL.

- Introdução ao Docker: https://docs.docker.com/get-started/
- Docker Compose: https://docs.docker.com/compose/

### 3.4 PostgreSQL

O banco de dados usado pelo projeto é o PostgreSQL.

- Documentação PostgreSQL: https://www.postgresql.org/docs/
- Guia básico de PostgreSQL: https://www.postgresql.org/docs/current/tutorial-start.html

### 3.5 Java Web e Tomcat

Este projeto é uma **aplicação web Java** que roda em um servidor **Apache Tomcat**.

#### O que é Tomcat?

O Tomcat é um servidor web e de aplicações Java. Ele funciona assim:

1. **Cliente faz uma requisição** (navegador ou aplicação)
   - Exemplo: `GET http://localhost:8080/home`

2. **Tomcat recebe a requisição**
   - Interpreta o caminho (`/home`) e procura qual componente deve responder

3. **Tomcat encontra a rota correspondente** (Servlet)
   - Usa a anotação `@WebServlet("/home")` para encontrar a classe correta

4. **Controller (Servlet) processa a requisição**
   - Executa a lógica do negócio
   - Consulta o banco de dados se necessário
   - Prepara os dados

5. **Resposta é gerada**
   - Pode ser HTML (redirecionando para um JSP)
   - Ou JSON para APIs
   - Ou qualquer outro formato

6. **Resposta volta ao cliente**
   - O navegador recebe o HTML/JSON e renderiza

#### Estrutura na aplicação

- **Servlets** (Controllers): Recebem requisições e controlam o fluxo
  - Localização: `src/main/java/com/tobias/controller/`
  - Exemplo: `@WebServlet("/home")` sobre uma classe que estende `HttpServlet`

- **JSP** (Views): Templates HTML que renderizam dados
  - Localização: `src/main/webapp/`
  - Exemplo: `index.jsp`

- **Service**: Lógica de negócio (consultas, processamento)
  - Localização: `src/main/java/com/tobias/service/`

- **Model**: Classes que representam dados (entidades do banco)
  - Localização: `src/main/java/com/tobias/model/`

#### Documentação

- Documentação Tomcat: https://tomcat.apache.org/tomcat-11.0-doc/
- Jakarta Servlet API (versão moderna usada aqui): https://jakarta.ee/specifications/servlet/

### 3.6 Bons hábitos de leitura de código

Quando for abrir o projeto, comece por estas pastas:
- `src/main/java/com/tobias/Main.java` - ponto de entrada do sistema
- `src/main/java/com/tobias/DatabaseConfig.java` - configuração de conexão com banco
- `src/main/java/com/tobias/controller/` - classes para controlar ações (ex: endpoints web)
- `src/main/java/com/tobias/service/` - lógica de negócio
- `src/main/java/com/tobias/model/` - classes de dados (ex: entidades do banco)

> Observação: a estrutura do projeto foi simplificada para projetos pequenos. Usamos pacotes curtos como `com.tobias` em vez de caminhos longos, facilitando a navegação e entendimento. As pastas estão preparadas para crescer com o projeto.

---

## 4. Estrutura do projeto

Este projeto usa a seguinte estrutura principal:

- `docker/` - arquivos do Docker e Docker Compose
- `src/main/java/` - código Java
- `src/main/resources/` - templates e recursos estáticos
- `pom.xml` - configuração do Maven

---

## 5. Como rodar o sistema

1. Certifique-se de ter Docker e Docker Compose instalados.
2. Abra o terminal na pasta raiz do projeto.
3. Execute:

```bash
cd docker
docker compose up --build
```

4. O projeto deve subir com:
- aplicação Java
- banco PostgreSQL
- PgAdmin

---

## 6. Vídeos recomendados

Assista estes vídeos para entender melhor Docker e a forma como sistemas como este funcionam:

- https://youtu.be/caAFYcUcgBc?si=ZKSFf0_M6e6p0GTR
- https://youtu.be/J9y5Spv8WsE?si=KuK7fXzZi-sZukEy

---

## 7. Recursos extras

- Documentação oficial Java: https://docs.oracle.com/en/java/
- Documentação oficial Docker: https://docs.docker.com/
- Documentação oficial PostgreSQL: https://www.postgresql.org/docs/
- Documentação oficial Maven: https://maven.apache.org/

---

## 8. Como usar este guia

1. Leia os tópicos na ordem sugerida.
2. Abra o projeto no editor e navegue pelas pastas indicadas.
3. Assista aos vídeos e use as documentações para esclarecer dúvidas.
4. Volte ao código e tente rodar o sistema localmente.

Boa sorte e divirta-se aprendendo o sistema TobIAs!
