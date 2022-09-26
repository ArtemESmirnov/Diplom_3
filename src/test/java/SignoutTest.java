import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobjects.*;
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

public class SignoutTest {
    final static String AUTH_USER_API_PATH = "/api/auth/user";
    final static String AUTH_LOGIN_API_PATH = "/api/auth/login";
    final static String AUTH_REGISTER_API_PATH = "/api/auth/register";
    private PersonalAccountPage personalAccountPage;
    private final String NAME = "TestName";
    private final String EMAIL = "testemail@gmail.com";
    private final String PASSWORD = "testpassword";

    private void deleteUser(){
        EmailPasswordUserBody loginUserBody = new EmailPasswordUserBody(EMAIL, PASSWORD);
        String token;

        Response loginResponse = loginUserRequest(loginUserBody, AUTH_LOGIN_API_PATH);
        if(loginResponse.statusCode() == SC_OK) {
            token = loginResponse.path("accessToken")
                    .toString().replaceAll("Bearer ", "");
            assertEquals(SC_ACCEPTED, deleteUserRequest(token, AUTH_USER_API_PATH).statusCode());
        }
    }

    private void createUser(){
        WholeUserBody user = new WholeUserBody(EMAIL, PASSWORD, NAME);

        createUserRequest(user, AUTH_REGISTER_API_PATH);
    }

    @Before
    public void setUp(){
        //System.setProperty("webdriver.chrome.driver", "F:\\Artem\\WebDriver\\bin\\yandexdriver.exe");

        MainPage mainPage = open("https://stellarburgers.nomoreparties.site/",
                MainPage.class);
        LoginPage loginPage = mainPage.goToPersonalAccountWithoutLogin();
        SignupPage signupPage = loginPage.goToSignupPage();
        createUser();
        signupPage.signup(NAME, EMAIL, PASSWORD);
        loginPage = signupPage.pressLinkGoToLogin();
        mainPage = loginPage.login(EMAIL, PASSWORD);
        personalAccountPage = mainPage.goToPersonalAccount();
    }

    @Test
    public void signoutShouldBePossible(){
        personalAccountPage.signout();
        $(byXpath(".//h2[text()='Вход']")).shouldBe(visible);
    }

    @After
    public void cleanUp(){
        deleteUser();
    }
}
