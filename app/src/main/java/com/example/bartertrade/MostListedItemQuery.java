 package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

 public class MostListedItemQuery extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_listed_item_query);

        db = FirebaseFirestore.getInstance();

        db.collection("Item").orderBy("CategoryId", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    RadioGroup ll = new RadioGroup(MostListedItemQuery.this);
                    ll.setOrientation(LinearLayout.VERTICAL);

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d(TAG, document.getId() + " => " + document.getData());
                        RadioButton rdbtn = new RadioButton(MostListedItemQuery.this);
                        rdbtn.setId(View.generateViewId());
                        rdbtn.setTag(document.getId());
                        rdbtn.setText(document.getString("Category")  );
                        ll.addView(rdbtn);
                    }
                    ((ViewGroup) findViewById(R.id.radioGroup)).addView(ll);
                }
            }
        });
    }
}
