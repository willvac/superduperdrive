package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    WebDriverWait wait;

    @FindBy(css = "div[id='logoutDiv'] form")
    public WebElement logoutButton;

    // Tabs
    @FindBy(id = "nav-files-tab")
    public WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    public WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    public WebElement credentialsTab;

    /*
     ****************************** Elements from Files Tab ******************************
     */
    @FindBy(id = "fileUpload")
    public WebElement chooseFileButton;

    @FindBy(className = "btn btn-dark")
    public WebElement uploadButton;

    @FindAll(@FindBy(css = "table[id='fileTable'] tbody th"))
    public List<WebElement> fileNameList;

    @FindBy(css = "table[id='fileTable'] a[href *= 'file/view']")
    public List<WebElement> fileViewButtons;

    @FindBy(css = "table[id='fileTable'] a[href *= 'file/delete']")
    public List<WebElement> fileDeleteButtons;



    /*
     ****************************** Elements from Notes Tab ******************************
     */

    @FindBy(css = "div[id = 'nav-notes'] button[class='btn btn-info float-right']")
    public WebElement noteAddNewButton;

    @FindAll(@FindBy(css = "table[id = 'userTable'] tbody th"))
    public List<WebElement> noteTitleList;

    @FindAll(@FindBy(css = "table[id = 'userTable'] tbody td:last-child")
    )
    public List<WebElement> noteDescriptionList;


    @FindAll(@FindBy(css = "table[id = 'userTable'] button"))
    public List<WebElement> noteEditButtonList;

    @FindAll(@FindBy(css = "a[href *= 'note/delete'"))
    public List<WebElement> noteDeleteButtonList;

    // WebElements from Note Modal
    @FindBy(id="note-title")
    public WebElement noteModalTitleField;

    @FindBy(id="note-description")
    public WebElement noteModalDescriptionField;

    @FindBy(css = "textarea[id='note-description']")
    public WebElement noteModalSubmitButton;


    /*
     ****************************** Elements from Credentials Tab ******************************
     */
    @FindBy(css = "div[id = 'nav-credentials'] button[class='btn btn-info float-right']")
    private WebElement credentialAddNewButton;

    // Credential Modal
    @FindBy(id="credential-url")
    private WebElement credentialModalUrlField;

    @FindBy(id="credential-username")
    private WebElement credentialModalUsernameField;

    @FindBy(id="credential-password")
    private WebElement credentialModalPasswordField;

    @FindBy(css = "div[id='credentialModal'] form")
    private WebElement credentialModalSubmitButton;


    // Credential Tab
    @FindAll(@FindBy(css = "table[id = 'credentialTable'] button"))
    private List<WebElement> credentialEditButtonList;

    @FindAll(@FindBy(css = "table[id='credentialTable'] tbody th"))
    private List<WebElement> credentialUrlList;


    @FindAll(@FindBy(css = "table[id='credentialTable'] tbody td:nth-last-child(2)"))
    private List<WebElement> credentialUsernameList;


    @FindAll(@FindBy(css = "table[id='credentialTable'] tbody td:last-child"))
    private List<WebElement> credentialPasswordList;

    @FindAll(@FindBy(css = "a[href *= 'credential/delete'"))
    private List<WebElement> credentialDeleteButtonList;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    /*
     ****************************** Methods for Notes  ******************************
     */

    public void clickEditNoteButton(int number) {
        (wait.until(ExpectedConditions.elementToBeClickable(noteEditButtonList.get(number-1)))).click();
    }

    public void clickFilesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(filesTab)).click();
    }

    public void clickNotesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
    }

    public void clickCredentialsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).submit();

    }

    public void clickAddNewNote() {
        wait.until(ExpectedConditions.elementToBeClickable(noteAddNewButton)).click();
    }

    public void inputNoteTitle(String title) {
        noteModalTitleField.sendKeys(title);
    }

    public void inputNoteDescription(String description) {
        noteModalDescriptionField.sendKeys(description);
    }

    public void clickNoteSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(noteModalSubmitButton)).submit();
    }

    public int getNumberOfCredentials() {
        return credentialUsernameList.size();
    }
    public void deleteNote(int number) {
        int count = 1;

        for(WebElement wb : noteDeleteButtonList) {
            if (count == number)
                wb.click();

            count++;
        }
    }

    public String[] getNote(int number) {
        String title = noteTitleList.get(number - 1 ).getText();
        String description = noteDescriptionList.get(number - 1).getText();
        return new String[]{title, description};
    }

    public void getValueNoteTitleField() {
        System.out.println(noteModalTitleField.getAttribute("value"));
    }

    /*
     ****************************** Methods for Credentials  ******************************
     */


    public String[] getCredential(int number) {
        number = number - 1;

        String url = credentialUrlList.get(number).getText();
        String username = credentialUsernameList.get(number).getText();
        String password = credentialPasswordList.get(number).getText();

        return new String[]{url, username, password};
    }


    public void editNote(String title, String description) {
        if (title != "") {
            noteModalTitleField.clear();
            inputNoteTitle(title);
        }

        if (description != "") {
            noteModalDescriptionField.clear();
            inputNoteDescription(description);
        }
    }

    public void clickAddNewCredentialButton() {
        wait.until(ExpectedConditions.elementToBeClickable(credentialAddNewButton)).click();
    }

    public void fillCredentialForm(String url, String username, String password) {
        credentialModalUrlField.sendKeys(url);
        credentialModalUsernameField.sendKeys(username);
        credentialModalPasswordField.sendKeys(password);

    }

    public void submitCredentialForm() {
        wait.until(ExpectedConditions.elementToBeClickable(credentialModalSubmitButton)).submit();

    }

    public String[] getCredFormData() {
        String url = credentialModalUrlField.getAttribute("value");
        String username = credentialModalUsernameField.getAttribute("value");
        String password = credentialModalPasswordField.getAttribute("value");

        return new String[]{url, username, password};
    }

    public void clickCredEditButton(int number) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialEditButtonList.get(number-1))).click();
    }
    public void editCredFormData(String url, String username, String password) {
        if(url != "") {
            credentialModalUrlField.clear();
            credentialModalUrlField.sendKeys(url);
        }

        if(username != "") {
            credentialModalUsernameField.clear();
            credentialModalUsernameField.sendKeys(username);
        }

        if(password != "") {
            credentialModalPasswordField.clear();
            credentialModalPasswordField.sendKeys(password);
        }
    }

    public void clickCredDeleteButton(int number) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialDeleteButtonList.get(number-1))).click();

    }

    public List<String[]> getAllNotes() {
        List<String[]> noteList = new ArrayList<>();
        for(int i = 0; i < noteTitleList.size(); i++) {
            noteList.add(new String[]{noteTitleList.get(i).getText(), noteDescriptionList.get(i).getText()});
        }

        return noteList;
    }

    public List<String> getAllNoteTitles() {
        List<String> noteTitleList = new ArrayList<>();
        for(WebElement webElement: this.noteTitleList)
            noteTitleList.add(webElement.getText());
        return noteTitleList;
    }

    public List<String> getAllNoteDescriptions() {
        List<String> noteDescriptionList = new ArrayList<>();
        for(WebElement webElement: this.noteDescriptionList)
            noteDescriptionList.add(webElement.getText());
        return noteDescriptionList;
    }

    public int getNoteListSize() {
        return getAllNotes().size();
    }
}
