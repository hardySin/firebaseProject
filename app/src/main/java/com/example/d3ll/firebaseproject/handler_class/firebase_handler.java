package com.example.d3ll.firebaseproject.handler_class;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.d3ll.firebaseproject.HolderPage;
import com.example.d3ll.firebaseproject.MainActivity;
import com.example.d3ll.firebaseproject.Model_class.createAcc_pojo;
import com.example.d3ll.firebaseproject.Model_class.settingPojo;
import com.example.d3ll.firebaseproject.R;
import com.example.d3ll.firebaseproject.chatPage;
import com.example.d3ll.firebaseproject.fragment_class.registerFrag;
import com.example.d3ll.firebaseproject.utility_class.AlertBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class firebase_handler implements firebase_Interface
{
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private AlertBuilder alertBuilder;
    private DatabaseReference mdatabase;
    public static List<String> getData;

    @Override
    public void CreateEmailAndPassowrd(createAcc_pojo acc_pojo , Context context) {
        mAuth=FirebaseAuth.getInstance();
        String name=acc_pojo.getName();
        String email=acc_pojo.getEmail_id();
        String password=acc_pojo.getPassword();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().getCurrentUser().getUid();

                            mdatabase=FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            Map<String,String> map=new HashMap<>();
                            map.put("Name",name);
                            map.put("Status","hi i'm using this chat");
                            map.put("Profile_Image","defualt");
                            map.put("Thumbnail_Image","hi i'm using this chat");


                            mdatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        registerFrag.progressBuilder.close_dialog();

                                        context.startActivity(new Intent(context, HolderPage.class));

                                    }
                                }
                            });

                        } else {

                            try
                            {
                                throw task.getException();
                            }
                            // if user enters wrong email.
                            catch (FirebaseAuthWeakPasswordException weakPassword)
                            {

                            }
                            // if user enters wrong password.
                            catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                            {

                            }
                            catch (FirebaseAuthUserCollisionException existEmail)
                            {

                                alertBuilder=new AlertBuilder(context,"Already Exist","Email Already present.Please Enter the Unique Email ID");
                                alertBuilder.alertCreater("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        registerFrag.progressBuilder.close_dialog();
                                    }
                                },"",null);
                            }
                            catch (Exception e)
                            {

                            }
                        }

                    }
                });
    }

    @Override
    public boolean userSignIn() {
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            return true;
        }
        return false;
    }


    @Override
    public void LoginAccount(String email,String password , Context context) {

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            MainActivity.progressBuilder.close_dialog();
                            context.startActivity(new Intent(context, HolderPage.class));
                        } else
                        {
                            MainActivity.progressBuilder.close_dialog();
                            try
                            {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e)
                            {
                                alertBuilder=new AlertBuilder(context,"Invalid Credential","Please Check your Email and Password");
                                alertBuilder.alertCreater("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                },"",null);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        // ...
                    }
                });


    }


    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

}
