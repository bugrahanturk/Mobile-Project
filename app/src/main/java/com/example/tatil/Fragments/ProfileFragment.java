package com.example.tatil.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tatil.Adapter.PhotoAdapter;
import com.example.tatil.Classes.Post;
import com.example.tatil.Classes.User;
import com.example.tatil.EditProfileActivity;
import com.example.tatil.MainActivity;
import com.example.tatil.PostActivity;
import com.example.tatil.R;
import com.example.tatil.StartingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ImageView options;
    private ImageView profile_image;
    private TextView username;
    private TextView adress;

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    public static List<Post> photoList;
    private ImageView expert;
    private TextView email;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase ;

    String profileId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String data = getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE).getString("profileId","none");

        System.out.println("gelen data"+data);

        if (data.equals("none")){
            profileId = firebaseUser.getUid();
        }else{
            profileId=data;
            getContext().getSharedPreferences("PROFILE",getContext().MODE_PRIVATE).edit().clear().apply();
        }

        expert = view.findViewById(R.id.verifed_user);

        email = view.findViewById(R.id.email_profile);
        options = view.findViewById(R.id.options);
        profile_image = view.findViewById(R.id.profile_photo);
        username = view.findViewById(R.id.username);
        adress = view.findViewById(R.id.adress);
        recyclerView = view.findViewById(R.id.recyler_view_posts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        photoList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(getContext(),photoList);
        recyclerView.setAdapter(photoAdapter);

        userInfo();
        userPhotos();

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });


        return view;
    }

    private void userPhotos() {

        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                photoList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);

                    if (post.getPublisher().equals(profileId)){
                        photoList.add(post);
                    }
                }
                if (photoList.size() > 2){
                    expert.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                    mDatabase.child(profileId).child("mentor").setValue("mentor");

                }else{
                    expert.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                }

                Collections.reverse(photoList);
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void userInfo() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(profileId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
                adress.setText(user.getAdress());
                email.setText(user.getEmail());

                if(user.getImageurl().equals("default")){
                   profile_image.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Picasso.get().load(user.getImageurl()).into(profile_image);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}