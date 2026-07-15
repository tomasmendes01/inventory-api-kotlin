FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# 1º: só os ficheiros que descrevem as dependências
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 2º: só agora o código-fonte
COPY src src
RUN ./gradlew bootJar -x test --no-daemon

# --- estágio final: só o necessário para correr a app ---
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

LABEL authors="tomas"

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]