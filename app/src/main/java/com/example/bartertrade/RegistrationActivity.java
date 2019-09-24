package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtName;
    private EditText txtPhone;
    private FirebaseAuth firebaseAuth;
    private Button mSelectedPhoto;
    private Uri mSelectedUri,profileUrl;
    private ImageView mImgPhoto;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            mSelectedUri = data.getData();


        }
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivity(intent);
    }

    public void btnRegistrationUser_Click(View v) {

        (firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "RegistrationActivity successful", Toast.LENGTH_LONG).show();

                            String uid = FirebaseAuth.getInstance().getUid();
                            String name = txtName.getText().toString();
                            String phone = txtPhone.getText().toString();
                            String email = txtEmail.getText().toString();

                            User user = new User(uid,name,phone,email);

                            FirebaseFirestore.getInstance().collection("users")
                                    .document(uid)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent i = new Intent (RegistrationActivity.this, LoginActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);

                                        }
                                    });

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