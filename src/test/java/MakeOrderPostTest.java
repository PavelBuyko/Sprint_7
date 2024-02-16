import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import resources.JSONBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertFalse;

//POST
@RunWith(Parameterized.class)
public class MakeOrderPostTest {
    private final String MAKEORDERPATH = "/api/v1/orders";
    private final String REMOVEORDERPATH = "/api/v1/orders/cancel";
    private final List<String> color;
    private static String track;
    JSONBuilder json;

    public MakeOrderPostTest(List<String> color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                        {Arrays.asList("BLACK", "GREY")},
                        {Arrays.asList("GREY")},
                        {Arrays.asList("BLACK")},
                        {Arrays.asList("")}
        });
    }


        @Before
        @Step("Создаем заготовку JSON заказа")
        public void fillUntestedData()
        {   json = new JSONBuilder();
            json.setFirstName("имя");
            json.setLastName("фамилия");
            json.setAddress("адрес");
            json.setMetroStation("метро");
            json.setPhone("телефон");
            json.setRentTime("1");
            json.setDeliveryDate("2020-06-06");
            json.setComment("");

        }

        @Test
        @DisplayName("Возможность выбора разных цветов и проверка тела ответа")
        @Step("Добавляем цвет в JSON и передаем запрос")
        public void orderColorTest()
        {
            json.setColor(color);
            PostAPI api = new PostAPI();
            Gson gson = new Gson();
            String jsonBody = gson.toJson(this.json);
            Response response = api.doPostRequest(MAKEORDERPATH,jsonBody);
            response.then().assertThat().statusCode((201));
            JSONBuilder Resopncejson = gson.fromJson(response.getBody().prettyPrint(),JSONBuilder.class);
            String actualTrack = Resopncejson.getTrack();
            assertFalse(actualTrack.isEmpty());
            track = actualTrack;

        }


    @After
    @Step("Удаляем заказ и очищаем JSON")
    public void clearData() {
        JSONBuilder finishJson = new JSONBuilder();
        finishJson.setTrack(track);
        PutAPI removeOrder = new PutAPI();
        removeOrder.doPutRequest(REMOVEORDERPATH, finishJson);
    }
}














/*
@RunWith(Parameterized.class)
public class CalculatorTest {
    private final int firstNumber;
    private final int secondNumber;
    private final int expected;

    public CalculatorTest(int firstNumber, int secondNumber, int expected) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.expected = expected;
    }


 */