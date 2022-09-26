import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.RestorePasswordPage;
import pageobjects.SignupPage;
import pojos.EmailPasswordUserBody;
import pojos.WholeUserBody;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static requestgenerators.CreateUserRequestGenerator.createUserRequest;
import static requestgenerators.DeleteUserRequestGenerator.deleteUserRequest;
import static requestgenerators.LoginUserRequestGenerator.loginUserRequest;

public class LoginTest {
    final static String AUTH_USER_API_PATH = "/api/auth/user";
    final static String AUTH_LOGIN_API_PATH = "/api/auth/login";
    final static String AUTH_REGISTER_API_PATH = "/api/auth/register";
    private LoginPage loginPage;
    private MainPage mainPage;
    private final String email = "testemail@gmail.com";
    private final String password = "testpassword";

    private void deleteUser(){
        EmailPasswordUserBody loginUserBody = new EmailPasswordUserBody(email, password);
        String token;

        Response loginResponse = loginUserRequest(loginUserBody, AUTH_LOGIN_API_PATH);
        if(loginResponse.statusCode() == SC_OK) {
            token = loginResponse.path("accessToken")
                    .toString().replaceAll("Bearer ", "");
            assertEquals(SC_ACCEPTED, deleteUserRequest(token, AUTH_USER_API_PATH).statusCode());
        }
    }

    private void createUser(){
        String name = "TestName";
        WholeUserBody user = new WholeUserBody(email, password, name);

        createUserRequest(user, AUTH_REGISTER_API_PATH);
    }

    @Before
    public void setUp(){
        //System.setProperty("webdriver.chrome.driver", "F:\\Artem\\WebDriver\\bin\\yandexdriver.exe");

        mainPage = open("https://stellarburgers.nomoreparties.site/",
                MainPage.class);
        createUser();
    }

    @Test
    public void loginButtonMainPageLoginShouldBePossible(){
        loginPage = mainPage.goToSignInForm();
        mainPage = loginPage.login(email, password);
        $(byXpath(".//h1[text()='Соберите бургер']")).shouldBe(visible);
    }

    @Test
    public void loginButtonPersonalAccountLoginShouldBePossible(){
        loginPage = mainPage.goToPersonalAccountWithoutLogin();
        mainPage = loginPage.login(email, password);
        $(byXpath(".//h1[text()='Соберите бургер']")).shouldBe(visible);
    }

    @Test
    public void loginLinkSignupFormLoginShouldBePossible(){
        loginPage = mainPage.goToPersonalAccountWithoutLogin();
        SignupPage signupPage = loginPage.goToSignupPage();
        loginPage = signupPage.pressLinkGoToLogin();
        mainPage = loginPage.login(email, password);
        $(byXpath(".//h1[text()='Соберите бургер']")).shouldBe(visible);
    }

    @Test
    public void loginLinkRestorePasswordLoginShouldBePossible(){
        loginPage = mainPage.goToPersonalAccountWithoutLogin();
        RestorePasswordPage restorePasswordPage = loginPage.goToRestorePassword();
        loginPage = restorePasswordPage.pressLinkGoToLogin();
        mainPage = loginPage.login(email, password);
        $(byXpath(".//h1[text()='Соберите бургер']")).shouldBe(visible);
    }

    @After
    public void cleanUp(){
        deleteUser();
    }
}
