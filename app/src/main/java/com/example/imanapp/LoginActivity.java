package com.example.imanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView tvMoveToRegister, tvMoveToForgotPassword;
    EditText etEmail, etPassword;
    Button btnLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvMoveToRegister = findViewById(R.id.loginGoToRegister);
        tvMoveToForgotPassword = findViewById(R.id.loginGoToForgetPassword);
        etEmail = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.loginBtnLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        tvMoveToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        tvMoveToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.buttonController(false, btnLogin, getApplicationContext());

                if(TextUtils.isEmpty(etEmail.getText().toString()) ||
                        TextUtils.isEmpty(etPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Password/Username Kosong", Toast.LENGTH_SHORT).show();
                    RegisterActivity.buttonController(true, btnLogin, getApplicationContext());
                }

                else {
                    firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(),
                            etPassword.getText().toString())
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login Gagal. Coba Lagi..", Toast.LENGTH_SHORT).show();
                                    RegisterActivity.buttonController(true, btnLogin, getApplicationContext());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login Berhasil!", Toast.LENGTH_SHORT).show();
                                    RegisterActivity.buttonController(true, btnLogin, getApplicationContext());
                                    Intent i = new Intent (getApplicationContext(), MainMenuActivity.class);
                                    startActivity(i);
                        }
                    });
                }
            }
        });
    }
}
