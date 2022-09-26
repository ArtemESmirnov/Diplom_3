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
    final static String authUserApiPath = "/api/auth/user";
    final static String authLoginApiPath = "/api/auth/login";
    final static String authRegisterApiPath = "/api/auth/register";
    private PersonalAccountPage personalAccountPage;
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

    private void createUser(){
        WholeUserBody user = new WholeUserBody(email, password, name);

        createUserRequest(user, authRegisterApiPath);
    }

    @Before
    public void setUp(){
        //System.setProperty("webdriver.chrome.driver", "F:\\Artem\\WebDriver\\bin\\yandexdriver.exe");

        MainPage mainPage = open("https://stellarburgers.nomoreparties.site/",
                MainPage.class);
        LoginPage loginPage = mainPage.goToPersonalAccountWithoutLogin();
        SignupPage signupPage = loginPage.goToSignupPage();
        createUser();
        signupPage.signup(name, email, password);
        loginPage = signupPage.pressLinkGoToLogin();
        mainPage = loginPage.login(email, password);
        personalAccountPage = mainPage.goToPersonalAccount();
    }

    @Test
    public void signoutShouldBePossible(){
        personalAccountPage.signout();
        $(byText("Вы — новый пользователь?")).shouldBe(visible);
    }

    @After
    public void cleanUp(){
        deleteUser();
    }
}
