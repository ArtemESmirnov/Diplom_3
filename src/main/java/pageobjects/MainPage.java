package pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    @FindBy(how = How.XPATH, using =".//a[@href = '/account']")
    private SelenideElement personalAccountLink;

    @FindBy(how = How.XPATH, using =".//button[text() = 'Войти в аккаунт']")
    private SelenideElement signInButton;

    public SelenideElement getBunsTab() {
        return bunsTab;
    }

    public SelenideElement getSaucesTab() {
        return saucesTab;
    }

    public SelenideElement getFillingsTab() {
        return fillingsTab;
    }

    @FindBy(how = How.XPATH, using =".//span[text()='Булки']/parent::div")
    private SelenideElement bunsTab;
    @FindBy(how = How.XPATH, using =".//span[text()='Соусы']/parent::div")
    private SelenideElement saucesTab;
    @FindBy(how = How.XPATH, using =".//span[text()='Начинки']/parent::div")
    private SelenideElement fillingsTab;

    public LoginPage goToPersonalAccountWithoutLogin(){
        personalAccountLink.click();
        LoginPage loginPage = page(LoginPage.class);
        loginPage.waitForLoad();
        return loginPage;
    }

    public PersonalAccountPage goToPersonalAccount(){
        personalAccountLink.click();
        PersonalAccountPage personalAccountPage = page(PersonalAccountPage.class);
        personalAccountPage.waitForLoad();
        return personalAccountPage;
    }

    public LoginPage goToSignInForm(){
        signInButton.click();
        LoginPage loginPage = page(LoginPage.class);
        loginPage.waitForLoad();
        return loginPage;
    }

    public void clickOnBuns(){
        bunsTab.click();
    }

    public void clickOnSauces(){
        saucesTab.click();
    }

    public void clickOnFillings(){
        fillingsTab.click();
    }

    public boolean checkIfTabIsActive(SelenideElement tab){
        String classValue = tab.getAttribute("class");
        return classValue.contains("current");
    }

    public void waitForLoad(){
        $(byText("Соберите бургер")).shouldBe(visible);
    }
}
