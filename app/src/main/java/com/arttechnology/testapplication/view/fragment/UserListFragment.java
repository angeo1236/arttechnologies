package com.arttechnology.testapplication.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arttechnology.testapplication.R;
import com.arttechnology.testapplication.adapter.UserListAdapter;
import com.arttechnology.testapplication.data.UserModel;
import com.arttechnology.testapplication.interfaces.RecyclerViewInterface;
import com.arttechnology.testapplication.viewmodel.UserViewModel;


/**
 * Created by angeooo on 28-Jan-20.
 */
public class UserListFragment extends BaseFragment implements RecyclerViewInterface {

    private RecyclerView users_rv;
    private UserListAdapter userListAdapter;
    private TextView total_td;
    private UserListFragment() {
    }

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        users_rv = view.findViewById(R.id.users_rv);
        total_td = view.findViewById(R.id.total_td);

        userListAdapter = new UserListAdapter(this::onItemClick);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        showProgress();
        userViewModel.getAllUsers().observe(this, userModels -> {
            dismissProgress();
            userListAdapter.setDataList(userModels);
            total_td.setText("Total "+userModels.size()+" users found!");

        });

        users_rv.setAdapter(userListAdapter);
        users_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    public void replaceFragment(Fragment fragment, UserModel userModel) {

        Bundle bundle=new Bundle();
        bundle.putSerializable("UserObject",userModel);
        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.addToBackStack("Userlist");
            fragmentTransaction.add(R.id.container_layout, fragment);
            fragmentTransaction.commit();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void onItemClick(UserModel userModel) {
        replaceFragment(new UserDetailFragment(),userModel);
    }
}
