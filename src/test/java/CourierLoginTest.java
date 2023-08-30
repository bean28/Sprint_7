import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest extends BaseTest {

    String login = "Popovich78";
    String password = "7536";

    CourierClient courierClient = new CourierClient();

    @Before
    public void createCourier() {
        courierClient.createCourier(new Courier(login, password));
    }

    @Test
    @DisplayName("Login courier")
    public void loginCourierTest() {
        Response response = courierClient.loginCourier(new Courier(login, password));
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Login courier with invalid login")
    public void loginCourierWithInvalidLoginTest() {
        Response response = courierClient.loginCourier(new Courier("wronglogin", password));
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with invalid password")
    public void loginCourierWithInvalidPasswordTest() {
        Response response = courierClient.loginCourier(new Courier(login, "wrongpass"));
        response.then().assertThat().statusCode(404);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with empty password")
    public void loginCourierWithEmptyPasswordTest() {
        Response response = courierClient.loginCourier(new Courier(login, ""));
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier with empty login")
    public void loginCourierWithEmptyLoginTest() {
        Response response = courierClient.loginCourier(new Courier("", password));
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void deleteCourier() {
        Response response = courierClient.loginCourier(new Courier(login, password));
        int courierId = response.getBody().path("id");
        courierClient.deleteCourier(Integer.toString(courierId));
    }
}
