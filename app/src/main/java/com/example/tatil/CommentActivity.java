package com.example.tatil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatil.Adapter.CommentAdapter;
import com.example.tatil.Classes.Comment;
import com.example.tatil.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    private EditText addComment;
    private ImageView imageProfile;
    private TextView post;

    private String postId;
    private String authorId;

    FirebaseUser firebaseUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        recyclerView = findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,commentList);

        recyclerView.setAdapter(commentAdapter);

        addComment = findViewById(R.id.add_comment);
        imageProfile = findViewById(R.id.image_profile);
        post = findViewById(R.id.post);

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        authorId = intent.getStringExtra("authorId");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        
        getUserImage();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(addComment.getText().toString())){
                    Toast.makeText(CommentActivity.this,"Please add comment!",Toast.LENGTH_SHORT).show();
                }else{
                    putComment();
                    addComment.setText("");
                }
            }
        });
        
        getComment();

    }

    private void getComment() {

        FirebaseDatabase.getInstance().getReference().child("Comments").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentList.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Comment comment = snapshot1.getValue(Comment.class);
                    commentList.add(comment);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void putComment() {
        HashMap<String , Object> map = new HashMap<>();
        map.put("comment",addComment.getText().toString());
        map.put("publisher",firebaseUser.getUid());

        database.getReference().child("Comments").child(postId).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CommentActivity.this,"Comment added!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CommentActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getUserImage() {

        database.getReference().child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user.getImageurl().equals("default")){
                    imageProfile.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Picasso.get().load(user.getImageurl()).into(imageProfile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}