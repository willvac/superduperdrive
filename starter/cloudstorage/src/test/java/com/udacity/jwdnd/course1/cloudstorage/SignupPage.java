package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
    WebDriverWait wait;

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    public WebElement signupButton;

    @FindBy(css = "a[href='/login']")
    public WebElement backToLoginButton;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    public void inputFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    public void inputLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    public void inputUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void inputPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickSignup() {
        wait.until(ExpectedConditions.elementToBeClickable(signupButton)).submit();
    }

    public void clickBackToLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(backToLoginButton)).click();
    }

    public void signup(String firstName, String lastName, String username, String password) {
        inputFirstName(firstName);
        inputLastName(lastName);
        inputUsername(username);
        inputPassword(password);
        clickSignup();
    }

    public String getSuccessMessage() {
        if(successMessage != null) {
            return successMessage.getText();
        }
        return null;
    }

    public String getErrorMessage() {
        if(errorMessage != null) {
            return errorMessage.getText();
        }
        return null;
    }



}