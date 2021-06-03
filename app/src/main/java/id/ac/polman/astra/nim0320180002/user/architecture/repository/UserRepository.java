package id.ac.polman.astra.nim0320180002.user.architecture.repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.polman.astra.nim0320180002.user.api.ApiUtils;
import id.ac.polman.astra.nim0320180002.user.api.UserService;
import id.ac.polman.astra.nim0320180002.user.architecture.dao.UserDao;
import id.ac.polman.astra.nim0320180002.user.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class UserRepository {
    private static UserRepository INSTANCE;
    private static UserDao sUserDao = new UserDao();
    private UserService mUserService;

    private UserRepository(Context context) {
        mUserService = ApiUtils.getUserService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            sUserDao = new UserDao();
            INSTANCE = new UserRepository(context);
        }
    }

    public static UserRepository get() {
        return INSTANCE;
    }

    public LiveData<List<User>> getUsers() {
        Call<List<User>> call = mUserService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sUserDao.loadUsers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        return sUserDao.getUsers();
    }

    public LiveData<User> getUser(String userId) {
        MutableLiveData<User> userData = new MutableLiveData<>();
        Call<User> call = mUserService.getUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sUserDao.updateUser(response.body());
                    userData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return userData;
    }

    public void deleteUser(String userId) {
        Call<User> call = mUserService.deleteUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sUserDao.deleteUser(userId);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Error API call : " + t.getMessage());
            }
        });
    }

    public void addUser(User user) {
        Call<User> call = mUserService.addUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sUserDao.updateUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Error API call : " + t.getMessage());
            }
        });
    }
}
