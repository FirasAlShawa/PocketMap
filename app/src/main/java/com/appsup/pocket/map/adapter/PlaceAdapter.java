package com.appsup.pocket.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsup.pocket.map.R;
import com.appsup.pocket.map.roomdb.entity.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.Viewholder> {

    List<Place> places;
    Context context ;

    public PlaceAdapter(List<Place> places, Context context) {
        this.places = places;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_item_layout,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final Place place = places.get(position);
        holder.place_tv.setText(place.getTitle());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        //add the view here
        TextView place_tv;

        public Viewholder(@NonNull View itemView){
            super(itemView);
            //get the views here
            place_tv = itemView.findViewById(R.id.place_tv);
        }
    }

}
