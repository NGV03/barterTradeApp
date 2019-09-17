package com.example.bartertrade;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import javax.annotation.Nonnull;


public class ViewHolderNotification extends RecyclerView.ViewHolder {
    public TextView nTitle, nContext,nStatus;
    public View nview;

    public ViewHolderNotification(@Nonnull View notification_box){
        super(notification_box);
        nview = notification_box;
        nTitle = notification_box.findViewById(R.id.nTitle);
        nContext = notification_box.findViewById(R.id.nCont);
        nStatus = notification_box.findViewById(R.id.nTime);



    }
}
