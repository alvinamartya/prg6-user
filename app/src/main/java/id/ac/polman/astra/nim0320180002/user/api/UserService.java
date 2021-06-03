package id.ac.polman.astra.nim0320180002.user.api;

import java.util.List;

import id.ac.polman.astra.nim0320180002.user.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
    @GET("user")
    Call<User> getUserById(@Query("id") String id);

    @GET("users")
    Call<List<User>> getUsers();

    @POST("user")
    Call<User> addUser(@Body User user);

    @DELETE("user")
    Call<User> deleteUserById(@Query("id") String id);

}
