import io.restassured.response.Response;
import resources.BaseHttpClient;

public class GetAPI extends BaseHttpClient {

    public Response getApi(String path)
    {
        return doGetRequest(path);
    }
}
