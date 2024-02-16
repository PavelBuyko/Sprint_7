import io.restassured.response.Response;
import resources.BaseHttpClient;

public class PostAPI extends BaseHttpClient {
    public Response doPostRequest(String path, Object body)
    {
        return super.doPostRequest(path,body);
    }

}
