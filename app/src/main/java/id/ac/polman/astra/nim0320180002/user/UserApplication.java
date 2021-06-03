package id.ac.polman.astra.nim0320180002.user;

import android.app.Application;
import android.util.Log;

import id.ac.polman.astra.nim0320180002.user.architecture.repository.UserRepository;

public class UserApplication extends Application {
    private static final String TAG = "UserApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");
        UserRepository.initialize(this);
    }
}
