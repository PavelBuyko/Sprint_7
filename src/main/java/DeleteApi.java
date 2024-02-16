import io.restassured.response.Response;
import resources.BaseHttpClient;

public class DeleteApi extends BaseHttpClient {
    public Response doDeleteRequest(String path, String user) {
        return super.doDeleteRequest(path, user);
    }
}

