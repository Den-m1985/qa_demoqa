package data.api;

import data.models.Cookie;
import data.models.Credentials;
import data.models.Token;
import data.models.UserNew;

import static data.specs.RequestSpecs.jsonRequestSpec;
import static data.specs.RequestSpecs.requestSpec;
import static data.specs.ResponseSpecs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyOrNullString;

public class AuthorizationApi {

    public UserNew createUser(Credentials credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().as(UserNew.class);
    }

    public Token getToken(Credentials credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(Token.class);
    }

    public Cookie login(Credentials credentials) {
        return given(jsonRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(Cookie.class);
    }

    public void deleteUser(UserNew user, Token token) {
        given(requestSpec)
                .header("Authorization", "Bearer " + token.getToken())
                .when()
                .delete("/Account/v1/User/" + user.getUserID())
                .then()
                .spec(responseSpec)
                .statusCode(204)
                .body(is(emptyOrNullString()));
    }
}
