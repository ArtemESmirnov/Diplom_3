package pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.*;

public class SignupPage {
    @FindBy(how = How.XPATH, using =".//label[text()='Имя']/following-sibling::input")
    private SelenideElement nameTextField;
    @FindBy(how = How.XPATH, using =".//label[text()='Email']/following-sibling::input")
    private SelenideElement emailTextField;
    @FindBy(how = How.XPATH, using =".//label[text()='Пароль']/following-sibling::input")
    private SelenideElement passwordTextField;
    @FindBy(how = How.XPATH, using =".//button[text()='Зарегистрироваться']")
    private SelenideElement signupButton;
    @FindBy(how = How.XPATH, using =".//a[text()='Войти']")
    private SelenideElement loginLink;
    public void waitForLoad(){
        signupButton.shouldBe(enabled);
    }

    public void signup(String name, String email, String password){
        nameTextField.sendKeys(name);
        emailTextField.sendKeys(email);
        passwordTextField.sendKeys(password);
    }

    public void pressSignupButtonGoToLogin(){
        signupButton.shouldBe(enabled).click();
        LoginPage loginPage = page(LoginPage.class);
        loginPage.waitForLoad();
    }

    public LoginPage pressLinkGoToLogin(){
        loginLink.click();
        LoginPage loginPage = page(LoginPage.class);
        loginPage.waitForLoad();
        return loginPage;
    }

    public void pressSignupButton(){
        signupButton.shouldBe(enabled).click();
    }
}
