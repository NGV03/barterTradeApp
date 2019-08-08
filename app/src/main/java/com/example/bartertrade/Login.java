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

public class Login extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar viewProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.et2);
        txtPassword = findViewById(R.id.et4);
        firebaseAuth = FirebaseAuth.getInstance();
        viewProgressBar = findViewById(R.id.progressBar1);
    }
    public void btnUserLogin(View v){


        (firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Login.this, Home.class);
                            startActivity(i);
                        }else{
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(Login.this, task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void btnUserRegister (View view){
        Intent i = new Intent(Login.this, Registration.class);
        startActivity(i);
    }

}
