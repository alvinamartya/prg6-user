package id.ac.polman.astra.nim0320180002.user.api;

public class ApiUtils {
    public static final String API_URL = "http://192.168.100.4:8080/";
    public static UserService getUserService() {
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
}
