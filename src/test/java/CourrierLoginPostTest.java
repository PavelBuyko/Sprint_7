import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import resources.JSONBuilder;
import resources.RandomString;

import static org.junit.Assert.assertFalse;


public class CourrierLoginPostTest {
    private final String APIPATH = "/api/v1/courier";
    private final String LOGINPATH = "/api/v1/courier/login";
    private final String DELETEPATH = "/api/v1/courier/";
    private String login;
    private String password;
    private String id;

    @Before
    @Step("Создаем курьера для теста")
    public void usualCourrierCreate()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setRandomPassword();
        data.setFirstName("asdaqweq");
        PostAPI api = new PostAPI();
        api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
    }

    @Test
    @DisplayName("После авторизации приходит id")
    public void baseLoginTest()
    {
        JSONBuilder body = new JSONBuilder();
        body.setLogin(login);
        body.setPassword(password);
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(LOGINPATH,body);
        Gson gson = new Gson();
        JSONBuilder json = gson.fromJson(response.getBody().prettyPrint(),JSONBuilder.class);
        assertFalse(json.getId().isEmpty());
        id = json.getId();
    }
    @Test
    @DisplayName("Возврат ошибки при не передаче Логина")
    public void noLoginAuthTest()
    {
        JSONBuilder body = new JSONBuilder();
        body.setPassword(password);
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(LOGINPATH,body);
        response.then().assertThat().onFailMessage("Недостаточно данных для входа");
        response.then().assertThat().statusCode((400));
    }
    @Test
    @DisplayName("Возврат ошибки при не Пароля")
    public void noPasswordAuthTest()
    {
        JSONBuilder body = new JSONBuilder();
        body.setLogin(login);
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(LOGINPATH,body);
        response.then().assertThat().onFailMessage("Недостаточно данных для входа");
        response.then().assertThat().statusCode((400));
    }
    @Test
    @DisplayName("Возврат ошибки при пустом поле Пароль")
    public void emptyPasswordAuthTest()
    {
        JSONBuilder body = new JSONBuilder();
        body.setLogin(login);
        body.setPassword("");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(LOGINPATH,body);
        response.then().assertThat().statusCode((400));
    }
    @Test
    @DisplayName("Возврат ошибки при пустом поле Логин")
    public void emptyLoginAuthTest()
    {
        JSONBuilder body = new JSONBuilder();
        body.setLogin("");
        body.setPassword(password);
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(LOGINPATH,body);
        response.then().assertThat().statusCode((400));
    }
    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void incorrectDataLoginTest()
    {
        RandomString randomString = new RandomString();
        JSONBuilder body = new JSONBuilder();
        body.setLogin(randomString.generateRandomString(24));
        body.setPassword(randomString.generateRandomString(24));
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(LOGINPATH,body);
        response.then().assertThat().onFailMessage("Учетная запись не найдена");
        response.then().assertThat().statusCode((404));
    }

    @After
    @Step("Удаляем курьера, созданного для теста")
    public void deleteCourier()
    {
            JSONBuilder AfterBody = new JSONBuilder();
            AfterBody.setLogin(login);
            AfterBody.setPassword(password);
            //делаем логин, чтобы в ответе получить id курьера
            PostAPI api = new PostAPI();
            Response afterResponse = api.doPostRequest(LOGINPATH,AfterBody);
            //достаем id из ответа
            Gson afterGson = new Gson();
            JSONBuilder json = afterGson.fromJson(afterResponse.getBody().prettyPrint(),JSONBuilder.class);
            DeleteApi delete = new DeleteApi();
            //отправляем delete request
            delete.doDeleteRequest(DELETEPATH,json.getId());
        }

    }




