package pageobjects;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginPage {
    @FindBy(how = How.XPATH, using =".//a[text()='Зарегистрироваться']")
    private SelenideElement signupLink;
    @FindBy(how = How.XPATH, using =".//fieldset[1]/div/div/input")
    private SelenideElement emailTextField;
    @FindBy(how = How.XPATH, using =".//fieldset[2]/div/div/input")
    private SelenideElement passwordTextField;
    @FindBy(how = How.XPATH, using =".//button[text()='Войти']")
    private SelenideElement loginButton;
    @FindBy(how = How.XPATH, using =".//a[text()='Восстановить пароль']")
    private SelenideElement restorePasswordLink;
    public SignupPage goToSignupPage(){
        signupLink.click();
        SignupPage signupPage = page(SignupPage.class);
        signupPage.waitForLoad();
        return signupPage;
    }

    public MainPage login(String email, String password) {
        emailTextField.sendKeys(Keys.CONTROL + "A");
        emailTextField.sendKeys(Keys.BACK_SPACE);
        emailTextField.sendKeys(email);
        passwordTextField.sendKeys(Keys.CONTROL + "A");
        passwordTextField.sendKeys(Keys.BACK_SPACE);
        passwordTextField.sendKeys(password);
        sleep(200);
        loginButton.click();
        MainPage mainPage = page(MainPage.class);
        mainPage.waitForLoad();
        return mainPage;
    }

    public RestorePasswordPage goToRestorePassword(){
        restorePasswordLink.click();
        RestorePasswordPage restorePasswordPage = page(RestorePasswordPage.class);
        restorePasswordPage.waitForLoad();
        return restorePasswordPage;
    }
    public void waitForLoad(){
        $(byText("Вы — новый пользователь?")).shouldBe(visible);
    }
}
