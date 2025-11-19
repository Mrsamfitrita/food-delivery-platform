# Этап 1: Сборка JAR через Gradle
FROM gradle:8.7-jdk17 AS builder

# Копируем весь проект
COPY . /home/gradle/project
WORKDIR /home/gradle/project

# Собираем JAR (пропускаем тесты)
RUN gradle bootJar -x test

# Этап 2: Запуск
FROM eclipse-temurin:17-jdk-alpine
COPY --from=builder /home/gradle/project/build/libs/*.jar /user-service.jar
ENTRYPOINT ["java", "-jar", "/restaurant-service.jar"]