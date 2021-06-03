package id.ac.polman.astra.nim0320180002.user.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.polman.astra.nim0320180002.user.architecture.repository.UserRepository;
import id.ac.polman.astra.nim0320180002.user.model.User;

public class UserListViewModel extends ViewModel {
    private UserRepository mUserRepository;

    public UserListViewModel() {
        mUserRepository = UserRepository.get();
    }

    public LiveData<List<User>> getUsers() {
        return mUserRepository.getUsers();
    }

    public void addUser(User user) {
        mUserRepository.addUser(user);
    }

    public void deleteUser(String userId) {mUserRepository.deleteUser(userId);}
}
