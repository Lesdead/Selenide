import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrder {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldValidDeliveryOrder() {
        $("[data-test-id=city] .input__control").setValue("Иркутск");
        String dateNowPlus5 = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").setValue(dateNowPlus5);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+12345678901");
        $("[data-test-id=agreement] .checkbox__box").click();
        $$x("//*[text()=\"Забронировать\"]").get(0).click();
        $(withText("Встреча успешно")).should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNoValidCityNotInRussian() {
        $("[data-test-id=city] .input__control").setValue("Irkutsk");
        String dateNowPlus5 = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").setValue(dateNowPlus5);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+12345678901");
        $("[data-test-id=agreement] .checkbox__box").click();
        $$x("//*[text()=\"Забронировать\"]").get(0).click();
        $(byText("Доставка в выбранный город недоступна")).should(visible);
    }

    @Test
    void shouldNoValidCityNotRussian() {
        $("[data-test-id=city] .input__control").setValue("Иерусалим");
        String dateNowPlus5 = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").setValue(dateNowPlus5);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+12345678901");
        $("[data-test-id=agreement] .checkbox__box").click();
        $$x("//*[text()=\"Забронировать\"]").get(0).click();
        $(byText("Доставка в выбранный город недоступна")).should(visible);
    }

    @Test
    void shouldNoValidDate() {
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=city] .input__control").setValue("Иркутск");
        String dateNowPlus1 = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").setValue(dateNowPlus1);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+12345678901");
        $("[data-test-id=agreement] .checkbox__box").click();
        $$x("//*[text()=\"Забронировать\"]").get(0).click();
        $(byText("Заказ на выбранную дату невозможен")).should(visible);
    }

    @Test
    void shouldNoValidName() {
        $("[data-test-id=city] .input__control").setValue("Иркутск");
        String dateNowPlus5 = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").setValue(dateNowPlus5);
        $("[data-test-id=name] .input__control").setValue("Ivanov Ivan");
        $("[data-test-id=phone] .input__control").setValue("+12345678901");
        $("[data-test-id=agreement] .checkbox__box").click();
        $$x("//*[text()=\"Забронировать\"]").get(0).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).should(visible);
    }

    @Test
    void shouldNoValidPhone() {
        $("[data-test-id=city] .input__control").setValue("Иркутск");
        String dateNowPlus5 = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").setValue(dateNowPlus5);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+1234567890");
        $("[data-test-id=agreement] .checkbox__box").click();
        $$x("//*[text()=\"Забронировать\"]").get(0).click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).should(visible);
    }

    @Test
    void shouldNoValidNoAgreement() {
        $("[data-test-id=city] .input__control").setValue("Иркутск");
        String dateNowPlus5 = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").setValue(dateNowPlus5);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] .input__control").setValue("+12345678901");
        $$x("//*[text()=\"Забронировать\"]").get(0).click();
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).should(visible);
    }

}
