package com.example.d3ll.firebaseproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class SaveImage extends AppCompatActivity implements View.OnClickListener{


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private static final int request_code= 1;
    private String currentphotoPath;
    private ImageView imageView;
    private Button button;
    private Button button3;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_image);
        imageView=(ImageView)findViewById(R.id.imageView);
        fragmentManager=this.getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        button=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button.setOnClickListener(this);

        button3.setOnClickListener(this);
    }

    private void thumbnailImageGet()
    {
        Intent intentImage=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentImage.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(intentImage,request_code);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

            if(requestCode== request_code && resultCode==RESULT_OK)
            {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button2:
                thumbnailImageGet();
                break;

            case R.id.button3:
                //dispatchTakePictureIntent();


                 break;

                default:
                    Toast.makeText(SaveImage.this,"default Working", Toast.LENGTH_LONG).show();
                    break;
        }
     }

     private File CreateFilePath() throws IOException
     {
         File image = null;
         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
             String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
             String image_name="JPEG_"+ timeStamp+ "_";
              File StorageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
             Log.i("Enviroment Directory", Environment.DIRECTORY_PICTURES);

             Log.i("StorageDir", String.valueOf(StorageDir));
               image=File.createTempFile(image_name,".jpg",StorageDir);

              currentphotoPath=image.getAbsolutePath();
             Log.i("imagePathCreate",image.toString() );
             Log.i("image absolute path", image.getAbsolutePath());


         }
         return  image;
     }

     private void dispatchTakePictureIntent()
     {
         Intent takePic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         if(takePic.resolveActivity(getPackageManager())!=null)
         {
                File photoFile=null;
             try {
                 photoFile=CreateFilePath();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             if(photoFile!=null)
             {
                 Uri photoImage_uri =FileProvider.getUriForFile(SaveImage.this,"com.example.android.fileprovider",photoFile);
                 takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoImage_uri);
                 startActivityForResult(takePic,request_code);
                 Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                 File f=new File(currentphotoPath);
                 Uri contentUri=Uri.fromFile(f);
                 mediaScanIntent.setData(contentUri);
                 this.sendBroadcast(mediaScanIntent );
             }
         }
     }

}
