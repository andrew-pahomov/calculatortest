package ru.norma24.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ExpressWarrantyPage {
    private SelenideElement sliderTitle = $("h1.slide-title");
    private SelenideElement getWarrantyButton = $("[data-target='#express_guarantee'].choose-calc-link");
    private SelenideElement callbackButton = $("[data-target='#back_call']");
    private ElementsCollection sliders = $$(".slider-calc .vue-slider-component.vue-slider-horizontal");
    private ElementsCollection sliderToolTips = $$("span.vue-slider-tooltip");
    private SelenideElement switchInput = $(".switch>label");
    private SelenideElement amountField = $("input.slider-calc-subtitle");
    private SelenideElement amountFee = $(".choose-calc-subtitle");
    private SelenideElement amountFeeKopeeks = $(".choose-calc-subtitle>span");
    private SelenideElement law44Select = $("[href='#44']");

    public ExpressWarrantyPage() {
        sliderTitle.shouldBe(visible).shouldHave(exactText("Экспресс банковские гарантии"));
    }

    public RequestPage getWarranty() {
        getWarrantyButton.click();
        return page(RequestPage.class);
    }

    public CallbackPage getCallback() {
        callbackButton.click();
        return page(CallbackPage.class);
    }

    public int getSliderAmountWarrantyWidth() {
        return getSliderWidth(sliders.first());
    }

    public int getSliderPeriodWarrantyWidth() {
        return getSliderWidth(sliders.last());
    }

    public int getSliderWidth(SelenideElement slider) {
        val padding = Integer.parseInt(slider.getCssValue("padding").substring(0, 1));
        val sliderSize = slider.getSize();
        val sliderWidth = sliderSize.getWidth();
        return sliderWidth - padding * 2;
    }

    public void moveSliderAmountWarranty(int xOffset) {
        actions().dragAndDropBy(sliders.first(), xOffset, 0).perform();
    }

    public void moveSliderPeriodWarranty(int xOffset) {
        actions().dragAndDropBy(sliders.last(), xOffset, 0).perform();
    }

    public double getSliderAmountWarrantyValue() {
        val text = sliderToolTips.get(0).getOwnText();
        if (text.contains(".")){
            val amountInKopeeks = Integer.parseInt(text.replace(".",""));
            return (double) amountInKopeeks/100;
        }
        return Integer.parseInt(text);
    }

    public int getSliderPeriodWarrantyValue() {
        return Integer.parseInt(sliderToolTips.get(2).getOwnText());
    }

    public void performSwitch() {
        switchInput.click();
    }

    public void fillAmount(String amount) {
        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        amountField.setValue(amount);
    }

    public void law44Select() {
        law44Select.click();
    }

    public double getAmountFee() {
        val text = amountFee.getOwnText();
        val kopeeksText = amountFeeKopeeks.getOwnText();
        val endOfString = text.indexOf(" ₽");
        val feeInKopeeks = Integer.parseInt(text.substring(0, endOfString).replace("\n", "")
                .replace(".", "").replace(" ", "") + kopeeksText);
        return (double) feeInKopeeks / 100;
    }
}
