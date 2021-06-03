package id.ac.polman.astra.nim0320180002.user.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import id.ac.polman.astra.nim0320180002.user.R;
import id.ac.polman.astra.nim0320180002.user.architecture.viewmodel.UserDetailViewModel;
import id.ac.polman.astra.nim0320180002.user.model.User;


public class UserFragment extends Fragment {
    private static final String ARG_USER_ID = "user_id";
    private static final String TAG = "UserFragment";

    private User mUser;
    private EditText mUsernameField;
    private UserDetailViewModel mUserDetailViewModel;
    private String mUserId;

    public UserDetailViewModel getUserDetailViewModel() {
        if (mUserDetailViewModel == null) {
            mUserDetailViewModel = new ViewModelProvider(this)
                    .get(UserDetailViewModel.class);
        }

        return mUserDetailViewModel;
    }

    public static UserFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateUI() {
        Log.i(TAG, "updateUI: called");
        mUsernameField.setText(mUser.getUsername());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "UserFragment.onViewCreated: called");
        mUserDetailViewModel.getUserLiveData().observe(
                getViewLifecycleOwner(),
                user -> {
                    mUser = user;
                    updateUI();
                }
        );
        mUserDetailViewModel.loadUser(mUserId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: called");
        mUserId = (String) getArguments().getSerializable(ARG_USER_ID);
        mUser = new User();
        mUserDetailViewModel = getUserDetailViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        mUsernameField = v.findViewById(R.id.username);
        mUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: called");
        mUserDetailViewModel.saveUser(mUser);
    }
}
