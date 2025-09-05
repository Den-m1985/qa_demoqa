package tests.web;

import data.RegistrationData;
import data.pages.RegistrationFormPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static data.RegistrationData.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Owner("DenisQA")
@Epic(value = "DemoQA")
@Feature(value = "Practise Form")
@Story(value = "Fill out a form")
@Tag("ui")
class RegistrationFormTests extends TestBase{

    private final RegistrationData data = new RegistrationData();
    private final RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    @Severity(NORMAL)
    @DisplayName("Fill form with maximum data")
    @Description("Image upload may not work on selenoid Firefox.\n" +
            "InvalidArgumentException: File not found: null")
    @Flaky
    @Test
    void fillFormWithMaximumDataTest() {
        step("Open registration form", registrationFormPage::openPage);

        step("Fill registration form", () -> {
            registrationFormPage.setFirstName(data.firstName)
                    .setLastName(data.lastName)
                    .setEmail(data.email)
                    .setGender(data.gender)
                    .setMobilePhone(data.mobilePhone)
                    .setDateOfBirth(data.dayOfBirth, data.monthOfBirth, data.yearOfBirth)
                    .setSubjects(SUBJECT)
                    .setHobby(FIRST_HOBBY)
                    .setHobby(SECOND_HOBBY)
                    .setHobby(THIRD_HOBBY)
                    .setAddress(data.address)
                    .setState(STATE)
                    .setCity(CITY);
        });

        step("Submit registration form", registrationFormPage::clickSubmit);

        step("Check results in opened table", () -> {
            registrationFormPage.checkResultsTableVisible()
                    .checkResult("Student Name", data.firstName + " " + data.lastName)
                    .checkResult("Student Email", data.email)
                    .checkResult("Gender", data.gender)
                    .checkResult("Mobile", data.mobilePhone)
                    .checkResult("Date of Birth", data.dayOfBirth + " " + data.monthOfBirth + "," + data.yearOfBirth)
                    .checkResult("Subjects", SUBJECT)
                    .checkResult("Hobbies", FIRST_HOBBY + ", " + SECOND_HOBBY + ", " + THIRD_HOBBY)
                    .checkResult("Address", data.address)
                    .checkResult("State and City", STATE + " " + CITY);
        });
    }

    @Severity(CRITICAL)
    @DisplayName("Fill form with minimum data")
    @Test
    void fillFormWithMinimumDataTest() {
        step("Open registration form", registrationFormPage::openPage);

        step("Fill registration form", () -> {
            registrationFormPage.setFirstName(data.firstName)
                    .setLastName(data.lastName)
                    .setGender(data.gender)
                    .setMobilePhone(data.mobilePhone)
                    .setDateOfBirth(data.dayOfBirth, data.monthOfBirth, data.yearOfBirth);
        });

        step("Submit registration form", registrationFormPage::clickSubmit);

        step("Check results in opened table", () -> {
            registrationFormPage.checkResultsTableVisible()
                    .checkResult("Student Name", data.firstName + " " + data.lastName)
                    .checkResultIsEmpty("Student Email")
                    .checkResult("Gender", data.gender)
                    .checkResult("Mobile", data.mobilePhone)
                    .checkResult("Date of Birth", data.dayOfBirth + " " + data.monthOfBirth + "," + data.yearOfBirth)
                    .checkResultIsEmpty("Subjects")
                    .checkResultIsEmpty("Hobbies")
                    .checkResultIsEmpty("Picture")
                    .checkResultIsEmpty("Address")
                    .checkResultIsEmpty("State and City");
        });
    }
}
