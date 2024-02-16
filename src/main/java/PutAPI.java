import io.restassured.response.Response;
import resources.BaseHttpClient;

public class PutAPI extends BaseHttpClient {
    public Response doPutRequest(String path, Object body)
    {
        return super.doPutRequest(path,body);
    }

}
