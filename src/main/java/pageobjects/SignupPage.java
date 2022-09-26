package pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class SignupPage {
    @FindBy(how = How.XPATH, using =".//fieldset[1]/div/div/input")
    private SelenideElement nameTextField;
    @FindBy(how = How.XPATH, using =".//fieldset[2]/div/div/input")
    private SelenideElement emailTextField;
    @FindBy(how = How.XPATH, using =".//fieldset[3]/div/div/input")
    private SelenideElement passwordTextField;
    @FindBy(how = How.XPATH, using =".//button[text()='Зарегистрироваться']")
    private SelenideElement signupButton;
    @FindBy(how = How.XPATH, using =".//a[text()='Войти']")
    private SelenideElement loginLink;
    public void waitForLoad(){
        $(byXpath(".//h2[text()='Регистрация']")).shouldBe(visible);
    }

    public void signup(String name, String email, String password){
        nameTextField.sendKeys(Keys.CONTROL + "A");
        nameTextField.sendKeys(Keys.BACK_SPACE);
        nameTextField.sendKeys(name);
        emailTextField.sendKeys(Keys.CONTROL + "A");
        emailTextField.sendKeys(Keys.BACK_SPACE);
        emailTextField.sendKeys(email);
        passwordTextField.sendKeys(Keys.CONTROL + "A");
        passwordTextField.sendKeys(Keys.BACK_SPACE);
        passwordTextField.sendKeys(password);
    }

    public void pressSignupButtonGoToLogin(){
        signupButton.click();
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
        signupButton.click();
    }
}
