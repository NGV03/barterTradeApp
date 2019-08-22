package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    private ArrayList<String> url = new ArrayList<>();
    private ArrayList<String> cate = new ArrayList<>();
    FirebaseFirestore db;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        title.add("test1");
        desc.add("test1");
        url.add("test1");
        cate.add("test1");
        intiRecyclerView();
       // getData();
    }

    private void getData(){
        String userid = FirebaseAuth.getInstance().getUid();
      task = db.collection("Item")
              .whereEqualTo("id",userid)
              .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful()){
                          List<Item> downloadItem = task.getResult().toObjects(Item.class);
                          for (Item item : downloadItem){
                              title.add(item.getTitle());
                              desc.add(item.getShortDesc());
                              url.add(item.getUrl());
                              cate.add(item.getCate());
                          }
                          intiRecyclerView();
                      }
                  }
              })
      ;


    }
    private void intiRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.resultRecycle);
        SearchResultAdapter adapter = new SearchResultAdapter(title,desc,url,cate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
