package tests.web;

import data.PagesLinks;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.TRIVIAL;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("DenisQA")
@Epic(value = "DemoQA")
@Feature(value = "All Pages")
@Story(value = "Page titles and errors in console log")
@Tag("ui")
class UniversalTests extends TestBase {

    @Severity(TRIVIAL)
    @DisplayName("Page should have title text")
    @EnumSource(PagesLinks.class)
    @ParameterizedTest(name = "[{index}] {0}")
    void pageShouldHaveTitle(PagesLinks link) {
        step("Open url", () ->
                open(link.getLink()));

        step("Page title should have text 'DEMOQA'", () -> {
            String expectedTitle = "DEMOQA";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }
}
