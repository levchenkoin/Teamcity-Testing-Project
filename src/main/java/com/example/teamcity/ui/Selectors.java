package com.example.teamcity.ui;

import org.openqa.selenium.By;

import com.codeborne.selenide.selector.ByAttribute;

public class Selectors {
    public static ByAttribute byId(String value) {
        return new ByAttribute("id", value);
    }

    public static ByAttribute byType(String value) {
        return new ByAttribute("type", value);
    }

    public static ByAttribute byDataTest(String value) {
        return new ByAttribute("data-test", value);
    }

    public static ByAttribute byClass(String value) {
        return new ByAttribute("class", value);
    }

    public static By byClassStartsWith(String classPrefix) {
    return By.cssSelector("[class^='" + classPrefix + "']");
    }

    public static ByAttribute byRole(String value) { return new ByAttribute("role", value); }
}
