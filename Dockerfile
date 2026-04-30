# ===============================
# STAGE 1 - Build
# ===============================
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copia apenas arquivos necessários primeiro (cache eficiente)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Dá permissão ao wrapper
RUN chmod +x gradlew

# Baixa dependências (cache layer)
RUN ./gradlew dependencies --no-daemon

# Agora copia o restante do projeto
COPY src src

# Gera o JAR
RUN ./gradlew clean build -x test --no-daemon

# ===============================
# STAGE 2 - Runtime
# ===============================
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copia apenas o jar final
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
