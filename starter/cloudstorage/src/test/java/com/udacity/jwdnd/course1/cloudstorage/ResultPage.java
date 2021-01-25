package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    WebDriverWait wait;

    @FindBy(css = "a[href='/home']")
    public WebElement hereButton;

    @FindBy(css = "h1")
    public WebElement outcome;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    public void clickHere() {
        wait.until(ExpectedConditions.elementToBeClickable(hereButton)).click();
    }


}

