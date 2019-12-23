package com.example.imanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextView tvGoToLogin;
    EditText etEmail, etPassword, etConfirmPassword;
    Button btnRegister;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvGoToLogin = findViewById(R.id.registerGoToLogin);
        etEmail = findViewById(R.id.registerEmail);
        etPassword = findViewById(R.id.registerPassword);
        etConfirmPassword = findViewById(R.id.registerConfirmPassword);
        btnRegister = findViewById(R.id.registerBtnRegister);

        tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //diambil dari fungsi yang dibuat di bawah
                buttonController(false, btnRegister, getApplicationContext());

                //untuk validasi email
                String emailTrimmed = etEmail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                //cek panjang password
                if(etPassword.getText().length() < 8 || etConfirmPassword.getText().length() < 8){
                    Toast.makeText(getApplicationContext(), "Password/Konfirmasi password Kurang Panjang", Toast.LENGTH_SHORT).show();

                    buttonController(true, btnRegister, getApplicationContext());
                }

                //cek kecocokan password
                else if(!etPassword.getText().toString().matches(etConfirmPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password tidak sama dengan baris " +
                            "konfirmasi password", Toast.LENGTH_SHORT).show();

                    buttonController(true, btnRegister, getApplicationContext());
                }

                //cek email valid
                else if(!emailTrimmed.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(), "email tidak valid", Toast.LENGTH_SHORT).show();

                    buttonController(true, btnRegister, getApplicationContext());
                }

                else {

                    firebaseAuth = FirebaseAuth.getInstance();
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                            buttonController(true, btnRegister, getApplicationContext());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getApplicationContext(), "berhasil registrasi!", Toast.LENGTH_SHORT).show();
                            buttonController(true, btnRegister, getApplicationContext());
                        }
                    });
                }
            }
        });
    }

    public static void buttonController (boolean isEnable, Button controlledButton, Context context){
        if(isEnable){
            //mengenable button kembali
            controlledButton.setClickable(true);
            controlledButton.setBackgroundColor(context.getColor(R.color.buttonEnabled));
        }
        else {
            //mendisable button
            controlledButton.setClickable(false);
            controlledButton.setBackgroundColor(context.getColor(R.color.buttondisabled));
        }
    }
}
