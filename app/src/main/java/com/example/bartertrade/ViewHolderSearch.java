package com.example.bartertrade;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderSearch extends RecyclerView.ViewHolder {

    public TextView mTitle, mLocation, mShortDesc, mCategory;
    public ImageView mUrl;
    public View mView;

    public ViewHolderSearch(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });

        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

        //initialize views with results.adapter.xml
        mTitle = itemView.findViewById(R.id.tTitle);
        mLocation = itemView.findViewById(R.id.tLocation);
        mUrl = itemView.findViewById(R.id.imagelogo);
        mShortDesc = itemView.findViewById(R.id.tShortDec);
        mCategory = itemView.findViewById(R.id.tCategory);


    }


    private ViewHolderSearch.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolderSearch.ClickListener clickListener){
        mClickListener = clickListener;

    }
}

