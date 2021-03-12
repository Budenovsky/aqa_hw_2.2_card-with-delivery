package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.ExactText;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    String getDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSendForm() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        String minDeliveryDate = getDate(3);
        $("[data-test-id='date'] input").setValue(minDeliveryDate);
        $("[data-test-id='name'] input").setValue("Вероника Степанова");
        $("[data-test-id='phone'] input").setValue("+79990001122");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + minDeliveryDate));
    }
}
