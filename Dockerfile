FROM maven:3.6-openjdk-17

WORKDIR /applicationSecurity
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run