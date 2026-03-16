FROM eclipse-temurin:21-jdk

WORKDIR /app

# copia o código do projeto
COPY ../src /app/src

# compila todos os arquivos java
RUN find src -name "*.java" -exec javac {} +

# roda o programa
CMD ["java", "-cp", "src", "Main"]