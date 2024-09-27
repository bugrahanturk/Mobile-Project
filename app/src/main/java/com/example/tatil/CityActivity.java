
package com.example.tatil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tatil.Adapter.CommentAdapter;
import com.example.tatil.Adapter.MentorAdapter;
import com.example.tatil.Classes.City;
import com.example.tatil.Classes.Comment;
import com.example.tatil.Classes.HashTag;
import com.example.tatil.Classes.Post;
import com.example.tatil.Classes.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {

    private String city_name;
    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private MentorAdapter mentorAdapter;
    private List<User> userList;
    private List<HashTag> hashTagList;
    private List<Post> postList;
    private TextView tv;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        recyclerView = findViewById(R.id.rv_mentors);
        tv = findViewById(R.id.tv_city_mentors);
        city_name = getIntent().getStringExtra("cityName");;


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        hashTagList = new ArrayList<>();
        postList = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot datasnapshot: snapshot.getChildren()) {
                    User user = datasnapshot.getValue(User.class);
                    if (user.getAdress().equalsIgnoreCase(city_name) && user.getMentor().equalsIgnoreCase("mentor")){
                        userList.add(user);
                    }
                    System.out.println(user);
                }
                mentorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mentorAdapter = new MentorAdapter(this,userList,city_name);
        recyclerView.setAdapter(mentorAdapter);

    }
}