package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtName;
    private EditText txtPhone;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtName = findViewById(R.id.et1);
        txtEmail = findViewById(R.id.et2);
        txtPhone = findViewById(R.id.et3);
        txtPassword = findViewById(R.id.et4);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void btnRegistrationUser_Click(View v) {

        (firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "RegistrationActivity successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent (RegistrationActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        else{
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void btnUserLogin(View view) {
        Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(i);
    }
}