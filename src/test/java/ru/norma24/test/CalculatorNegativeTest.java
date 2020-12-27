package ru.norma24.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.norma24.page.ExpressWarrantyPage;
import ru.norma24.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorNegativeTest {

    ExpressWarrantyPage openProductPage() {
        val startPage = open("https://test.norma24.ru/", StartPage.class);
        val warrantyProductPage = startPage.chooseProduct();
        warrantyProductPage.law44Select();
        return warrantyProductPage;
    }

    @Test
    void shouldNotMoveSliderAmountWarrantyBeyondLimits() {
        val warrantyProductPage = openProductPage();
        val sliderWidth = warrantyProductPage.getSliderAmountWarrantyWidth();
        val sliderValue = warrantyProductPage.getSliderAmountWarrantyValue();
        warrantyProductPage.moveSliderAmountWarranty((int) (-sliderWidth / 1.9));
        assertEquals(sliderValue, warrantyProductPage.getSliderAmountWarrantyValue());
        var amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.moveSliderAmountWarranty((int) (sliderWidth / 1.9));
        assertEquals(sliderValue, warrantyProductPage.getSliderAmountWarrantyValue());
        amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.getWarranty();
    }

    @Test
    void shouldNotMoveSliderPeriodWarrantyBeyondLimits() {
        val warrantyProductPage = openProductPage();
        val sliderWidth = warrantyProductPage.getSliderPeriodWarrantyWidth();
        val sliderValue = warrantyProductPage.getSliderPeriodWarrantyValue();
        warrantyProductPage.moveSliderPeriodWarranty((int) (-sliderWidth / 1.9));
        assertEquals(sliderValue, warrantyProductPage.getSliderPeriodWarrantyValue());
        var amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.moveSliderPeriodWarranty((int) (sliderWidth / 1.9));
        assertEquals(sliderValue, warrantyProductPage.getSliderPeriodWarrantyValue());
        amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.getWarranty();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/TestInvalidData.csv", numLinesToSkip = 1)
    void shouldNotEnterInvalidAmountWithoutPrepayment(String amount, int expectedWarrantyValue) {
        val warrantyProductPage = openProductPage();
        warrantyProductPage.fillAmount(amount);
        val sliderValue = warrantyProductPage.getSliderAmountWarrantyValue();
        assertEquals(expectedWarrantyValue, sliderValue);
        val amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.getWarranty();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/TestInvalidData.csv", numLinesToSkip = 1)
    void shouldNotEnterInvalidAmountWithPrepayment(String amount, int expectedWarrantyValue) {
        val warrantyProductPage = openProductPage();
        warrantyProductPage.performSwitch();
        warrantyProductPage.fillAmount(amount);
        val sliderValue = warrantyProductPage.getSliderAmountWarrantyValue();
        assertEquals(expectedWarrantyValue, sliderValue);
        val amountFee = warrantyProductPage.getAmountFee();
        assertTrue(amountFee > 0);
        warrantyProductPage.getWarranty();
    }
}
