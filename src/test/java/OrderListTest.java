import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest extends BaseTest {

    @Test
    public void getOrderListTest() {

        Response response = given()
                .when()
                .get("/api/v1/orders");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("orders", notNullValue());
    }
}