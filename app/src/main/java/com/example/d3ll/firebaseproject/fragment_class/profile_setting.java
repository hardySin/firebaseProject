package com.example.d3ll.firebaseproject.fragment_class;


import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.Context;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.d3ll.firebaseproject.HolderPage;

import com.example.d3ll.firebaseproject.imageview;
import com.example.d3ll.firebaseproject.Model_class.settingPojo;
import com.example.d3ll.firebaseproject.R;


import com.example.d3ll.firebaseproject.utility_class.AlertBuilder;
import com.example.d3ll.firebaseproject.utility_class.GlideApp;
import com.example.d3ll.firebaseproject.utility_class.progressBuilder;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;


public class profile_setting extends Fragment {

    private Context context;
    private Unbinder unbinder;
    private int nextFragment;
    private List<settingPojo> setting_val;
    private ViewPager viewPager;
    private settingAdapter adapter;
    public static FragmentManager fragmentManager;
    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    @BindView(R.id.setting_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.mycoordinator)
    CoordinatorLayout CoordinatorLayout;


    @BindView(R.id.bottomModal)
    LinearLayout linearLayout;

    @BindView(R.id.enable)
    ConstraintLayout enable;

    @BindViews({R.id.thumbnail,R.id.takeImage})
    List<CircleImageView> imageViewList;




    public static DatabaseReference mdatabase;
    private StorageReference mStorageRef;
    Map<String,String> userProfile ;
    private AlertBuilder alertBuilder;
    public static BottomSheetBehavior sheetBehavior;
    private final int permission_code=2;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.fragment_profile_setting, container, false);
        unbinder= ButterKnife.bind(this,view);

        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setTitle("Profile");
        ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager=((AppCompatActivity) context).getSupportFragmentManager();
        viewPager=(ViewPager) HolderPage.mInstance.findViewById(R.id.viewpager);
        nextFragment = viewPager.getCurrentItem();
        getUserProfile();



        sheetBehavior = BottomSheetBehavior.from(linearLayout);
        SheetBehavior();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(nextFragment);
            }
        });

