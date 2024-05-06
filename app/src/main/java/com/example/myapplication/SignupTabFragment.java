package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class SignupTabFragment extends Fragment {


    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_signup_tab, container, false);
    return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etMailLog = view.findViewById(R.id.login_email);
        EditText etPasswordLog = view.findViewById(R.id.login_password);
        // EditText etConfirmLog = view.findViewById(R.id.login_confirm);

        EditText etMailSign = view.findViewById(R.id.signup_email);
        EditText etPasswordSign = view.findViewById(R.id.signup_password);
        EditText etConfirmSign = view.findViewById(R.id.signup_confirm);



        //  String email = etMail.getText().toString();

     //   Toast.makeText(getActivity(),"singup email is " + email,Toast.LENGTH_LONG).show();

    }
}