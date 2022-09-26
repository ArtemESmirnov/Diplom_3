package pageobjects;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
public class RestorePasswordPage {
    @FindBy(how = How.XPATH, using =".//a[text()='Войти']")
    private SelenideElement loginLink;

    public LoginPage pressLinkGoToLogin(){
        loginLink.click();
        LoginPage loginPage = page(LoginPage.class);
        loginPage.waitForLoad();
        return loginPage;
    }

    public void waitForLoad(){
        $(byXpath(".//h2[text()='Восстановление пароля']")).shouldBe(visible);
    }
}
