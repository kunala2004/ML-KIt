package com.example.ml_kit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void textrec(View v)
    {
        Intent i = new Intent(getApplicationContext(),textrec.class);
        startActivity(i);
    }
    public void ld(View v)
    {
        Intent i = new Intent(getApplicationContext(),LandmarkDetection.class);
        startActivity(i);
    }
    public void bs(View v)
    {
        Intent i = new Intent(getApplicationContext(),BarcodeScanner.class);
        startActivity(i);
    }




}
