package id.ac.polman.astra.nim0320180002.user.architecture.dao;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.polman.astra.nim0320180002.user.model.User;

public class UserDao {
    private static final String TAG = UserDao.class.getSimpleName();
    private MutableLiveData<List<User>> users = new MutableLiveData<>();
    public LiveData<List<User>> getUsers() {
        return users;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private User getUserById(String userId, List<User> userList) {
        return userList
                .stream()
                .filter(x -> x.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public void loadUsers(List<User> usersData) {
        users.setValue(usersData);
    }

    public void deleteUser(String userId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<User> userList = users.getValue();
            if(userList != null) {
                User selectedUser = userList
                        .stream().filter(x->x.getId().equals(userId))
                        .findAny().orElse(null);

                if(selectedUser != null) {
                    userList.remove(selectedUser);
                    users.setValue(userList);
                }
            }
        }
    }

    public void updateUser(User user) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<User> userList = users.getValue();
            if(userList != null) {
                User selectedUser = getUserById(user.getId(), userList);

                if(selectedUser != null) {
                    selectedUser.setUsername(user.getUsername());
                } else {
                    userList.add(user);
                }
                users.setValue(userList);
            }
        }
    }
}
