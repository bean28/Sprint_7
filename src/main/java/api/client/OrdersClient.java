package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String ORDER = "/api/v1/orders";

    @Step("Create order")
    public Response createOrder(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(ORDER);
    }

    @Step("Get order list")
    public Response getOrderList() {
        return given()
                .when()
                .get(ORDER);
    }
}
