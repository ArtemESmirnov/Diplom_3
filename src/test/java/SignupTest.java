import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.SignupPage;
import pojos.EmailPasswordUserBody;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static requestgenerators.DeleteUserRequestGenerator.deleteUserRequest;
import static requestgenerators.LoginUserRequestGenerator.loginUserRequest;

public class SignupTest {
    final static String AUTH_USER_API_PATH = "/api/auth/user";
    final static String AUTH_LOGIN_API_PATH = "/api/auth/login";
    private SignupPage signupPage;
    private final String NAME = "TestName";
    private final String EMAIL = "testemail@gmail.com";
    private String password = "testpassword";

    private void deleteUser(){
        EmailPasswordUserBody loginUserBody = new EmailPasswordUserBody(EMAIL, password);
        String token;

        Response loginResponse = loginUserRequest(loginUserBody, AUTH_LOGIN_API_PATH);
        if(loginResponse.statusCode() == SC_OK) {
            token = loginResponse.path("accessToken")
                    .toString().replaceAll("Bearer ", "");
            assertEquals(SC_ACCEPTED, deleteUserRequest(token, AUTH_USER_API_PATH).statusCode());
        }
    }

    @Before
    public void setUp(){
        //System.setProperty("webdriver.chrome.driver", "F:\\Artem\\WebDriver\\bin\\yandexdriver.exe");

        MainPage mainPage = open("https://stellarburgers.nomoreparties.site/",
                MainPage.class);
        LoginPage loginPage = mainPage.goToPersonalAccountWithoutLogin();
        signupPage = loginPage.goToSignupPage();
    }

    @Test
    public void signUpCorrectShouldBePossibleTest() {
        signupPage.signup(NAME, EMAIL, password);
        signupPage.pressSignupButtonGoToLogin();

        $(byXpath(".//h2[text()='Вход']")).shouldBe(visible);
    }

    @Test
    public void signUpIncorrectPasswordShouldFail(){
        password = "12345";
        signupPage.signup(NAME, EMAIL, password);
        signupPage.pressSignupButton();

        $(byText("Некорректный пароль")).
                shouldBe(visible);
    }

    @After
    public void cleanUp(){
        deleteUser();
    }
}
