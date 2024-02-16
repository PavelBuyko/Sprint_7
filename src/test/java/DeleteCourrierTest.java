import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import resources.JSONBuilder;

//DELETE
public class DeleteCourrierTest {
    private final String CREATEPATH = "/api/v1/courier";
    private final String LOGINPATH = "/api/v1/courier/login";
    private final String DELETEPATH = "/api/v1/courier/";
    private String login;
    private String password;
    private String id;

    @Before
    @Step ("Создаем курьера и получаем его логин")
    public void makeCourrier()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setRandomPassword();
        data.setFirstName("asldjqwe");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(CREATEPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        //создаем json для логина
        JSONBuilder body = new JSONBuilder();
        body.setLogin(login);
        body.setPassword(password);
        //делаем логин, чтобы в ответе получить id курьера
        PostAPI loginapi = new PostAPI();
        Response loginresponse = loginapi.doPostRequest(LOGINPATH,body);
        //достаем id из ответа
        Gson gson = new Gson();
        JSONBuilder json = gson.fromJson(loginresponse.getBody().prettyPrint(),JSONBuilder.class);
        this.id = json.getId();
    }


    @Test
    @DisplayName("Удаление курьера")
    @Step("Удаление курьера")
    public void deleteCourrier()
    {
        DeleteApi delete = new DeleteApi();
        Response testResponse = delete.doDeleteRequest(DELETEPATH,id);
        testResponse.then().assertThat().statusCode((200));
        testResponse.then().assertThat().onFailMessage("ok: true");
    }
    @Test
    @DisplayName("Нельзя удалить не существующего курьера")
    @Step("Удаление курьера")
    public void deleteNotExistingCourrier()
    {
        DeleteApi delete = new DeleteApi();
        Response testrResponse = delete.doDeleteRequest(DELETEPATH,"000000");
        testrResponse.then().assertThat().statusCode((404));
        testrResponse.then().assertThat().onFailMessage("Курьера с таким id нет");
    }
    @Test
    @DisplayName("Нельзя удалить курьера, если не передать ID")
    @Step("Удаление курьера")
    public void deleteCourrierWithoutID()
    {
        DeleteApi delete = new DeleteApi();
        Response testrResponse = delete.doDeleteRequest(DELETEPATH,"");
        testrResponse.then().assertThat().statusCode((400));
        testrResponse.then().assertThat().onFailMessage("Недостаточно данных для удаления курьера");
    }


}
