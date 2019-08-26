package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    private ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference itemsCollectionRef = db.collection("Item");
        Query itemsQuery = itemsCollectionRef.whereEqualTo("id",FirebaseAuth.getInstance().getCurrentUser().getUid());
        itemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Log.d("Data suc", "Got data");
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Log.d("Data suc", "onComplete: got a new note." );
                        Item item = document.toObject(Item.class);
                        title.add(item.getId());
                        desc.add(item.getShortDesc());
                        url.add(item.getUrl());
                        cate.add(item.getCate());
                        items.add(item);
                    }
                    intiRecyclerView();
                    Toast.makeText(searchActivity.this, items.get(0).getId(), Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("Data Error", "Can't getting data");
                }
            }
        });



//        db.collection("Item").whereEqualTo("Category","Vehicles").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful() && task.getResult() != null){
//                            Toast.makeText(searchActivity.this, "Getting data", Toast.LENGTH_SHORT).show();
//                            for (QueryDocumentSnapshot document : task.getResult()){
//                                Item item = document.toObject(Item.class);
//                                items.add(item);
//                            }
//                            String a = items.get(0).getId();
//                            Toast.makeText(searchActivity.this, "a", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(searchActivity.this, "Data failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//        db.collection("Item").document("JB4MSJCzoA5VF8JVeKue").get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()){
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            String id = documentSnapshot.getString("id");
//                            Toast.makeText(searchActivity.this, id, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        //title.add("test1");
        //desc.add("test1");
        //url.add("test1");
        //cate.add("test1");
       //getData();
    }


//    private void getData(){
//       // String userid = FirebaseAuth.getInstance().getUid();
//        Toast.makeText(this, "Getting data", Toast.LENGTH_SHORT).show();
//        db.collection("Item").document("").get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    String id = documentSnapshot.getString("Id");
//                    Toast.makeText(searchActivity.this, id, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//      db.collection("Item")
//             // .whereEqualTo("id",userid)
//              .get()
//              .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                  @Override
//                  public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                      if (!queryDocumentSnapshots.isEmpty()){
//                          Toast.makeText(searchActivity.this, "Loading data", Toast.LENGTH_SHORT).show();
//                          List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                          for (DocumentSnapshot d : list){
//                              Item t = d.toObject(Item.class);
//                              Toast.makeText(searchActivity.this, t.getId(), Toast.LENGTH_SHORT).show();
////                              title.add(t.getTitle());
////                              desc.add(t.getShortDesc());
////                              url.add(t.getUrl());
////                              cate.add(t.getCate());
////                              items.add(t);
////                              intiRecyclerView();
//                          }
//                      }
//                  }
//              });


//    }
    private void intiRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.resultRecycle);
        SearchResultAdapter adapter = new SearchResultAdapter(title,desc,url,cate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

