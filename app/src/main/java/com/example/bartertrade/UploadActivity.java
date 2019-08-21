package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    public FirebaseFirestore db;
    private StorageReference mStorageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri imguri;
    private  String userid;
    private Button btnSave, btnUpload;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        db = FirebaseFirestore.getInstance();

        //initializing Storagereference
        mStorageRef = FirebaseStorage.getInstance().getReference();
        btnSave = (Button) findViewById(R.id.button_save);
        btnUpload = (Button) findViewById(R.id.button_upload);
        imageView = (ImageView) findViewById(R.id.iv_1);

        btnSave.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpload){
            // open file chooser
            Filechooser();

        } else if(v == btnSave){
            // upload file to firebase storage
            Fileuploader();
        }
    }

    private  void Fileuploader(){
        if(imguri != null ) {
            StorageReference ref = mStorageRef.child("Item/item.jpg");
            Toast.makeText(UploadActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();

            ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UploadActivity.this, "Image uploaded successfully!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
        saveData(imguri);
    }

    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selectan Image"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData()!= null){
            imguri = data. getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                imageView.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

//    public void goHome(View view) {
//        Intent goHome = new Intent(this, Home.class);
//        saveData();
//        startActivity(goHome);
//        finish();
//    }

    private void saveData(Uri imguri) {
        Map<String, String> data = new HashMap<>();
        EditText title = findViewById(R.id.etv_1);
        EditText shortDesc = findViewById(R.id.etv_2);
        EditText location = findViewById(R.id.etv_3);
        String eTitle = title.getText().toString();
        String eShortDesc = shortDesc.getText().toString();
        String eLocation = location.getText().toString();
        String eUrl = imguri.toString();
        userid = FirebaseAuth.getInstance().getUid();
        data.put("Img", eUrl);
        data.put("Title", eTitle);
        data.put("Location", eLocation);
        data.put("Short Description", eShortDesc);
        data.put("id", userid);
        db.collection("Item").add(data);
        Toast.makeText(this, "Successfully saved your item", Toast.LENGTH_SHORT).show();
    }


}
