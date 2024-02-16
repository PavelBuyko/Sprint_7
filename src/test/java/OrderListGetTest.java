import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertFalse;

//GET
public class OrderListGetTest {

    private final String APIPATH = "/api/v1/orders";

    @Test
    @DisplayName("Проверь, что в тело ответа возвращается список заказов.")
    public void getOrderlistAll()
    {
        GetAPI api = new GetAPI();
        Response response = api.getApi(APIPATH);
        response.then().assertThat().statusCode((200));
        assertFalse(response.then().extract().body().asString().isEmpty());
    }
}
