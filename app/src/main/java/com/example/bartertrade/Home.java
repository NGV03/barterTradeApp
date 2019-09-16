package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Intent selected = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            Intent i = new Intent(Home.this,  Home.class);
                            startActivity(i);
                            break;
                        case R.id.nav_add:
                            Intent j = new Intent(Home.this,  UploadActivity.class);
                            startActivity(j);
                            break;
                        case R.id.nav_notification:
                            Intent k = new Intent(Home.this,  MessagesActivity.class);
                            startActivity(k);
                        case R.id.action_search:
                            Intent l = new Intent(Home.this,  ListActivity.class);
                            startActivity(l);

                            break;
                    }
                    getIntent();

                    return true;
                }
            };

    public void btn_postAd(View view) {
        Intent i = new Intent(Home.this, UploadActivity.class);
        startActivity(i);
    }
    public void goSearch(View view) {
        Intent i = new Intent(Home.this, ListActivity.class);
        startActivity(i);
    }
}
