package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String CREATE = "/api/v1/courier";

    private static final String LOGIN = "/api/v1/courier/login";

    private static final String DELETE = "/api/v1/courier/%s";

    @Step("Create courier")
    public Response createCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(CREATE);
    }

    @Step("Login courier")
    public Response loginCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(LOGIN);
    }

    @Step("Delete courier")
    public Response deleteCourier(String id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format(DELETE, id));
    }
}
