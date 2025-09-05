package tests.api;

import data.RegistrationData;
import data.api.AuthorizationApi;
import data.models.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBaseApi;

import static config.user.UserProperties.PASSWORD;
import static config.user.UserProperties.USERNAME;
import static data.specs.RequestSpecs.jsonRequestSpec;
import static data.specs.RequestSpecs.requestSpec;
import static data.specs.ResponseSpecs.responseSpec;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@Owner("DenisQA")
@Epic(value = "Book Store Application")
@Feature(value = "API")
@Story(value = "Account API")
@Tag("api")
class AccountTests  extends TestBaseApi{
    private final AuthorizationApi authorizationApi = new AuthorizationApi();
    private final Credentials credentials = new Credentials();
    private final RegistrationData data = new RegistrationData();

    @Severity(CRITICAL)
    @DisplayName("New user creation with success")
    @Description("post /Account/v1/User")
    @Test
    void createNewUserTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        UserNew user = authorizationApi.createUser(credentials);

        assertThat(user.getUserID()).isNotEmpty();
        assertThat(user.getUsername()).isEqualTo(credentials.getUserName());
        assertThat(user.getBooks()).isEmpty();
    }

    @Severity(NORMAL)
    @DisplayName("New user creation with error 'User exists!'")
    @Description("post /Account/v1/User")
    @Test
    void reCreateOldUserTest() {
        credentials.setUserName(USERNAME);
        credentials.setPassword(PASSWORD);

        given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec)
                .statusCode(406)
                .body("code", is("1204"),
                        "message", is("User exists!"));
    }

    @Severity(CRITICAL)
    @DisplayName("New user token generation with success")
    @Description("post /Account/v1/GenerateToken")
    @Test
    void getTokenTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        authorizationApi.createUser(credentials);
        Token token = authorizationApi.getToken(credentials);

        assertThat(token.getToken()).isNotEmpty();
        assertThat(token.getExpires()).isNotEmpty();
        assertThat(token.getStatus()).isEqualTo("Success");
        assertThat(token.getResult()).isEqualTo("User authorized successfully.");
    }


    @Severity(CRITICAL)
    @DisplayName("New user deletion with success")
    @Description("delete /Account/v1/User/{UUID}")
    @Test
    void deleteUserTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        UserNew user = authorizationApi.createUser(credentials);
        Token token = authorizationApi.getToken(credentials);

        authorizationApi.deleteUser(user, token);

        given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .spec(responseSpec)
                .statusCode(404)
                .body("code", is("1207"),
                        "message", is("User not found!"));
    }

    @Severity(NORMAL)
    @DisplayName("New user deletion with error 'User not authorized!'")
    @Description("delete /Account/v1/User/{UUID}")
    @Test
    void deleteUnauthorizedUserTest() {
        credentials.setUserName(data.firstName + data.lastName);
        credentials.setPassword(PASSWORD);

        UserNew user = authorizationApi.createUser(credentials);

        given(requestSpec)
                .when()
                .delete("/Account/v1/User/" + user.getUserID())
                .then()
                .spec(responseSpec)
                .statusCode(401)
                .body("code", is("1200"),
                        "message", is("User not authorized!"));
    }
}
