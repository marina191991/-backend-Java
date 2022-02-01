package org.example.PetStore;

import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.swing.UIManager.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Pet extends AbstractTest {
    public static long idP;
    @Test
    void addPet() {
         idP =given()
                .body("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \n"  +  (String) prop.get("petNameBody")+
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .accept("application/json")
                .contentType("application/json")
                .when()
                .post(url+"pet")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("category.name", response -> equalTo((String) prop.get("petName")))
                .contentType("application/json")
                .extract()
                 .response()
                 .jsonPath()
                 .getLong("id");

                System.out.println(idP);
    }

    @Test
    void updateAnExistingPet() {
        given()
                .body("{\n" +
                        "  \"id\":\n" + idP  + ",\n"+
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .contentType("application/json")
                .accept("application/json")
                .when()
                .put(url+"pet")
                .then()
                .contentType("application/json")
                .statusCode(200)
                .body("category.name", response -> equalTo("string"))
                .time(lessThan(2L), SECONDS);

    }
    @Test
    void deletePet() {
        given()
                .accept("application/json")
                .header("api_key",(String) prop.get("petName"))
                .when()
                .post(url+"pet/{idP}",idP)
                .then()
                .statusCode(200)
                .contentType("application/json");



    }

    @Test
    void findByStatus() {
        List <String> f = given()
                .queryParams("status", "available")
                .accept("application/json")
                .when()
                .get(url + "pet/findByStatus")
                .then()
                .contentType("application/json")
                .statusCode(200)
               .extract()
              .response()
                .jsonPath()
                .getList("status");
        assertThat(f ,hasItem("available"));



    }
    @Test
    void uploadImage() {
        given()
                .multiPart(new File("src/test/resources/catPicture.jpg"))
                .contentType("multipart/form-data")
                .accept("application/json")
                .when()
                .post(url+"pet/"+idP+"/uploadImage")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json");


    }
}
