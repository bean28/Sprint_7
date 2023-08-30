import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierParametrizedTest extends BaseTest {

    String login;
    String password;
    String firstName;

    CourierClient courierClient = new CourierClient();

    public CreateCourierParametrizedTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters(name = "Создание курьера. Тестовые данные: {0} {1} {2}")
    public static Object[][] parametersOfCourier() {
        return new Object[][]{
                {"Gogovich78", "1234", "saske"},
                {"Bobovich78", "qwerty", "madara"}
        };
    }

    @Test
    @DisplayName("Create courier")
    public void createCourierTest() {
        Response response = courierClient.createCourier(new Courier(login, password, firstName));
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier twice")
    public void createCourierTwiceTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.createCourier(courier);
        Response response = courierClient.createCourier(courier);
        response.then().assertThat().statusCode(409);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void deleteCourier() {
        Response response = courierClient.loginCourier(new Courier(login, password));
        int courierId = response.getBody().path("id");
        courierClient.deleteCourier(Integer.toString(courierId));
    }
}
