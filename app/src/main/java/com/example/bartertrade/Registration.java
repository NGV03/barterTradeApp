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

public class Registration extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private ProgressBar viewProgressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        txtEmail = findViewById(R.id.et2);
        txtPassword = findViewById(R.id.et4);
        viewProgressBar = findViewById(R.id.progressBar1);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void btnRegistrationUser_Click(View v) {

        viewProgressBar.setVisibility(View.VISIBLE);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        viewProgressBar.setVisibility(View.INVISIBLE);

                        if(task.isSuccessful()){
                            Toast.makeText(Registration.this, "Registration successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent (Registration.this, Login.class);
                            startActivity(i);
                        }
                        else{
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void btnUserLogin(View view) {
        Intent i = new Intent(Registration.this, Login.class);
        startActivity(i);
    }
}