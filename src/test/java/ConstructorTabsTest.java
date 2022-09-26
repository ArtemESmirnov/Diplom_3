import org.junit.Before;
import org.junit.Test;
import pageobjects.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertTrue;

public class ConstructorTabsTest {
    private MainPage mainPage;

    @Before
    public void setUp(){
        //System.setProperty("webdriver.chrome.driver", "F:\\Artem\\WebDriver\\bin\\yandexdriver.exe");

        mainPage = open("https://stellarburgers.nomoreparties.site/",
                MainPage.class);
    }

    @Test
    public void bunsTabAfterClickShouldBeActive(){
        if(mainPage.checkIfTabIsActive(mainPage.getBunsTab()))
            mainPage.clickOnFillings();
        mainPage.clickOnBuns();
        assertTrue(mainPage.checkIfTabIsActive(mainPage.getBunsTab()));
    }

    @Test
    public void saucesTabAfterClickShouldBeActive(){
        if(mainPage.checkIfTabIsActive(mainPage.getSaucesTab()))
            mainPage.clickOnFillings();
        mainPage.clickOnSauces();
        assertTrue(mainPage.checkIfTabIsActive(mainPage.getSaucesTab()));
    }

    @Test
    public void fillingsTabAfterClickShouldBeActive(){
        if(mainPage.checkIfTabIsActive(mainPage.getFillingsTab()))
            mainPage.clickOnBuns();
        mainPage.clickOnFillings();
        assertTrue(mainPage.checkIfTabIsActive(mainPage.getFillingsTab()));
    }
}
