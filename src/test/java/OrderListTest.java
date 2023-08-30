import api.client.OrdersClient;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest extends BaseTest {

    OrdersClient ordersClient = new OrdersClient();

    @Test
    public void getOrderListTest() {
        Response response = ordersClient.getOrderList();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("orders", notNullValue());
    }
}