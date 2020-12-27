package ru.norma24.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    public static FormInfo getFormInfo() {
        return new FormInfo(getName(), getPhone(), getCompany(), getEmail(), getRegion());
    }

    public static String getName() {
        return faker.name().fullName();
    }

    public static String getPhone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String getCompany() {
        return faker.company().name();
    }

    public static String getEmail() {
        Faker faker = new Faker(new Locale("en"));
        return faker.internet().emailAddress();
    }

    public static SliderInfo getSliderAmountWarrantyInfo() {
        return new SliderInfo(10000, 14000000);
    }

    public static SliderInfo getSliderPeriodWarrantyInfo() {
        return new SliderInfo(10, 1825);
    }

    public static String getRegion() {
        val random = new Random();
        val regions = new String[]{"31", "77", "24", "76"};
        return regions[random.nextInt(regions.length - 1)];
    }

    @Value
    public static class FormInfo {
        String name;
        String phone;
        String company;
        String email;
        String region;
    }

    @Value
    public static class SliderInfo {
        int minValue;
        int maxValue;
    }
}
