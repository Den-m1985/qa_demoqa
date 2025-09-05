package data.api;

import data.models.CookieDto;
import data.models.CredentialsDto;
import data.models.TokenDto;
import data.models.UserDto;

import static data.specs.RequestSpecs.jsonRequestSpec;
import static data.specs.RequestSpecs.requestSpec;
import static data.specs.ResponseSpecs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyOrNullString;

public class AuthorizationApi {

    public UserDto createUser(CredentialsDto credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().as(UserDto.class);
    }

    public TokenDto getToken(CredentialsDto credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(TokenDto.class);
    }

    public CookieDto login(CredentialsDto credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(CookieDto.class);
    }

    public void deleteUser(UserDto user, TokenDto token) {
        given(requestSpec)
                .header("Authorization", "Bearer " + token.token())
                .when()
                .delete("/Account/v1/User/" + user.userId())
                .then()
                .spec(responseSpec)
                .statusCode(204)
                .body(is(emptyOrNullString()));
    }
}
