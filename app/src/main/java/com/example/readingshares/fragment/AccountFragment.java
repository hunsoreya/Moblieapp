package com.example.readingshares.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.readingshares.R;
import com.example.readingshares.activity.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    public Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        Button btnLogout;

        View view;
        view = inflater.inflate(R.layout.activity_frameaccount,container,false);

        btnLogout = (Button) view.findViewById(R.id.btn_account_logout);



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LogInActivity.class));
            }
        });


        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
}
