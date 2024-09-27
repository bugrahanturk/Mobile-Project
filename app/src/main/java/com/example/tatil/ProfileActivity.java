package com.example.tatil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.tatil.Fragments.HomeFragment;
import com.example.tatil.Fragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle intent = getIntent().getExtras();
        if (intent != null) {

            String profileId = intent.getString("publisherId");
            System.out.println(intent.getString("publisherId") + "publisher id");

            getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", profileId).apply();
        }

        selectorFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_profile , selectorFragment).commit();

    }
}