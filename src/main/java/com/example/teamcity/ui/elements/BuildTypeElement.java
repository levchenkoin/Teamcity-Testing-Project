package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class BuildTypeElement extends PageElement {
    private final SelenideElement header;

    public BuildTypeElement(SelenideElement element) {
        super(element);

        this.header = findElement(Selectors.byRole("heading"));
    }
}
