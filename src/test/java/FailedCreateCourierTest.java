import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class FailedCreateCourierTest extends BaseTest {

    String login = "Victorovich78";
    String password = "8456";
    String firstName = "saske";

    CourierClient courierClient = new CourierClient();

    @Test
    @DisplayName("Failed to create courier without password")
    public void createCourierWithoutPassTest() {
        Response response = courierClient.createCourier(new Courier(login, null, firstName));
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Failed to create courier with empty password")
    public void createCourierWithEmptyPassTest() {
        Response response = courierClient.createCourier(new Courier(login, "", firstName));
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Failed to create courier without login")
    public void createCourierWithoutLoginTest() {
        Response response = courierClient.createCourier(new Courier(null, password, firstName));
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Failed to create courier with empty login")
    public void createCourierWithEmptyLoginTest() {
        Response response = courierClient.createCourier(new Courier("", password, firstName));
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
