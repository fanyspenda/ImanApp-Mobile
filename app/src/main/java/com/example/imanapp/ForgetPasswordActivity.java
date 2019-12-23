package com.example.imanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    Button btnSendToEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etEmail = findViewById(R.id.forgotPasswordEdtEmail);
        btnSendToEmail = findViewById(R.id.forgotPasswordbtnFrgtPass);
        firebaseAuth = FirebaseAuth.getInstance();

        btnSendToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterActivity.buttonController(false, btnSendToEmail, getApplicationContext());

                if(TextUtils.isEmpty(etEmail.getText().toString())){
                    RegisterActivity.buttonController(true, btnSendToEmail, getApplicationContext());
                    Toast.makeText(ForgetPasswordActivity.this, "Email masih kosong...", Toast.LENGTH_SHORT).show();
                }

                else {
                    firebaseAuth.sendPasswordResetEmail(etEmail.getText().toString())
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    RegisterActivity.buttonController(true, btnSendToEmail, getApplicationContext());
                                    Toast.makeText(ForgetPasswordActivity.this, "gagal mengirim ke email", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ForgetPasswordActivity.this, "berhasil mengirim reset password ke email", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                }
            }
        });
    }
}
