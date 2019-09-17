package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    List<notificationModel> modelList = new ArrayList<>();
    RecyclerView nRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    NotificationAdapter adapter;
    Boolean read = true;
    Boolean unread = false;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        db = FirebaseFirestore.getInstance();
        nRecyclerView = findViewById(R.id.notificationRecycle);
        nRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        nRecyclerView.setLayoutManager(layoutManager);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        notificationModel model = new notificationModel(uid,"Welcome to Mata","Welcome to Mata",false);
        modelList.add(model);
        adapter = new NotificationAdapter(NotificationActivity.this, modelList);
        nRecyclerView.setAdapter(adapter);
        getNotification();
    }

    private void getNotification(){
        db.collection("notifications")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult() != null){
                            for(DocumentSnapshot doc: task.getResult()){
                                if (doc.getBoolean("status") == true){
                                    notificationModel model = new notificationModel(doc.getString("id"),
                                            doc.getString("title"),
                                            doc.getString("context"),
                                            read
                                    );
                                    modelList.add(model);
                                }else{
                                    notificationModel model = new notificationModel(doc.getString("id"),
                                            doc.getString("title"),
                                            doc.getString("context"),
                                            unread
                                    );
                                    modelList.add(model);
                                }

                            }
                            //adapter
                            adapter = new NotificationAdapter(NotificationActivity.this, modelList);
                            nRecyclerView.setAdapter(adapter);
                        }else {
                            notificationModel model = new notificationModel(FirebaseAuth.getInstance().getUid(),"Welcome to Mata","Welcome to Mata",false);
                            modelList.add(model);
                            adapter = new NotificationAdapter(NotificationActivity.this, modelList);
                            nRecyclerView.setAdapter(adapter);
                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //called when there  is any error while retrieving data
                        Toast.makeText(NotificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
