package com.example.tatil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatil.Classes.HashTag;
import com.example.tatil.Classes.Post;
import com.example.tatil.Classes.User;
import com.example.tatil.CommentActivity;
import com.example.tatil.Fragments.ProfileFragment;
import com.example.tatil.ProfileActivity;
import com.example.tatil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.ViewHolder>{

    private Context mContext;
    private List<User> userList;
    private String city_name;
    private int count;
    private List<Post> postsList;

    private FirebaseUser firebaseUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public MentorAdapter(Context mContext, List<User> userList,String city_name) {
        this.mContext = mContext;
        this.userList = userList;
        this.city_name = city_name;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mentor_item,parent,false);
        return new MentorAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        count = 0;

        Picasso.get().load(user.getImageurl()).into(holder.imageProfile);
        holder.username.setText(user.getUsername());

        holder.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("publisherId",user.getId());
                mContext.startActivity(intent);
            }
        });

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("publisherId",user.getId());
                mContext.startActivity(intent);
            }
        });

        noOfPosts(user.getId(),holder.noOfPost, user);


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageProfile;
        private TextView description;

        public TextView username;
        public TextView noOfPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.profile_img);

            description = itemView.findViewById(R.id.description);
            username = itemView.findViewById(R.id.username);
            noOfPost = itemView.findViewById(R.id.no_of_post);
        }
    }

    private void noOfPosts (String publisherId,TextView text,User user){

        database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot: snapshot.getChildren()) {
                    Post post= datasnapshot.getValue(Post.class);
                    if (post.getPublisher().equals(publisherId)){
                        count++;
                    }
                }
                text.setText(count+" posts "+"\n"+user.getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

