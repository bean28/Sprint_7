import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest extends BaseTest {

    String firstName;
    String lastName;
    String address;
    String metroStation;
    String phone;
    int rentTime;
    String deliveryDate;
    String comment;
    List<String> color;

    public CreateOrderParametrizedTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа. Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7} {8}")
    public static Object[][] parametersOfCourier() {
        return new Object[][]{
                {"Petya", "Sorokin", "Lenina 5", "2", "+7 800 355 35 35", 5, "2020-06-06", "efhvbeurfh", List.of("GREY")},
                {"Igor", "Sorokin", "Lenina 6", "2", "+7 800 355 35 36", 5, "2020-06-06", "efhvbeurfh", List.of("BLACK")},
                {"Masha", "Sorokin", "Lenina 7", "2", "+7 800 355 35 37", 5, "2020-06-06", "efhvbeurfh", List.of()},
                {"Irina", "Sorokin", "Lenina 8", "2", "+7 800 355 35 38", 5, "2020-06-06", "efhvbeurfh", List.of("GREY", "BLACK")}
        };
    }

    @Test
    public void createOrder() throws JsonProcessingException {
        String json = "{\"firstName\":\"" + firstName
                + "\",\"lastName\":\"" + lastName
                + "\",\"address\":\"" + address
                + "\",\"metroStation\":\"" + metroStation
                + "\",\"phone\":\"" + phone
                + "\",\"rentTime\":" + rentTime
                + ",\"deliveryDate\":\"" + deliveryDate
                + "\",\"comment\":\"" + comment
                + "\",\"color\":" + new ObjectMapper().writeValueAsString(color) + "}";

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("track", notNullValue());
    }
}
