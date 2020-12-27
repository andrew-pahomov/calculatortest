package ru.norma24.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class StartPage {
    private SelenideElement infoSection = $(".info-section");
    private SelenideElement expressWarrantyBlock = $(".info-block.warranty");

    public StartPage() {
        infoSection.shouldBe(visible);
    }

    public ExpressWarrantyPage chooseProduct() {
        expressWarrantyBlock.click();
        return page(ExpressWarrantyPage.class);
    }
}
