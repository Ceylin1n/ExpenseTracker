package com.example.e;

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

public class RegisterFragment extends Fragment {

    EditText etEmail, etPassword;
    Button btnRegister;
    TextView tvLogin;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        tvLogin = view.findViewById(R.id.tvLogin);

        SharedPreferences prefs = requireActivity().getSharedPreferences("User", requireActivity().MODE_PRIVATE);

        btnRegister.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            prefs.edit()
                    .putString("email", email)
                    .putString("password", password)
                    .apply();

            Toast.makeText(getContext(), "Account Created", Toast.LENGTH_SHORT).show();

            ((MainActivity) requireActivity()).loadFragment(new LoginFragment());

        });

        tvLogin.setOnClickListener(v ->
                ((MainActivity) requireActivity()).loadFragment(new LoginFragment())
        );

        return view;
    }
}