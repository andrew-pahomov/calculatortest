package ru.norma24.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.norma24.data.DataHelper;
import ru.norma24.page.ExpressWarrantyPage;
import ru.norma24.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.norma24.data.DataHelper.getFormInfo;

public class CalculatorPositiveTest {

    ExpressWarrantyPage openProductPage() {
        val startPage = open("https://test.norma24.ru/", StartPage.class);
        val warrantyProductPage = startPage.chooseProduct();
        warrantyProductPage.law44Select();
        return warrantyProductPage;
    }

    @Test
    void checkSliderAmountWarrantyLimits() {
        val warrantyProductPage = openProductPage();
        val sliderWarrantyInfo = DataHelper.getSliderAmountWarrantyInfo();
        val sliderWidth = warrantyProductPage.getSliderAmountWarrantyWidth();
        warrantyProductPage.moveSliderAmountWarranty(-sliderWidth / 2);
        assertEquals(sliderWarrantyInfo.getMinValue(), warrantyProductPage.getSliderAmountWarrantyValue());
        var amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.moveSliderAmountWarranty(sliderWidth / 2);
        assertEquals(sliderWarrantyInfo.getMaxValue(), warrantyProductPage.getSliderAmountWarrantyValue());
        amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
    }

    @Test
    void checkSliderPeriodWarrantyLimits() {
        val warrantyProductPage = openProductPage();
        val sliderWarrantyInfo = DataHelper.getSliderPeriodWarrantyInfo();
        val sliderWidth = warrantyProductPage.getSliderPeriodWarrantyWidth();
        warrantyProductPage.moveSliderPeriodWarranty(-sliderWidth / 2);
        assertEquals(sliderWarrantyInfo.getMinValue(), warrantyProductPage.getSliderPeriodWarrantyValue());
        var amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.moveSliderPeriodWarranty(sliderWidth / 2);
        assertEquals(sliderWarrantyInfo.getMaxValue(), warrantyProductPage.getSliderPeriodWarrantyValue());
        amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
    }

    @Test
    void shouldSendValidDefaultFormRequest() {
        val warrantyProductPage = openProductPage();
        val formInfo = getFormInfo();
        val amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        val requestPage = warrantyProductPage.getWarranty();
        requestPage.sendRequest(formInfo);
        requestPage.requestIsSent();
    }

    @Test
    void shouldSendCallbackRequest() {
        val warrantyProductPage = openProductPage();
        val formInfo = getFormInfo();
        val callbackPage = warrantyProductPage.getCallback();
        callbackPage.sendCallback(formInfo);
        callbackPage.callbackIsSent();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/TestValidData.csv", numLinesToSkip = 1)
    void shouldSendValidFormMinPeriodWithoutPrepayment(String amount) {
        val warrantyProductPage = openProductPage();
        val sliderWidth = warrantyProductPage.getSliderPeriodWarrantyWidth();
        warrantyProductPage.moveSliderPeriodWarranty(-sliderWidth / 2);
        warrantyProductPage.fillAmount(amount);
        val amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        val requestPage = warrantyProductPage.getWarranty();
        val formInfo = getFormInfo();
        requestPage.sendRequest(formInfo);
        requestPage.requestIsSent();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/TestValidData.csv", numLinesToSkip = 1)
    void shouldSendValidFormMinPeriodWithPrepayment(String amount) {
        val warrantyProductPage = openProductPage();
        val sliderWidth = warrantyProductPage.getSliderPeriodWarrantyWidth();
        warrantyProductPage.moveSliderPeriodWarranty(-sliderWidth / 2);
        warrantyProductPage.fillAmount(amount);
        warrantyProductPage.performSwitch();
        val amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        val requestPage = warrantyProductPage.getWarranty();
        val formInfo = getFormInfo();
        requestPage.sendRequest(formInfo);
        requestPage.requestIsSent();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/TestValidData.csv", numLinesToSkip = 1)
    void shouldSendValidFormMaxPeriodWithoutPrepayment(String amount) {
        val warrantyProductPage = openProductPage();
        val sliderWidth = warrantyProductPage.getSliderPeriodWarrantyWidth();
        warrantyProductPage.moveSliderPeriodWarranty(sliderWidth / 2);
        warrantyProductPage.fillAmount(amount);
        val amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        val requestPage = warrantyProductPage.getWarranty();
        val formInfo = getFormInfo();
        requestPage.sendRequest(formInfo);
        requestPage.requestIsSent();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/TestValidData.csv", numLinesToSkip = 1)
    void shouldSendValidFormMaxPeriodWithPrepayment(String amount) {
        val warrantyProductPage = openProductPage();
        val sliderWidth = warrantyProductPage.getSliderPeriodWarrantyWidth();
        warrantyProductPage.moveSliderPeriodWarranty(sliderWidth / 2);
        warrantyProductPage.fillAmount(amount);
        warrantyProductPage.performSwitch();
        val amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        val requestPage = warrantyProductPage.getWarranty();
        val formInfo = getFormInfo();
        requestPage.sendRequest(formInfo);
        requestPage.requestIsSent();
    }
}
