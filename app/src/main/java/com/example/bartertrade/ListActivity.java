package com.example.bartertrade;

import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //layout manager for recycler view
    RecyclerView.LayoutManager layoutManager;

    //firestore instance
    FirebaseFirestore db;
    CustomAdapter adapter;
    ImageView imageView;


    @Override
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //initialize firestore
        db = FirebaseFirestore.getInstance();

        //initialize views
        mRecyclerView = findViewById(R.id.resultRecycle);
        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //show  data in recycler view
        showData();
    }

    private void showData() {

        db.collection("Item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        //called when data is retrieved
                        //show data
//
                        for(DocumentSnapshot doc: task.getResult()){

                            Model model = new Model(doc.getString("id"),
                                    doc.getString("Title"),
                                    doc.getString("Location"),
                                    doc.getString("Img"),
                                    doc.getString("Short Description"),
                                      doc.getString("Category")
                            );


                            modelList.add(model);
                        }

                        //adapter
                        adapter = new CustomAdapter(ListActivity.this, modelList);
                        //set adapter to recycler view
                        mRecyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //called when there  is any error while retrieving data
                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    //search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bottom_navigation, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<Model> newList =  new ArrayList<>();

        for(Model m: modelList){
            if(m.getTitle().toLowerCase().contains(userInput)){
                newList.add(m);
            }
        }
        adapter.updateList(newList);
        return true;
    }
}
