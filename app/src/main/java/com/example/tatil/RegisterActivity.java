package com.example.tatil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView loginUser;
    private Spinner adress;
    private ArrayList<String> adresses = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private DatabaseReference myRef;
    private FirebaseAuth myAuth;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);
        loginUser = findViewById(R.id.login_user);
        adress = findViewById(R.id.location);

        adresses.add("İstanbul");
        adresses.add("Ankara");
        adresses.add("İzmir");
        adresses.add("Adana");
        adresses.add("Antalya");
        adresses.add("Rize");
        adresses.add("Gaziantep");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, adresses);

        adress.setAdapter(arrayAdapter);

        myRef = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvUserName = username.getText().toString();
                String tvName = name.getText().toString();
                String tvEmail = email.getText().toString();
                String tvPassword = password.getText().toString();
                String tvlocation = adresses.get(adress.getSelectedItemPosition());
                System.out.println(tvlocation);

                if (TextUtils.isEmpty(tvUserName) ||TextUtils.isEmpty(tvlocation) ||TextUtils.isEmpty(tvPassword) || TextUtils.isEmpty(tvEmail) || TextUtils.isEmpty(tvName)){
                    Toast.makeText(RegisterActivity.this,"Please check all blanks!! ",Toast.LENGTH_SHORT).show();
                }else if (tvPassword.length() < 6){
                    Toast.makeText(RegisterActivity.this,"Password must be longer than 6 characters!!",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(tvName,tvUserName,tvPassword,tvEmail,tvlocation);
                }


            }
        });

    }

    private void registerUser(String name, String username, String password, String email,String location) {

        pd.setMessage("Please Wait !");
        pd.show();

        myAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String , Object> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("username",username);
                map.put("id",myAuth.getCurrentUser().getUid());
                map.put("adress",location);
                map.put("imageurl","default");
                map.put("mentor","default");

                myRef.child("Users").child(myAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this,"Please update your profile!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}