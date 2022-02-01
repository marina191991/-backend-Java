package org.example.PetStore;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class User extends AbstractTest {

    @Test
    void CreateWithArray() {
        given()

                .body("[\n" +
                        "  {\n" +
                        "    \"id\": 0,\n" +
                        "    \"username\": \"string\",\n" +
                        "    \"firstName\": \"string\",\n" +
                        "    \"lastName\": \"string\",\n" +
                        "    \"email\": \"string\",\n" +
                        "    \"password\": \"string\",\n" +
                        "    \"phone\": \"string\",\n" +
                        "    \"userStatus\": 0\n" +
                        "  }\n" +
                        "]")
                .contentType("application/json")
                .when()
                .post(url + "user/createWithArray")
                .then()
                .statusCode(200)
                .body("type", response -> equalTo("unknown"))
                .contentType("application/json")
                .assertThat().header("access-control-allow-headers", "Content-Type, api_key, Authorization");

    }

    @Test
    void CreateWithList() {
        given()
                .body("[\n" +
                        "  {\n" +
                        "    \"id\": 0,\n" +
                        "    \"username\": \"string\",\n" +
                        "    \"firstName\": \"string\",\n" +
                        "    \"lastName\": \"string\",\n" +
                        "    \"email\": \"string\",\n" +
                        "    \"password\": \"string\",\n" +
                        "    \"phone\": \"string\",\n" +
                        "    \"userStatus\": 0\n" +
                        "  }\n" +
                        "]")
                .contentType("application/json")
                .accept("application/json")
                .when()
                .post(url + "user/createWithList")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("type", response -> equalTo("unknown"))
                .body("message", response -> equalTo("ok"));
    }

    @Test
    void GetUserByUserName() {
        String userN = given()
                .when()
                .get(url + "user/" + (String) prop.get("user"))
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response()
                .jsonPath()
                .getString("username");
        assertThat(userN, equalTo((String) prop.get("user")));
    }


    @Test
    void loginUserIntoSystem() {
        given()
                .queryParam("username", (String) prop.get("userName"))
                .queryParam("password", (String) prop.get("password"))
                .accept("application/json")
                .when()
                .get(url + "user/login")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }


    @Test
    void deleteUser() {
        given()
                .accept("application/json")
                .when()
                .delete(url+"user/"+(String) prop.get("user"))
                .then()
                .statusCode(200)
                .body("message", response -> equalTo((String) prop.get("user")))
                .contentType("application/json");

    }
}