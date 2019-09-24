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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MostActiveUsersQueryActivity extends AppCompatActivity {
    private static final String TAG = MostActiveUsersQueryActivity.class.getName();

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostactiveusers);

        db = FirebaseFirestore.getInstance();
        db.collection("users").orderBy("loginCount", Query.Direction.DESCENDING).limit(5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    RadioGroup ll = new RadioGroup(MostActiveUsersQueryActivity.this);
                    ll.setOrientation(LinearLayout.VERTICAL);

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d(TAG, document.getId() + " => " + document.getData());
                        RadioButton rdbtn = new RadioButton(MostActiveUsersQueryActivity.this);
                        rdbtn.setId(View.generateViewId());
                        rdbtn.setTag(document.getId());
                        rdbtn.setText(document.getString("name" ) + " - Number of logins:"+ " " + document.get("loginCount"));
                        ll.addView(rdbtn);
                    }
                    ((ViewGroup) findViewById(R.id.radioGroup)).addView(ll);
                }
            }
        });
    }
}
