package com.example.d3ll.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.d3ll.firebaseproject.utility_class.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class imageview extends AppCompatActivity {

//this concept of shared animation in android
    @BindView(R.id.downloadUrl)
    ImageView imageView1;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
         ButterKnife.bind(this);
         String value = getIntent().getExtras().getString("url");

                  GlideApp.with(this)
                     .load(value)
                     .into(imageView1);
      }
}
