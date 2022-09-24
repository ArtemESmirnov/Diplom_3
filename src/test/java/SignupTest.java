import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    final static String authUserApiPath = "/api/auth/user";
    final static String authLoginApiPath = "/api/auth/login";
    private SignupPage signupPage;
    private LoginPage loginPage;
    private final String name = "TestName";
    private final String email = "testemail@gmail.com";
    private final String password = "testpassword";

    private void deleteUser(){
        EmailPasswordUserBody loginUserBody = new EmailPasswordUserBody(email, password);
        String token;

        Response loginResponse = loginUserRequest(loginUserBody, authLoginApiPath);
        if(loginResponse.statusCode() == SC_OK) {
            token = loginResponse.path("accessToken")
                    .toString().replaceAll("Bearer ", "");
            assertEquals(SC_ACCEPTED, deleteUserRequest(token, authUserApiPath).statusCode());
        }
    }

    @Before
    public void setUp(){
        //System.setProperty("webdriver.chrome.driver", "F:\\Artem\\WebDriver\\bin\\yandexdriver.exe");

        MainPage mainPage = open("https://stellarburgers.nomoreparties.site/",
                MainPage.class);
        loginPage = mainPage.goToPersonalAccount();
        signupPage = loginPage.goToSignupPage();
    }

    @Test
    public void signUpCorrectShouldBePossibleTest() {
        signupPage.signup(name, email, password);
        loginPage = signupPage.pressSignupButtonGoToLogin();
        loginPage.login(email, password);

        $(byText("Соберите бургер")).
                shouldBe(visible);
    }

    @Test
    public void signUpIncorrectPasswordShouldFail(){
        signupPage.signup(name, email, "12345");
        signupPage.pressSignupButton();
        $(byText("Некорректный пароль")).
                shouldBe(visible);
    }

    @After
    public void cleanUp(){
        try {
            deleteUser();
        }
        catch (Exception e){}
    }
}
