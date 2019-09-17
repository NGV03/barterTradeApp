package com.example.bartertrade;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class DetailItemActivity extends AppCompatActivity {
    TextView mTitle;
    TextView mShortDesc;
    TextView mLocation;
    TextView mCategory;
    TextView mName ;
    ImageView mImage;
    Button mExchange;

    public FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details");

        //set back button to action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mImage = findViewById(R.id.detail_image);
        mTitle = findViewById(R.id.detail_title);
        mShortDesc = findViewById(R.id.detail_description);
        mCategory = findViewById(R.id.detail_category);
        mLocation = findViewById(R.id.detail_location);
        mExchange = findViewById(R.id.btnExchange);

        mExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(DetailItemActivity.this,  MessagesActivity.class);
                startActivity(k);
            }
        });

        //get data from intent
        String image = getIntent().getStringExtra("image");
        String title = getIntent().getStringExtra("title");
        String shortdesc = getIntent().getStringExtra("shortdesc");
        String location = getIntent().getStringExtra("location");
        String category = getIntent().getStringExtra("category");

        //get data from firebase
        db = FirebaseFirestore.getInstance();



        //set data to views
        Picasso.get().load(image).into(mImage);
        mTitle.setText(title);
        mShortDesc.setText(shortdesc);
        mLocation.setText(location);
        mCategory.setText(category);


    }

    //handle on back pressed to go to previous activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
