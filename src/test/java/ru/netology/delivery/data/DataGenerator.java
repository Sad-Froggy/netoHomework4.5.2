package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {
    }

        private static final Faker faker = new Faker(new Locale("en"));

        private static final RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();


        public static String getLogin() {
            return faker.name().username();
        }

        public static String getPassword() {
        return faker.internet().password();
        }

        public static class Registration {

            private Registration() {
            }

            public static RegistrationDto getUser(String status) {
                return new RegistrationDto(getLogin(), getPassword(), status);
            }

            public static RegistrationDto getRegisteredUser(String status) {
                RegistrationDto user = getUser(status);
                given()
                        .spec(spec)
                        .body(new RegistrationDto("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
                        .when()
                        .post("/api/system/users")
                        .then()
                        .statusCode(200);
                return user;
            }

        }

        @Value
    public static class RegistrationDto {
            String login;
            String password;
            String status;
        }

}
