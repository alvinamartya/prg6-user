package id.ac.polman.astra.nim0320180002.user.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import id.ac.polman.astra.nim0320180002.user.R;
import id.ac.polman.astra.nim0320180002.user.architecture.repository.UserRepository;
import id.ac.polman.astra.nim0320180002.user.architecture.viewmodel.UserListViewModel;
import id.ac.polman.astra.nim0320180002.user.model.User;

public class UserListFragment extends Fragment {
    private static final String TAG = "UserListFragment";

    private UserListViewModel mUserListViewModel;
    private RecyclerView mUserRecyclerView;
    private UserAdapter mAdapter;

    public interface Callbacks {
        public void onUserSelected(String id);
    }

    private Callbacks mCallbacks = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: called");
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: called");
        mCallbacks = null;
    }

    private void updateUI(List<User> users) {
        Log.i(TAG, "updateUI: Called");
        mAdapter = new UserAdapter(users);
        mUserRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: called");
        setHasOptionsMenu(true);
        mUserListViewModel = new ViewModelProvider(this)
                .get(UserListViewModel.class);
        mAdapter = new UserAdapter(Collections.emptyList());
    }

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        mUserRecyclerView = view.findViewById(R.id.user_recycler_view);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        Log.i(TAG, "onViewCreated: called");
        mUserListViewModel.getUsers().observe(getViewLifecycleOwner(),
                users -> {
                    updateUI(users);
                    Log.d(TAG, "Got Users: " + users.size());
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_user_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_user:
                User user = new User();
                mUserListViewModel.addUser(user);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                mCallbacks.onUserSelected(user.getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mIdTextView;
        private TextView mUsernameTextView;
        private LinearLayout mUserLayout;
        private User mUser;

        public UserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_user, parent, false));
            itemView.setOnClickListener(this);
            mUserLayout = itemView.findViewById(R.id.user_linear_layout);
            mIdTextView = itemView.findViewById(R.id.user_id);
            mUsernameTextView = itemView.findViewById(R.id.user_name);
        }

        public void bind(User user) {
            mUser = user;
            mIdTextView.setText(mUser.getId());
            mUsernameTextView.setText(mUser.getUsername());
            mUserLayout.setOnLongClickListener((View.OnLongClickListener) view -> {
                new AlertDialog
                        .Builder(getContext())
                        .setTitle("Hapus data")
                        .setMessage("Apakah anda yakin untuk menghapus data ini?")
                        .setPositiveButton("Ya", (dialogInterface, i) -> {
                            mUserListViewModel.deleteUser(user.getId());
                        })
                        .setNegativeButton("Tidak", null)
                        .show();
                return true;
            });
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onUserSelected(mUser.getId());
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        private List<User> mUserList;

        public UserAdapter(List<User> users) {
            mUserList = users;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new UserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            User user = mUserList.get(position);
            holder.bind(user);
        }

        @Override
        public int getItemCount() {
            return mUserList.size();
        }
    }
}
