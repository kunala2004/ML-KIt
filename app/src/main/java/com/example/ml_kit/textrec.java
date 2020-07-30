package com.example.ml_kit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class textrec extends AppCompatActivity {

    static final int REQUEST_CODE_CAPTURE = 1;
    private ImageView imageview;
    private Bitmap photo;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textdec);
        imageview = findViewById(R.id.imageView2);
        text=findViewById(R.id.text);
    }

    public void capture(View v) {
        dispatchTakePictureIntent();
    }


    private void textrecognition() {

        FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(photo);

        FirebaseVisionTextRecognizer detector= FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        final Task<FirebaseVisionText> result=detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText)
                    {
                        for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
                            String blockText = block.getText();
                            Toast.makeText(textrec.this,"Text Recognized : "+blockText,Toast.LENGTH_SHORT).show();
                            text.setText(blockText);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        Toast.makeText(textrec.this,"No Text Recognized",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == RESULT_OK) {

            photo = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(photo);
            textrecognition();

        }
        else if(requestCode==RESULT_CANCELED)
        {
            Toast.makeText(this,"No Image Taken",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Failed To Take Image", Toast.LENGTH_SHORT).show();
        }

    }
}



