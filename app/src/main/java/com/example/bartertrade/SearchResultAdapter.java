package com.example.bartertrade;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mTitle;
    private ArrayList<String> mDesc;
    private ArrayList<String> mImage;
    private ArrayList<String> mVideo;
    private ArrayList<String> mCate;
    private Context context;


    public SearchResultAdapter(ArrayList<String> mTitle, ArrayList<String> mDesc, ArrayList<String> mImage, ArrayList<String> mCate) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mImage = mImage;
        this.mCate = mCate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.results_adapter, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.title.setText(mTitle.get(i));
        viewHolder.desc.setText(mDesc.get(i));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mTitle.get(i));
                Toast.makeText(context, mTitle.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DeatiledItemActivity.class);
                intent.putExtra("image_url", mImage.get(i));
                intent.putExtra("image_title", mTitle.get(i));
                intent.putExtra("image_desc", mDesc.get(i));
                context.startActivity(intent);
            }
        });

   }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView desc;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagelogo);
            title = itemView.findViewById(R.id.tTitle);
            desc = itemView.findViewById(R.id.tDesc);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