//        WebSettings settings=webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        webView.loadUrl("file:///android_asset/loader.html");

        return  view;
    }


    private void addValueToAdpater()
    {

        Iterator iterator=userProfile.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            switch (pair.getKey().toString())
            {
                case "Status":
                    settingPojo settingPojo=new settingPojo();
                    settingPojo.settitleKey(pair.getKey().toString());
                    settingPojo.setUser_info(pair.getValue().toString());
                    setting_val.add(settingPojo);

                    break;

                case "Name":
                    settingPojo settingPojo1=new settingPojo();
                    settingPojo1.settitleKey(pair.getKey().toString());
                    settingPojo1.setUser_info(pair.getValue().toString());
                    setting_val.add(settingPojo1);
                    break;

            }


        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        inflater.inflate(R.menu.logout, menu);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context=context;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                //                FirebaseAuth.getInstance().signOut();
                //                startActivity(new Intent(context, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.thumbnail,R.id.takeImage})
    public void ImageData(View v)
    {
        switch (v.getId())
        {
            case R.id.thumbnail:
               String downloadurl= userProfile.get("Profile_Image");
                Intent intent = new Intent(context, imageview.class);
                intent.putExtra("url",downloadurl);
                // create the transition animation - the images in the layouts
                // of both activities are defined with android:transitionName="robot"
                Pair[]pairs=new Pair[1];
                pairs[0]=new Pair<View,String>(imageViewList.get(0),"check");
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context,pairs);
                // start the new activity
                startActivity(intent, options.toBundle());


                break;
            case R.id.takeImage:
                String content[]={"Camera","Gallery","Cancel"};
                androidx.appcompat.app.AlertDialog.Builder  builder=new androidx.appcompat.app.AlertDialog.Builder(context);
                builder .setTitle("Choose the Image");
                builder.setCancelable(false);
                builder .setItems(content, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:

                                break;
                            case 1:
                                checkForPermission();
                                break;
                            case 2:
                                dialog.cancel();
                                break;
                        }
                    }
                });
                builder.create();
                builder.show();

        }
    }

    private void checkForPermission()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (context.checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }
            else
            {
                GallertIntent();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length >0) {
                    boolean readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (readAccepted  && writeAccepted ) {
                        GallertIntent();
                    }

                    else if(shouldShowRequestPermissionRationale(permissions[0]))
                    {
                        AlertBuilder builder=new AlertBuilder(context,"Allow Permission","You need to allow the permission to update your profile");
                        builder.alertCreater("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
                                }
                            }
                        }, "Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                    }
                    else
                    {
                        Snackbar snackbar = Snackbar.make(CoordinatorLayout, "Allow Permission", Snackbar.LENGTH_LONG)
                                .setAction("Setting", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        final Intent i = new Intent();
                                        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        i.addCategory(Intent.CATEGORY_DEFAULT);
                                        i.setData(Uri.parse("package:" + context.getPackageName()));
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //new activty open flag activity new task
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        //no histort avaiable where recent pages avaliable
                                        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                        // direct back to pervious activity or fragment
                                        context.startActivity(i);

                                    }
                                });
                        View view = snackbar.getView();
                        snackbar.setActionTextColor(Color.RED);
                        final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                        snackbar.show();
                    }

                }
                break;
        }
    }

    public void GallertIntent()
    {
//        Intent intent = new Intent();
//// Show only images, no videos or anything else
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//// Always show the chooser (if there are multiple options available)
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
            if(resultCode==RESULT_OK)
            {
                mStorageRef=FirebaseStorage.getInstance().getReference();
                Uri SelectedImage=data.getData();
                Toast.makeText(context,"uri :"+SelectedImage,Toast.LENGTH_LONG).show();
                CropImage.activity(SelectedImage)
                        .start(getContext(), this);
            }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progressBuilder builder = new progressBuilder(context);
                builder.create_Dialog("Image Uploading", "Please wait while we upload the profile Image");
                Uri resultUri = result.getUri();
                  byte[] thumb_byte = new byte[0];
                File file=new File(resultUri.getPath());
                try {
                    Bitmap bitmap= new Compressor((AppCompatActivity)context)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(file);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    thumb_byte= baos.toByteArray();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(context,"uri :"+"result uri "+resultUri,Toast.LENGTH_LONG).show();
                StorageReference mfilePath = mStorageRef.child("profile_images").child(StringRandom() + ".jpg");
                StorageReference thumbnail_path= mStorageRef.child("Thumbnail_Image").child(StringRandom() + ".jpg");

                UploadTask image= mfilePath.putFile(resultUri);

                byte[] finalThumb_byte = thumb_byte;


                UploadTask thumbnail = thumbnail_path.putBytes(finalThumb_byte);
                Task<Uri> thumbnailtask = thumbnail.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return thumbnail_path.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri thumbnailUrl = task.getResult();
                            Toast.makeText(context,"uri :"+"thumbnailUrl hahaha "+thumbnailUrl,Toast.LENGTH_LONG).show();

                            mdatabase.child("Thumbnail_Image").setValue(thumbnailUrl.toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context,"uri :"+"thumbnailUrl completed"+thumbnailUrl,Toast.LENGTH_LONG).show();
                                            Task<Uri> imageTask = image.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                @Override
                                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                    if (!task.isSuccessful()) {
                                                        throw task.getException();
                                                    }

                                                    // Continue with the task to get the download URL
                                                    return mfilePath.getDownloadUrl();
                                                }
                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    if (task.isSuccessful()) {
                                                        Uri imageUri = task.getResult();
                                                        Toast.makeText(getContext(),"download url "+ imageUri,Toast.LENGTH_LONG).show();

                                                        mdatabase.child("Profile_Image").setValue(imageUri.toString()).addOnCompleteListener(new OnCompleteListener<Void>()
                                                        {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task)
                                                            {
                                                                if (task.isSuccessful()) {
                                                                    builder.close_dialog();

                                                                    Snackbar snackbar =Snackbar.make(view.findViewById(R.id.mycoordinator),"Profile Update",Snackbar.LENGTH_LONG);
                                                                    View view = snackbar.getView();
                                                                    snackbar.setActionTextColor(Color.RED);
                                                                    final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                                                                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                                                                    snackbar.show();



                                                                    GlideApp.with(getContext())
                                                                            .load(thumbnailUrl)
                                                                              .into(imageViewList.get(0));


                                                                }
                                                            }
                                                        });

                                                    } else {
                                                        builder.close_dialog();
                                                        Snackbar snackbar =Snackbar.make(view.findViewById(R.id.mycoordinator),"Got error",Snackbar.LENGTH_LONG)

                                                                .setAction("Try Again", new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v)
                                                                    {
                                                                        checkForPermission();
                                                                    }
                                                                });
                                                        View view = snackbar.getView();
                                                        snackbar.setActionTextColor(Color.RED);
                                                        final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

                                                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                                                        snackbar.show();                        }
                                                }
                                            });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                        else
                        {
                           Toast.makeText(getContext(),"thumbnailUrl error",Toast.LENGTH_LONG).show();
                        }
                    }
                });



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();


            }
        }
    }



    public static String StringRandom()
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(20);

        for (int i = 0; i < 20; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return  sb.toString();
    }

    private void SheetBehavior()
    {

        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }



    public void getUserProfile() {

        userProfile = new HashMap<>();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //get the value from the firebase in iterate style
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String profile_image=dataSnapshot.child("Thumbnail_Image").getValue().toString();



                GlideApp.with(getContext())
                        .load(profile_image)
//                        .placeholder(R.drawable.men)
//                        .transition(DrawableTransitionOptions.withCrossFade(2000))
                        .into(imageViewList.get(0));

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    GenericTypeIndicator<HashMap<String, ClipData.Item>> t = new GenericTypeIndicator<HashMap<String, ClipData.Item>>() {};
                    HashMap<String, ClipData.Item> beans= (HashMap<String, ClipData.Item>) dataSnapshot.getValue();
                    Set s=beans.entrySet();
                    Iterator i=s.iterator();

                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        System.out.println("key :"+ me.getKey() +" "+ "value :"+me.getValue());
                        userProfile.put(me.getKey().toString(),me.getValue().toString());

                    }

                }

                setrecyclerView();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void setrecyclerView()
    {
        setting_val=new ArrayList<>();
        adapter=new settingAdapter(setting_val,view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new Divider(context, LinearLayoutManager.VERTICAL,18));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        addValueToAdpater();

    }


}
