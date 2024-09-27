package com.example.tatil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatil.CityActivity;
import com.example.tatil.Classes.City;
import com.example.tatil.Classes.Comment;
import com.example.tatil.Classes.Post;
import com.example.tatil.Classes.User;
import com.example.tatil.CommentActivity;
import com.example.tatil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CardHolder> {

    private List<City> cityList;
    private Context mContext;

    public CityAdapter(List<City> cityList, Context mContext) {
        this.cityList = cityList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item,parent,false);

        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {


        City city = cityList.get(position);

        if (city.getImageurl().equals("istanbul")){
            Picasso.get().load(city.getImageurl()).placeholder(R.drawable.istanbul).into(holder.img_city_image);
            holder.tv_city_name.setText(city.getName());
        }else if (city.getImageurl().equals("ankara")){
            Picasso.get().load(city.getImageurl()).placeholder(R.drawable.ankara).into(holder.img_city_image);
            holder.tv_city_name.setText(city.getName());
        }else if (city.getImageurl().equals("izmir")){
            Picasso.get().load(city.getImageurl()).placeholder(R.drawable.izmir).into(holder.img_city_image);
            holder.tv_city_name.setText(city.getName());
        }else if (city.getImageurl().equals("adana")){
            Picasso.get().load(city.getImageurl()).placeholder(R.drawable.adana).into(holder.img_city_image);
            holder.tv_city_name.setText(city.getName());
        }else if (city.getImageurl().equals("antep")){
            Picasso.get().load(city.getImageurl()).placeholder(R.drawable.antep).into(holder.img_city_image);
            holder.tv_city_name.setText(city.getName());
        }else if (city.getImageurl().equals("rize")){
            Picasso.get().load(city.getImageurl()).placeholder(R.drawable.rize).into(holder.img_city_image);
            holder.tv_city_name.setText(city.getName());
        }else if (city.getImageurl().equals("antalya")){
            Picasso.get().load(city.getImageurl()).placeholder(R.drawable.antalya).into(holder.img_city_image);
            holder.tv_city_name.setText(city.getName());
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CityActivity.class);
                intent.putExtra("cityName",city.getName());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder{

        private TextView tv_city_name;
        private ImageView img_city_image;
        private CardView cardView;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            tv_city_name = itemView.findViewById(R.id.city_name);
            img_city_image = itemView.findViewById(R.id.city_image);
            cardView = itemView.findViewById(R.id.city_card);
        }
    }
}
