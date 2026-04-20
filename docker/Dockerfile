# ETAPA 1: Construção (Build) usando Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia o pom.xml e baixa as dependências (otimiza o cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código e gera o arquivo .war
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Rodar o servidor Tomcat
# Como você usa Java 21, o ideal é o Tomcat 10 ou 11
FROM tomcat:10.1-jdk21-temurin
WORKDIR /usr/local/tomcat/webapps/

# Copia o .war que foi gerado lá na etapa 1 (builder) para dentro do Tomcat
COPY --from=builder /app/target/*.war ./ROOT.war

EXPOSE 8080