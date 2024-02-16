import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import resources.JSONBuilder;


public class CreateCourrierPostTest {
    private final String APIPATH = "/api/v1/courier";
    private final String LOGINPATH = "/api/v1/courier/login";
    private final String DELETEPATH = "/api/v1/courier/";
    private String login;
    private String password;

    @Test
    @DisplayName("курьера можно создать")
    public void usualCourrierCreate()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setRandomPassword();
        data.setFirstName("asdaqweq");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((201));
    }
    @Test
    @DisplayName("нельзя создать двух одинаковых курьеров")
    public void createSameCourrier()
    {
        JSONBuilder data = new JSONBuilder("ninja","1234","saske");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((409));
    }
    @Test
    @DisplayName("Нельзя создать курьера с пустым полем 'Логин'")
    public void tryCreateCourrierWithoutName()
    {
        JSONBuilder data = new JSONBuilder("","1234","saske");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((400));
        response.then().assertThat().onFailMessage("Недостаточно данных для создания учетной записи");

    }
    @Test
    @DisplayName("Нельзя создать курьера с пустым полем 'Пароль'")
    public void tryCreateCourrierWithoutPassword()
    {
        JSONBuilder data = new JSONBuilder("miuipmtun","","sasske");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((400));
        response.then().assertThat().onFailMessage("Недостаточно данных для создания учетной записи");

    }
    @Test
    @DisplayName("Нельзя создать курьера с пустым полем 'Имя'")
    public void tryCreateCourrierWithoutFirstname()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setRandomPassword();
        data.setFirstName("");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((400));
        response.then().assertThat().onFailMessage("Недостаточно данных для создания учетной записи");

    }
    @Test
    @DisplayName("Проверка кода ответа при успешном создании курьера")
    public void checkResponse()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setRandomPassword();
        data.setFirstName("asdaqweq");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((201));
    }
    @Test
    @DisplayName("Нельзя создать курьера уже существующим логином")
    public void checkErrorResponseSameLogin()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setRandomPassword();
        data.setFirstName("asdaqweq");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().onFailMessage("Этот логин уже используется. Попробуйте другой.");
    }
    @Test
    @DisplayName("Нельзя создать курьера если не передавать поле 'Имя'")
    public void tryCreateCourrierWithoutNameField()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomPassword();
        data.setFirstName("bla-bla-bla");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((400));

    }
    @Test
    @DisplayName("Нельзя создать курьера если не передавать поле 'Пароль'")
    public void tryCreateCourrierWithoutPasswordField()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setFirstName("bla-bla-bla");
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((400));
        response.then().assertThat().onFailMessage("Недостаточно данных для создания учетной записи");

    }
    @Test
    @DisplayName("Нельзя создать курьера если не передавать поле 'Имя'")
    public void tryCreateCourrierWithoutFirstNameField()
    {
        JSONBuilder data = new JSONBuilder();
        data.setRandomLogin();
        data.setRandomPassword();
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(APIPATH,data);
        this.login = data.getLogin();
        this.password = data.getPassword();
        response.then().assertThat().statusCode((400));

    }
    @After
    @Step("Удалить созданного для теста курьера")
    public void deleteCourrier()
    {   //создаем json для логина
        JSONBuilder body = new JSONBuilder();
        body.setLogin(login);
        body.setPassword(password);
        //делаем логин, чтобы в ответе получить id курьера
        PostAPI api = new PostAPI();
        Response response = api.doPostRequest(LOGINPATH,body);
        //достаем id из ответа
        Gson gson = new Gson();
        JSONBuilder json = gson.fromJson(response.getBody().prettyPrint(),JSONBuilder.class);
        DeleteApi delete = new DeleteApi();
        //отправляем delete request
        delete.doDeleteRequest(DELETEPATH,json.getId());
    }
}



