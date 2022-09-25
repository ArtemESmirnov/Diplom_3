package pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

public class PersonalAccountPage {
    @FindBy(how = How.XPATH, using =".//a[text()='Профиль']")
    private SelenideElement profileLink;
    @FindBy(how = How.XPATH, using =".//p[text()='Конструктор']/ancestor::a")
    private SelenideElement constructorLink;
    @FindBy(how = How.XPATH, using ="(.//a[@href='/'])[2]")
    private SelenideElement logoLink;
    @FindBy(how = How.XPATH, using =".//button[text()='Выход']")
    private SelenideElement signoutButton;

    public MainPage constructorLinkGotToMain(){
        constructorLink.click();
        MainPage mainPage = page(MainPage.class);
        mainPage.waitForLoad();
        return mainPage;
    }

    public MainPage logoLinkGotToMain(){
        logoLink.click();
        MainPage mainPage = page(MainPage.class);
        mainPage.waitForLoad();
        return mainPage;
    }

    public LoginPage signout(){
        signoutButton.click();
        LoginPage loginPage = page(LoginPage.class);
        loginPage.waitForLoad();
        return loginPage;
    }

    public void waitForLoad(){
        profileLink.shouldBe(visible);
    }
}
