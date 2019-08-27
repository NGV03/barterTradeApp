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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Ref;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    public FirebaseFirestore db;
    private StorageReference mStorageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imguri;
    private String cate;
    private  String userid;
    private Button btnSave, btnUpload;
    private ImageView imageView;
    PlacesClient placesClient;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS);
    AutocompleteSupportFragment placesFragment;


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

        initPlaces();
        setupPlaceAutoComplete();



    }

    private void setupPlaceAutoComplete() {
        placesFragment = (AutocompleteSupportFragment)getSupportFragmentManager()
            .findFragmentById(R.id.autocomplete_fragment);
        placesFragment.setPlaceFields(placeFields);
        placesFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(UploadActivity.this, ""+ place.getAddress(),Toast.LENGTH_LONG).show();
                final EditText location = findViewById(R.id.etv_3);
                location.setText(place.getAddress());
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(UploadActivity.this, ""+ status.getStatusMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initPlaces() {
        Places.initialize(this, getString(R.string.places_api_key));
        placesClient = Places.createClient(this);
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


    private void getCate(){
        RadioButton vehicles = findViewById(R.id.vehicles);
        RadioButton furniture = findViewById(R.id.furniture);
        RadioButton electronics = findViewById(R.id.electronics);
        RadioButton clothing = findViewById(R.id.clothing);
        if (vehicles.isChecked()){
            cate = "Vehicles";
        }else if (furniture.isChecked()){
            cate = "Furniture";
        }else if (electronics.isChecked()){
            cate = "Electronics";
        }else if (clothing.isChecked()){
            cate = "Clothing";
        }else {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
        }
    }
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
        getCate();
        data.put("Img", eUrl);
        data.put("Title", eTitle);
        data.put("Location", eLocation);
        data.put("Short Description", eShortDesc);
        data.put("Category", cate   );
        data.put("id", userid);
        db.collection("Item").add(data);
        Toast.makeText(this, "Successfully saved your item", Toast.LENGTH_SHORT).show();
    }


}
