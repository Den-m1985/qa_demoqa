package tests.web;

import data.RegistrationData;
import data.api.AuthorizationApi;
import data.models.CookieDto;
import data.models.CredentialsDto;
import data.pages.ProfilePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static config.user.UserProperties.PASSWORD;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.BLOCKER;

@Owner("DenisQA")
@Epic(value = "Book Store Application")
@Feature(value = "Profile Page")
@Story(value = "Authorization")
@Tag("ui")
class AuthorizationTests extends TestBase{
    private final AuthorizationApi authorizationApi = new AuthorizationApi();
    private final ProfilePage profilePage = new ProfilePage();
    private final RegistrationData data = new RegistrationData();

    @Severity(BLOCKER)
    @DisplayName("User should be able to authorize")
    @Test
    void authorizationTest() {
        CredentialsDto credentials = new CredentialsDto(data.firstName + data.lastName, PASSWORD);
        authorizationApi.createUser(credentials);
        authorizationApi.getToken(credentials);
        CookieDto cookie = authorizationApi.login(credentials);

        step("Clear old cookies if exist", () -> {
            open("/favicon.png");
            getWebDriver().manage().deleteAllCookies();
        });

        step("Open profile page", profilePage::openPage);
        step("Check user is not logged in", profilePage::checkLoginSuggestionIsVisible);

        step("Set cookies and refresh page", () -> {
            getWebDriver().manage().addCookie(new org.openqa.selenium.Cookie("token", cookie.token()));
            getWebDriver().manage().addCookie(new org.openqa.selenium.Cookie("expires", cookie.expires()));
            getWebDriver().manage().addCookie(new org.openqa.selenium.Cookie("userID", cookie.userId()));
            getWebDriver().manage().addCookie(new org.openqa.selenium.Cookie("userName", cookie.username()));
            refresh();
        });

        step("Check user is logged in by logout button visibility", profilePage::checkLogoutButtonIsVisible);
    }
}
