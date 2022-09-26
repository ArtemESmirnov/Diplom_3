package pageobjects;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginPage {
    @FindBy(how = How.XPATH, using =".//a[text()='Зарегистрироваться']")
    private SelenideElement signupLink;
    @FindBy(how = How.XPATH, using =".//label[text()='Email']/following-sibling::input")
    private SelenideElement emailTextField;
    @FindBy(how = How.XPATH, using =".//label[text()='Пароль']/following-sibling::input")
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
        emailTextField.sendKeys(email);
        passwordTextField.sendKeys(password);
        loginButton.shouldBe(enabled).click();
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
        loginButton.shouldBe(enabled);
    }
}
