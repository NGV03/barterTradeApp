package com.example.bartertrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContactsActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);

                UserItem userItem = (UserItem) item;
                intent.putExtra("user", userItem.user);
                startActivity(intent);
            }
        });
        
        fetchUsers();
    }

    private void fetchUsers() {
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null){
                            Log.e("Test", e.getMessage(), e);
                            return;
                        }
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        adapter.clear();
                        for (DocumentSnapshot doc : docs){
                            User user = doc.toObject(User.class);
                            String uid = FirebaseAuth.getInstance().getUid();
                            if(user.getUserid().equals(uid))
                                continue;

                            adapter.add(new UserItem(user));
                            adapter.notifyDataSetChanged();
                        }


                    }
                });
    }

    private class UserItem extends Item <ViewHolder> {

        private final  User user;

        private UserItem (User user){
            this.user = user;
        }


        public void bind(@NonNull ViewHolder viewHolder, int position){
            TextView txtName = viewHolder.itemView.findViewById(R.id.textViewUser);
            ImageView imgPhoto = viewHolder.itemView.findViewById(R.id.imageView);

            txtName.setText(user.getName());

        }

        public int getLayout(){
            return R.layout.item_user;
        }


    }
}
