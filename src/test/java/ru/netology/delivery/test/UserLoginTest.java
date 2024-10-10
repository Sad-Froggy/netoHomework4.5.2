package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.delivery.data.DataGenerator.Registration.getUser;

public class UserLoginTest {

    String APP_ADDRESS = "http://localhost:9999";

    @BeforeEach
    void start() {
        open(APP_ADDRESS);
    }

    @Test
    void shouldSuccessfullyLogin() {
        var user = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("button.button").click();
        $("h2")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Личный кабинет"));
    }

    @Test
    void shouldReturnErrorIfUserNotRegistered() {
        var user = getUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldReturnErrorIfUserIsBlocked() {
        var user = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

}
