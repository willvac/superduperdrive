package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriverWait wait;

    @FindBy(id = "inputUsername")
    public WebElement usernameField;

    @FindBy(id = "inputPassword")
    public WebElement passwordField;

    @FindBy(id= "submit-button")
    public WebElement submitButton;

    @FindBy(id = "signup-link")
    public WebElement signupButton;

    @FindBy(id="logout-msg")
    public WebElement logoutMessage;

    @FindBy(id="error-msg")
    public WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    public void inputUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void inputPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void login(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickSubmit();
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void clickSignup() {
        wait.until(ExpectedConditions.elementToBeClickable(signupButton)).click();
    }

    public String getLogoutMessage() {
        if(logoutMessage != null)
            return logoutMessage.getText();
        return null;
    }

    public String getErrorMessage() {
        if(errorMessage != null)
            return errorMessage.getText();
        return null;
    }


}