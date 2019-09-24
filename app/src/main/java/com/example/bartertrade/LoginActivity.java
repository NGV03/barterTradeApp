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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar viewProgressBar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        txtEmail = findViewById(R.id.et2);
        txtPassword = findViewById(R.id.et4);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void addCount(){
        //increase login count by 1 every time user logins
        String user = firebaseAuth.getInstance().getUid();

        if (user != null) {
            db.collection("users").document(user).update("loginCount", FieldValue.increment(1));
        }
    }
    public void btnUserLogin(View v){

        (firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "LoginActivity successful", Toast.LENGTH_LONG).show();
                            addCount();
                            Intent i = new Intent(LoginActivity.this, Home.class);
                            startActivity(i);




                        }else{
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void btnUserRegister (View view){
        Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(i);
    }

}
