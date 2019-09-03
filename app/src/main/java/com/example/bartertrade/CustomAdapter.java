package com.example.bartertrade;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.results_adapter, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item clicks here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user clicks an item

                //show data on toast when clicking
                String title = modelList.get(position).getTitle();
                String location = modelList.get(position).getLocation();
                String  url = modelList.get(position).getUrl();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when user clicks long item
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        //bind views /set data

        holder.mTitle.setText(modelList.get(i).getTitle());
        holder.mLocation.setText(modelList.get(i).getLocation());
        Picasso.get().load(modelList.get(i).getUrl()).into(holder.mUrl);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
