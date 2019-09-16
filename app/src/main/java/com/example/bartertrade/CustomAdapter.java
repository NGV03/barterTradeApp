package com.example.bartertrade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolderSearch> implements Filterable {

    ListActivity listActivity;
    List<Model> modelList;
    //copy of list for search
    List<Model> modelListFull;
    Context context;
    FirebaseAuth firebaseAuth;
    public CustomAdapter(ListActivity listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;
        //copy list for modelList for search
        modelListFull = new ArrayList<>(modelList);

    }

    @NonNull
    @Override
    public ViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.results_adapter, parent, false);

        ViewHolderSearch viewHolderSearch = new ViewHolderSearch(itemView);
        //handle item clicks here
        viewHolderSearch.setOnClickListener(new ViewHolderSearch.ClickListener() {
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
    public void onBindViewHolder(@NonNull ViewHolderSearch holder, int i) {
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

    @Override
    public Filter getFilter() {
        return modelFilter;
    }

    private Filter modelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filteredList = new ArrayList<>();

            //check if search input field empty
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(modelListFull);
            }else{
                //if something typed in  the search view
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Model m : modelListFull){
                    if(m.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(m);
                    } else if (m.getLocation().toLowerCase().contains(filterPattern)){
                        filteredList.add(m);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            //returns filtered arraylist
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelList.clear();
            modelList.addAll((List) results.values);
            //notify adapter to refresh list
            notifyDataSetChanged();

        }
    };


}
