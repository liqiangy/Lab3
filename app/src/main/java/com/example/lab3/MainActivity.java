package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    EditText et1,et2;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.et1);
        sharedpreferences=getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String myDefault=sharedpreferences.getString("Myemail","");
        et1.setText(myDefault);
        Button btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPause();
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                goToProfile.putExtra("EMAIL",et1.getText().toString());
                finish();
            }
        });

    }
    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String myEmail=et1.getText().toString();
        editor.putString("Myemail", myEmail);
        editor.commit();
    }
}