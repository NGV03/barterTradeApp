package com.example.bartertrade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class NotificationAdapter extends RecyclerView.Adapter<ViewHolderNotification> {
    NotificationActivity activity;
    List<notificationModel> modelList;
    public NotificationAdapter(NotificationActivity activity, List<notificationModel> modelList) {
        this.activity = activity;
        this.modelList = modelList;

    }

    @Nonnull
    @Override
    public ViewHolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_box,parent,false);
        ViewHolderNotification viewHolderNotification = new ViewHolderNotification(view);
        return viewHolderNotification;
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotification holder, int i) {
       holder.nTitle.setText(modelList.get(i).getNtitle());
       holder.nContext.setText(modelList.get(i).getNcontext());
       if (modelList.get(i).getNstatus() == true){
           holder.nStatus.setText("Unread");
       }else {
           holder.nStatus.setText("Read");
       }


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}

