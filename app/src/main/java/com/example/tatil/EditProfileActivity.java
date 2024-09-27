package com.example.tatil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class EditProfileActivity extends AppCompatActivity {

    private Button btn_image_profile;
    private Button btn_exit;

    private Uri imageUri;
    private StorageTask uploadTask;
    private StorageReference storageReference;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_exit = findViewById(R.id.exit);
        btn_image_profile = findViewById(R.id.change_img);
        storageReference = FirebaseStorage.getInstance().getReference().child("Uploads");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btn_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().getSharedPreferences("LOCATION",getApplicationContext().MODE_PRIVATE).edit().clear().apply();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EditProfileActivity.this, StartingActivity.class));
            }
        });

    }

    private void uploadImage(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + ".jpeg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        String url = downloadUrl.toString();

                        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("imageurl").setValue(url);
                        pd.dismiss();
                        Toast.makeText(EditProfileActivity.this,"Profile photo changed!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditProfileActivity.this,"Upload Failed!!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this,"No image selected!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            uploadImage();

        }else{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }

    }
}