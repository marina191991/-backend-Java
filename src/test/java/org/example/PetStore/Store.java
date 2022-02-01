package org.example.PetStore;


import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class Store extends AbstractTest {



    @Test
     void OrderPlaced() {

        given().body("{\n" +
                "  \"id\": 0,\n" +
                "  \"petId\": 0,\n" +
                "  \"quantity\": 0,\n" +
                "  \"shipDate\": \"2022-01-30T19:20:47.542Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}")
                .contentType("application/json")
                .when()
                .post(url+"store/order")
                .then()
                .statusCode(200)
                .contentType("application/json");


}
    @Test
    void FindOrderById() {
        String order = given()
                    .when()
                    .get(url+"store/order/"+((String) prop.get("orderId")))
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("id");
        assertThat(order, equalTo((String) prop.get("orderId")));
    }
    @Test
    void Inventory() {
        given()
        .when()
                .get(url+"store/inventory")
                .then().log().all()
                .statusCode(200)
                .contentType("application/json");

    }



}
