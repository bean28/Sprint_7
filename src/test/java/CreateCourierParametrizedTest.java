import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierParametrizedTest extends BaseTest {

    String login;
    String password;
    String firstName;

    public CreateCourierParametrizedTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters(name = "Создание курьера. Тестовые данные: {0} {1} {2}")
    public static Object[][] parametersOfCourier() {
        return new Object[][]{
                {"Popovich78", "1234", "saske"},
                {"Bobovich78", "qwerty", "madara"}
        };
    }

    @Test
    public void createCourierTest() {
        String json = String.format("{\"login\": \"%s\",\"password\": \"%s\",\"firstName\": \"%s\"}", login, password, firstName);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    public void createCourierTwiceTest() {
        String json = String.format("{\"login\": \"%s\",\"password\": \"%s\",\"firstName\": \"%s\"}", login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().assertThat().statusCode(409);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
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


