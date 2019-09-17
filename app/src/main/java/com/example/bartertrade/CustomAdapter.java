package com.example.bartertrade;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder>  {

    ListActivity listActivity;
    List<Model> modelList;




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

        ViewHolder viewHolderSearch = new ViewHolder(itemView);
        //handle item clicks here
        viewHolderSearch.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user clicks an item

                //show data on toast when clicking
                String title = modelList.get(position).getTitle();
                String location = modelList.get(position).getLocation();
                String  url = modelList.get(position).getUrl();
                String shortDesc = modelList.get(position).getShortDec();
                String category = modelList.get(position).getCategory();

                //pass this data to new activity
                Intent intent = new Intent(view.getContext(), DetailItemActivity.class);
                intent.putExtra("image", url );
                intent.putExtra("title",title);
                intent.putExtra("shortdesc",shortDesc);
                intent.putExtra("location",location);
                intent.putExtra("category",category);
                view.getContext().startActivity(intent);



            }

            @Override
            public void onItemLongClick(View view, int position) {
                //this will be called when user clicks long item
            }
        });

        return viewHolderSearch;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        //bind views /set data
        holder.mTitle.setText(modelList.get(i).getTitle());
        holder.mLocation.setText(modelList.get(i).getLocation());
        holder.mShortDesc.setText(modelList.get(i).getShortDec());
        holder.mCategory.setText(modelList.get(i).getCategory());
        Picasso.get().load(modelList.get(i).getUrl()).into(holder.mUrl);
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void updateList(List newList){
        modelList = new ArrayList<>();
        modelList.addAll(newList);
        notifyDataSetChanged();

    }




}