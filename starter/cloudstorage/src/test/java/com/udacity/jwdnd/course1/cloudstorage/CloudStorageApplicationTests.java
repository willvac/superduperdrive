package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static ChromeOptions options;
	private WebDriver driver;
	private HomePage homepage;
	private LoginPage loginPage;
	private ResultPage resultPage;
	private SignupPage signupPage;


	@Autowired
	private CredentialService credentialService;

	@Autowired
	private UserService userService;

	@BeforeAll
	static void beforeAll() {
		options = new ChromeOptions();
		options.addArguments("--headless");
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver(options);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	//Verifies that an unauthorized user can only access the login and signup pages
	@Test
	public void verifyUnauthorizedUserPageAccess() {

		// Check pages that require authorization - home and result page
		// Expected to be redirected to login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());

		// Check pages that do not require authorization - login and signup page
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	// Signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible
	@Test
	public void verifyLoginAndLogout() throws InterruptedException {
		// helper method will create user, login, and get homepage
		createUserAndLoginHelper(0);

		// check that we are able to get the homepage
		Assertions.assertEquals("Home", driver.getTitle()); // check that the title of the page is Home

		// logout
		homepage = new HomePage(driver);
		homepage.clickLogout();
		Thread.sleep(1000);

		// check that we are redirected to homepage
		// todo: check that we get the logout message instead;
		Assertions.assertEquals("Login", driver.getTitle());

		// attempt to get the home page
		// expected to be redirected to login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle()); // check that the title of the page is Login
	}

	// creates a note, and verifies it is displayed
	@Test
	public void createAndVerifyNote() throws InterruptedException {
		// call helper to sign up, login, and get home
		createUserAndLoginHelper(1);

		// call helper to create a note
		String[] note = createNoteHelper();

		// get note
		Thread.sleep(2000);
		homepage.clickNotesTab();

		Thread.sleep(1000);

		// assumes we have added one note and this note is at position 1
		String[] displayedNote = homepage.getNote(1);

		// check that the note added is the same as note displayed
		Assertions.assertEquals(note[0], displayedNote[0]); // title
		Assertions.assertEquals(note[1], displayedNote[1]); // description
	}
	// edits an existing note and verifies that the changes are displayed
	@Test
	public void verifyNoteEdit() throws InterruptedException {
		// call helper to sign up, login, and get home
		createUserAndLoginHelper(2);

		// call helper to create a note
		String[] note = createNoteHelper();

		// get note
		Thread.sleep(2000);
		homepage.clickNotesTab();

		// assume we have 1 note and that is at position 1
		Thread.sleep(1000);
		homepage.clickEditNoteButton(1);
		Thread.sleep(1000);
		homepage.editNote("Edited Title", "Edited Description");
		homepage.clickNoteSubmit();
		Thread.sleep(1000);


		resultPage = new ResultPage(driver);
		resultPage.clickHere();

		Thread.sleep(2000);
		homepage.clickNotesTab();
		Thread.sleep(1000);

		// check that the displayed notes are the edited notes
		// check that changes were actually made by confirming that the edit note != original note

		String[] displayedNote = homepage.getNote(1);
		Assertions.assertNotEquals(note[0], displayedNote[0]); // not original title
		Assertions.assertNotEquals(note[1], displayedNote[1]); // not original description
		Assertions.assertEquals("Edited Title", displayedNote[0]); // edited title
		Assertions.assertEquals("Edited Description", displayedNote[1]); // edited description

	}
	// deletes a note and verifies that the note is no longer displayed
	@Test
	public void verifyNoteDelete() throws InterruptedException {
		// call helper to sign up, login, and get home
		createUserAndLoginHelper(3);

		// call helper to create a note
		String[] note = createNoteHelper();

		// get note
		Thread.sleep(2000);
		homepage.clickNotesTab();

		int originalNumberOfNotes = homepage.getNoteListSize();

		// assume we have 1 note and that it is at position 1
		Thread.sleep(1000);
		homepage.deleteNote(1);
		int newNoteCount = homepage.getNoteListSize();

		Thread.sleep(1000);
		resultPage = new ResultPage(driver);
		resultPage.clickHere();

		Thread.sleep(2000);
		homepage.clickNotesTab();

		Thread.sleep(1000);

		// Gets all of the note tiles and description
		List<String> noteTitleList = homepage.getAllNoteTitles();
		List<String> noteDescriptionList = homepage.getAllNoteDescriptions();

		// Check that the list of note title does not contain our note title
		Assertions.assertEquals(false, noteTitleList.contains(note[0]));
		// Check that the list of note description does not contain our description
		Assertions.assertEquals(false, noteDescriptionList.contains(note[1]));
		// Check that we have 1 fewer note
		Assertions.assertEquals(originalNumberOfNotes - 1,newNoteCount);
	}

	// creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted
	@Test
	public void verifyCredentialCreateAndEncryption() throws InterruptedException {
		// call helper to sign up, login, and get home
		String[] loginCredential = createUserAndLoginHelper(4);

		// call helper to create and store credential 1, 2, and 3
		String[] actualCred1 = createCredentialHelper(1);
		Thread.sleep(1000);
		String[] actualCred2 = createCredentialHelper(2);
		Thread.sleep(1000);
		String[] actualCred3 = createCredentialHelper(3);

		Thread.sleep(1000);

		homepage.clickCredentialsTab();

		int userId = userService.getUser(loginCredential[2]).getUserId();

		// check credential 1
		Thread.sleep(2000);
		String[] displayedCred1 = homepage.getCredential(1);
		String encryptedPassword = credentialService.getAllCredentials(userId).get(0).getPassword();
		Assertions.assertEquals(actualCred1[0], displayedCred1[0]); // check URL
		Assertions.assertEquals(actualCred1[1], displayedCred1[1]); // check username
		Assertions.assertEquals(encryptedPassword, displayedCred1[2]); // check that the displayed password = encrypted password
		Assertions.assertNotEquals(actualCred1[2], displayedCred1[2]); // check that the displayed password != actual password

		// check credential 2
		String[] displayedCred2 = homepage.getCredential(2);
		encryptedPassword = credentialService.getAllCredentials(userId).get(1).getPassword();
		Assertions.assertEquals(actualCred2[0], displayedCred2[0]); // check URL
		Assertions.assertEquals(actualCred2[1], displayedCred2[1]); // check username
		Assertions.assertEquals(encryptedPassword, displayedCred2[2]); // check that the displayed password = encrypted password
		Assertions.assertNotEquals(actualCred2[2], displayedCred2[2]); // check that the displayed password != actual password

		// check credential 3;
		String[] displayedCred3 = homepage.getCredential(3);
		encryptedPassword = credentialService.getAllCredentials(userId).get(2).getPassword();
		Assertions.assertEquals(actualCred3[0], displayedCred3[0]); // check URL
		Assertions.assertEquals(actualCred3[1], displayedCred3[1]); // check username
		Assertions.assertEquals(encryptedPassword, displayedCred3[2]); // check that the displayed password = encrypted password
		Assertions.assertNotEquals(actualCred3[2], displayedCred3[2]); // check that the displayed password != actual password
	}
	// views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed
	@Test
	public void verifyCredentialEditAndDecryption() throws InterruptedException {

		// call helper to sign up, login, and get home
		createUserAndLoginHelper(5);

		Thread.sleep(1000);

		// call helper to create and store credential 1, 2, and 3
		String[] actualCred1 = createCredentialHelper(1);
		Thread.sleep(1000);
		String[] actualCred2 = createCredentialHelper(2);
		Thread.sleep(1000);
		String[] actualCred3 = createCredentialHelper(3);

		Thread.sleep(1000);
		homepage.clickCredentialsTab();
		Thread.sleep(2000);

		//check and edit credential 1
		homepage.clickCredEditButton(1);
		Thread.sleep(1000);
		String[] decryptedCred1 = homepage.getCredFormData();
		Assertions.assertEquals(actualCred1[0], decryptedCred1[0]); //check url
		Assertions.assertEquals(actualCred1[1], decryptedCred1[1]); //check username
		Assertions.assertEquals(actualCred1[2], decryptedCred1[2]); //check password (decrypted)

		String[] actualEditedCred1 = editCredentialHelper(1); //edit the credential
		Thread.sleep(1000);

		//check edited credential 1
		homepage.clickCredEditButton(1);
		Thread.sleep(2000);
		String[] editedDecryptedCred1 = homepage.getCredFormData();
		Assertions.assertEquals(actualEditedCred1[0], editedDecryptedCred1[0]); //check edited url
		Assertions.assertEquals(actualEditedCred1[1], editedDecryptedCred1[1]); //check edited username
		Assertions.assertEquals(actualEditedCred1[2], editedDecryptedCred1[2]); //check edited password

		//check and edit credential 2
		driver.get("http://localhost:" + this.port + "/home");
		homepage.clickCredentialsTab();
		Thread.sleep(1000);
		homepage.clickCredEditButton(2);
		Thread.sleep(1000);
		String[] decryptedCred2 = homepage.getCredFormData();
		Assertions.assertEquals(actualCred2[0], decryptedCred2[0]); // check url
		Assertions.assertEquals(actualCred2[1], decryptedCred2[1]); // check username
		Assertions.assertEquals(actualCred2[2], decryptedCred2[2]); // check password
		String[] actualEditedCred2 = editCredentialHelper(2);
		Thread.sleep(1000);

		//check edited credential 2
		homepage.clickCredEditButton(2);
		Thread.sleep(2000);
		String[] editedDecryptedCred2 = homepage.getCredFormData();
		Assertions.assertEquals(actualEditedCred2[0], editedDecryptedCred2[0]); //check edited URL
		Assertions.assertEquals(actualEditedCred2[1], editedDecryptedCred2[1]); // check edited username
		Assertions.assertEquals(actualEditedCred2[2], editedDecryptedCred2[2]); // check edited password

		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1000);
		homepage.clickCredentialsTab();

		//check and edit credential 3
		Thread.sleep(1000);
		homepage.clickCredEditButton(3);
		Thread.sleep(1000);
		String[] decryptedCred3 = homepage.getCredFormData();
		Assertions.assertEquals(actualCred3[0], decryptedCred3[0]); // check url
		Assertions.assertEquals(actualCred3[1], decryptedCred3[1]); // check username
		Assertions.assertEquals(actualCred3[2], decryptedCred3[2]); // check password
		String[] actualEditedCred3 = editCredentialHelper(3);
		Thread.sleep(1000);

		//check edited credential 3
		homepage.clickCredEditButton(3);
		Thread.sleep(2000);
		String[] editedDecryptedCred3 = homepage.getCredFormData();
		Assertions.assertEquals(actualEditedCred3[0], editedDecryptedCred3[0]); // check edited url
		Assertions.assertEquals(actualEditedCred3[1], editedDecryptedCred3[1]); // check edited username
		Assertions.assertEquals(actualEditedCred3[2], editedDecryptedCred3[2]); // check edited password
	}

	// deletes an existing set of credentials and verifies that the credentials are no longer displayed
	@Test
	public void verifyCredentialDelete() throws InterruptedException {
		// call helper to sign up, login, and get home
		createUserAndLoginHelper(6);

		// call helper to create and store credential 1, 2, and 3
		String[] actualCred1 = createCredentialHelper(1);
		Thread.sleep(1000);
		String[] actualCred2 = createCredentialHelper(2);
		Thread.sleep(1000);
		String[] actualCred3 = createCredentialHelper(3);

		Thread.sleep(1000);
		homepage.clickCredentialsTab();

		// Delete credential number 3 and Assert that we have 1 fewer credential
		Thread.sleep(1000);
		int originalNumberOfCredentials = homepage.getNumberOfCredentials();
		homepage.clickCredDeleteButton(3);
		Thread.sleep(1000);
		resultPage = new ResultPage(driver);
		resultPage.clickHere();
		Thread.sleep(1000);
		homepage.clickCredentialsTab();
		Thread.sleep(1000);
		int currentNumberOfCredentials = homepage.getNumberOfCredentials();
		Assertions.assertEquals(originalNumberOfCredentials-1, currentNumberOfCredentials);

		// Delete credential number 2 and Assert that we have 1 fewer credential
		homepage.clickCredDeleteButton(2);
		Thread.sleep(1000);
		resultPage = new ResultPage(driver);
		resultPage.clickHere();
		Thread.sleep(1000);
		homepage.clickCredentialsTab();
		Thread.sleep(1000);
		currentNumberOfCredentials = homepage.getNumberOfCredentials();
		Assertions.assertEquals(originalNumberOfCredentials-2, currentNumberOfCredentials);

		// Delete credential number 1 and Assert that we have 1 fewer credential
		homepage.clickCredDeleteButton(1);
		Thread.sleep(1000);
		resultPage = new ResultPage(driver);
		resultPage.clickHere();
		Thread.sleep(1000);
		currentNumberOfCredentials = homepage.getNumberOfCredentials();
		Assertions.assertEquals(originalNumberOfCredentials-3, currentNumberOfCredentials);


	}

	// helper methods below

	//helper method to create a user, login, and get home page
	// returns String[] containing the credentials {firstName, lastName, username, password}
	public String[] createUserAndLoginHelper(int number) throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/signup");
		signupPage = new SignupPage(driver);

		// username and password of user created
		String username = "username" + number;
		String password = "password" + number;
		String firstName = "firstName" + number;
		String lastName = "lastName" + number;

		signupPage.signup(firstName, lastName, username, password);
		Thread.sleep(1000);

		signupPage.clickBackToLogin();
		Thread.sleep(1000);

		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		return new String[] {firstName, lastName, username, password};
	}

	public String[] createNoteHelper() throws InterruptedException {
		String title = "descriptive title";
		String description = "descriptive description";

		Thread.sleep(1000);

		homepage = new HomePage(driver);
		homepage.clickNotesTab();

		Thread.sleep(1000);
		homepage.clickAddNewNote();

		Thread.sleep(1000);
		homepage.inputNoteTitle(title);
		homepage.inputNoteDescription(description);
		homepage.clickNoteSubmit();

		Thread.sleep(1000);
		resultPage = new ResultPage(driver);
		resultPage.clickHere();

		return new String[]{title, description};
	}

	// helper method to create credentials
	public String[] createCredentialHelper(int exampleNumber) throws InterruptedException {
		String url;
		String username;
		String password;

		switch(exampleNumber) {
			case 1:
				url = "www.google.com";
				username = "johndoe123";
				password = "applejuice123";
				break;
			case 2:
				url = "www.yahoo.com";
				username = "yahooliganjohn";
				password = "password2";
				break;
			case 3:
				url = "www.udacity.com";
				username = "john@gmail.com";
				password = "udacity123";
				break;
			default:
				url = "www.google.com" + exampleNumber;
				username = "johndoe" + exampleNumber;
				password = "password" + exampleNumber;

		}

		Thread.sleep(1000);
		homepage = new HomePage(driver);
		homepage.clickCredentialsTab();

		Thread.sleep(1000);
		homepage.clickAddNewCredentialButton();

		Thread.sleep(1000);
		homepage.fillCredentialForm(url, username, password);
		homepage.submitCredentialForm();

		Thread.sleep(1000);
		resultPage = new ResultPage(driver);
		resultPage.clickHere();

		return new String[]{url, username, password};
	}

	// helper methods to edit credentials
	public String[] editCredentialHelper(int number) throws InterruptedException {
		String url = "editUrl" + number;
		String username = "editUser" + number;
		String password = "editPassword" + number;
		homepage.editCredFormData(url, username, password);
		homepage.submitCredentialForm();

		Thread.sleep(1000);
		resultPage = new ResultPage(driver);
		resultPage.clickHere();

		Thread.sleep(2000);
		homepage.clickCredentialsTab();

		return new String[]{url, username, password};
	}


}
