# Estágio 1: Build (Compilar o Java usando Maven)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /usr/src/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Run (Rodar a aplicação com fontes instaladas)
FROM eclipse-temurin:21-jre

# Instalar fontes necessárias para o gerador de PDF
RUN apt-get update && apt-get install -y fontconfig libfreetype6 && rm -rf /var/lib/apt/lists/*

# Copiar os arquivos gerados no estágio de build
COPY --from=build /usr/src/app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /usr/src/app/target/quarkus-app/*.jar /deployments/
COPY --from=build /usr/src/app/target/quarkus-app/app/ /deployments/app/
COPY --from=build /usr/src/app/target/quarkus-app/quarkus/ /deployments/quarkus/

CMD ["java", "-jar", "/deployments/quarkus-run.jar"]