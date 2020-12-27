package ru.norma24.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.norma24.data.DataHelper;

import java.util.Random;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RequestPage {
    private SelenideElement swalContainer = $(".swal2-html-container");
    private SelenideElement wrapTitle = $$("h3.warranty-wrap-title").first();
    private SelenideElement nameField = $$(".form-warranty input[placeholder='Имя']").first();
    private SelenideElement phoneField = $$(".form-warranty input[placeholder='Номер телефона']").first();
    private SelenideElement companyField = $(".form-warranty input[placeholder='ИНН / наименование компании']");
    private SelenideElement emailField = $(".form-warranty input[placeholder='Электронная почта']");
    private SelenideElement checkbox = $$(".form-warranty .custom-checkbox").first();
    private SelenideElement button = $$(".form-warranty button").first();
    private SelenideElement promo = $(".suggestions-promo");
    private ElementsCollection listItems = $$(".suggestions-suggestion");

    public RequestPage() {
        wrapTitle.shouldBe(visible).shouldHave(exactText("Оставить заявку"));
    }

    public void requestIsSent() {
        swalContainer.shouldBe(visible).shouldHave(exactText("Заявка успешно отправлена"));
    }

    public void sendRequest(DataHelper.FormInfo formInfo) {
        nameField.doubleClick();
        nameField.setValue(formInfo.getName());
        phoneField.setValue(formInfo.getPhone());
        selectCompany(formInfo);
        emailField.setValue(formInfo.getEmail());
        checkbox.click();
        button.click();
    }

    public void selectCompany(DataHelper.FormInfo formInfo) {
        val random = new Random();
        companyField.setValue(formInfo.getRegion());
        promo.waitUntil(visible, 5000);
        val item = random.nextInt(listItems.size());
        listItems.get(item).click();
    }
}
