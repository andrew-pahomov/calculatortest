package ru.norma24.page;

import com.codeborne.selenide.SelenideElement;
import ru.norma24.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CallbackPage {
    private SelenideElement wrapTitle = $$("h3.warranty-wrap-title").last();
    private SelenideElement nameField = $$(".client-request-form  input[placeholder='Имя']").last();
    private SelenideElement phoneField = $$(".client-request-form input[placeholder='Номер телефона']").last();
    private SelenideElement checkbox = $$(".client-request-form .custom-checkbox").last();
    private SelenideElement button = $$(".client-request-form button").last();
    private SelenideElement swalContainer = $(".swal2-html-container");

    public CallbackPage() {
        wrapTitle.shouldBe(visible).shouldHave(exactText("Обратный звонок"));
    }

    public void sendCallback(DataHelper.FormInfo formInfo) {
        nameField.doubleClick();
        nameField.setValue(formInfo.getName());
        phoneField.setValue(formInfo.getPhone());
        checkbox.click();
        button.click();
    }

    public void callbackIsSent() {
        swalContainer.shouldBe(visible).shouldHave(exactText("Заявка успешно отправлена"));
    }
}
