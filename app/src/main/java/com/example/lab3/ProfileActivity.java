package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    ImageButton mImageButton;
    EditText et4,et5;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mImageButton=findViewById(R.id.imagebtn);
        et4=findViewById(R.id.et4);
        et5=findViewById(R.id.et5);
        Log.e(ACTIVITY_NAME,"onCreate()");
        Intent fromMain = getIntent();
        String emailFromMain=fromMain.getStringExtra("EMAIL");
        et5.setText(emailFromMain);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            };
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(ACTIVITY_NAME, "onStart()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(ACTIVITY_NAME, "onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(ACTIVITY_NAME, "onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(ACTIVITY_NAME, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(ACTIVITY_NAME, "onDestroy()");
    }
}