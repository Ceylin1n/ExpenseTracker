package com.example.e;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvRegister = view.findViewById(R.id.tvRegister);

        SharedPreferences prefs = requireActivity().getSharedPreferences("User", requireActivity().MODE_PRIVATE);

        if (prefs.getBoolean("logged", false)) {
            startActivity(new Intent(getActivity(), MainActivity2.class));
            requireActivity().finish();
        }

        tvRegister.setOnClickListener(v ->
                ((MainActivity) requireActivity()).loadFragment(new RegisterFragment())
        );

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String savedEmail = prefs.getString("email", "");
            String savedPassword = prefs.getString("password", "");

            if (email.equals(savedEmail) && password.equals(savedPassword)) {

                prefs.edit().putBoolean("logged", true).apply();

                startActivity(new Intent(getActivity(), MainActivity2.class));
                requireActivity().finish();

            } else {

                Toast.makeText(getContext(), "Wrong Email or Password", Toast.LENGTH_SHORT).show();

            }

        });

        return view;
    }
}