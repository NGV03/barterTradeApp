package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
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
import com.squareup.picasso.Picasso;

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


    //notification
    NotificationCompat.Builder notification;
    private static final int uniqueID  = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        db = FirebaseFirestore.getInstance();

        //initializing Storagereference
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");

        btnSave = (Button) findViewById(R.id.button_save);
        btnUpload = (Button) findViewById(R.id.button_upload);
        imageView = (ImageView) findViewById(R.id.iv_1);

        btnSave.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        initPlaces();
        setupPlaceAutoComplete();

        //notifcation
        notification = new NotificationCompat.Builder(this,"default");
        notification.setAutoCancel(false);

    }
    private void setupPlaceAutoComplete() {
        placesFragment = (AutocompleteSupportFragment)getSupportFragmentManager()
            .findFragmentById(R.id.autocomplete_fragment);
        placesFragment.setPlaceFields(placeFields);
        placesFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final TextView location = findViewById(R.id.etv_3);
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
            notification.setSmallIcon(R.drawable.logo);
            notification.setTicker("Title");
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle("MATA");
            notification.setContentText("Item uploaded  successfully");

            Intent i = new Intent(this, Home.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(uniqueID, notification.build());
            Fileuploader();


        }
    }

    //to get image file extension
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private  void Fileuploader(){
        if(imguri != null ) {

            StorageReference ref = mStorageRef.child(System.currentTimeMillis()+ "." +getFileExtension(imguri));

            ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UploadActivity.this, "Image uploaded successfully!", Toast.LENGTH_LONG).show();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            saveData(downloadUrl);
                            //Toast.makeText(UploadActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData()!= null){
            imguri = data.getData();
            Picasso.get().load(imguri).into(imageView);

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
        TextView location = findViewById(R.id.etv_3);

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

        if(eTitle.isEmpty()){
            Toast.makeText(this, "Please enter Title", Toast.LENGTH_SHORT).show();
        }else if(eShortDesc.isEmpty()){
            Toast.makeText(this, "Please enter Short description", Toast.LENGTH_LONG).show();
        }else if(eLocation.isEmpty()){
            Toast.makeText(this, "Please enter Location", Toast.LENGTH_SHORT).show();
        }else if(eUrl.isEmpty()){
            Toast.makeText(this, "Please choose an Image", Toast.LENGTH_SHORT).show();
        }else {

            db.collection("Item").add(data);
            Toast.makeText(this, "Successfully saved your item", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Home.class);
            startActivity(i);
        }
    }


    public void goHome(View view) {


    }
}
