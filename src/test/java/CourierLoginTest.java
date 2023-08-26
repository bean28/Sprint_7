import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest extends BaseTest {

    String login = "Popovich78";
    String password = "7536";

    @Before
    public void createCourier() {
        String json = String.format("{\"login\": \"%s\",\"password\": \"%s\"}", login, password);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
    }

    @Test
    public void loginCourierTest() {
        String json = String.format("{\"login\": \"%s\",\"password\": \"%s\"}", login, password);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("id", notNullValue());
    }

    @Test
    public void loginCourierWithInvalidLoginTest() {
        String json = String.format("{\"login\": \"%s4\",\"password\": \"%s\"}", login, password);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithInvalidPasswordTest() {
        String json = String.format("{\"login\": \"%s\",\"password\": \"%s5\"}", login, password);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithEmptyPasswordTest() {
        String json = String.format("{\"login\": \"%s\", \"password\": \"\"}", login);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithEmptyLoginTest() {
        String json = String.format("{\"password\": \"%s5\"}", password);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void deleteCourier() {
        String json = String.format("{\"login\": \"%s\",\"password\": \"%s\"}", login, password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        int courierId = response.getBody().path("id");
        String delete = String.format("{\"id\":\"%s\"}", courierId);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(delete)
                .when()
                .delete(String.format("/api/v1/courier/%s", courierId));
    }
}
