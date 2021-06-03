package id.ac.polman.astra.nim0320180002.user.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import id.ac.polman.astra.nim0320180002.user.R;
import id.ac.polman.astra.nim0320180002.user.ui.fragment.UserFragment;
import id.ac.polman.astra.nim0320180002.user.ui.fragment.UserListFragment;

public class MainActivity extends AppCompatActivity implements UserListFragment.Callbacks {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = UserListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onUserSelected(String id) {
        Fragment fragment = UserFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}