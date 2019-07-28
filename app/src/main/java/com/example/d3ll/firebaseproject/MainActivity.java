package com.example.d3ll.firebaseproject;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d3ll.firebaseproject.Model_class.createAcc_pojo;
import com.example.d3ll.firebaseproject.fragment_class.registerFrag;
import com.example.d3ll.firebaseproject.handler_class.firebase_Interface;
import com.example.d3ll.firebaseproject.handler_class.firebase_handler;
import com.example.d3ll.firebaseproject.utility_class.AlertBuilder;
import com.example.d3ll.firebaseproject.utility_class.InternetConnection;
import com.example.d3ll.firebaseproject.utility_class.progressBuilder;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private Dialog dialog;
    private InternetConnection  connection;
    private AlertBuilder builder;
    private firebase_Interface anInterface;
    public static FragmentManager fragmentManager;
    public static progressBuilder progressBuilder;
    private Animation animation;

    private CallbackManager callbackManager;

    private FirebaseAuth mAuth;

    @BindView(R.id.AlreadyAccount) Button AlreadyAccount;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.createAcc)TextView createAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        fragmentManager=getSupportFragmentManager();
        connection=new InternetConnection(this);
        progressBuilder =new progressBuilder(this);
        animation = AnimationUtils.loadAnimation(this,R.anim.slide_left);
        anInterface=new firebase_handler();
        dialog=new Dialog(this);
        ButterKnife.bind(this);
        createDialog();
        userSignIn(anInterface.userSignIn());
    }
    private void userSignIn(Boolean aBoolean)
    {
        if(aBoolean)
        {
            startActivity(new Intent(MainActivity.this, HolderPage.class));
        }
        else
        {
            builder=new AlertBuilder(this,"Welcome to my App","If You are new create your Account or Already user login to my app");
            builder.alertCreater("Ok",null,"",null);
        }

    }

    private void createDialog()
    {
        EditText user_name,password;
        //view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_popup,null,false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.custom_popup);
        Button Login=(Button)dialog.findViewById(R.id.Login);
        user_name=(EditText)dialog.findViewById(R.id.user_email);
        password=dialog.findViewById(R.id.user_password);
        FrameLayout errorContent1=(FrameLayout)dialog.findViewById(R.id.error_text1);
        TextView textViewList1=(TextView) dialog.findViewById(R.id.text_error1);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection.check())
                {
                    if (!password.toString().equals("")) {
                        if (!user_name.toString().equals("")) {

                            progressBuilder.create_Dialog("Please Wait...", "while your account is creating");
                            anInterface.LoginAccount(user_name.getText().toString(), password.getText().toString(), MainActivity.this);

                        } else {
                            errorContent1.startAnimation(animation);
                            textViewList1.setText("Fields Required");

                        }
                    } else {
                        errorContent1.startAnimation(animation);
                        textViewList1.setText("Fields Required");

                    }
                }
                else
                {

                    errorContent1.startAnimation(animation);
                    textViewList1.setText("No Internet");                 }
            }
        });
    }




    @OnClick(R.id.AlreadyAccount)
    public void AlreadyAccount()
    {
        if(connection.check())
        {
            dialog.show();
        }
        else
        {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.firstSheet), "Check Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("CHECK IT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertBuilder();
                        }
                    });
            View view = snackbar.getView();
            snackbar.setActionTextColor(Color.RED);
            final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);

            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            snackbar.show();
        }
    }

    @OnClick(R.id.createAcc)
    public void createAcc()
    {
        if(connection.check())
        {
            fragmentManager.beginTransaction().add(R.id.firstSheet,new registerFrag(MainActivity.this),"form Fragment")
                    //   .setCustomAnimations(R.anim.slide_left,0)
                    .addToBackStack(new MainActivity().getClass().getName())
                    .commit();
        }
        else
        {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.firstSheet), "Check Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("CHECK IT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertBuilder();
                        }
                    });
            View view = snackbar.getView();
            snackbar.setActionTextColor(Color.RED);
            final TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            snackbar.show();
        }
    }

    private void AlertBuilder()
    {
        builder=new AlertBuilder(this,"Your Priority","Connection not available.Please check your connection manually");
        builder.alertCreater("InternetData", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        }, "Wifi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));


            }
        });
    }

    @Override
    public void onBackPressed() {
        builder=new AlertBuilder(this,"Close App","Do you want to close the app");
        builder.alertCreater("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

    }

    @OnClick(R.id.login_button)
//    public void facebook_login()
//    {
//        // Initialize Facebook Login button
//        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,Arrays.asList("email","user_photos","public_profile"));
//         LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                 handleFacebookAccessToken(loginResult.getAccessToken());
//                progressBuilder.create_Dialog("","Please Wait...");
//                //FirebaseUser user = mAuth.getCurrentUser();
//
//            }
//
//            @Override
//            public void onCancel() {
//                Snackbar snackbar = Snackbar.make(findViewById(R.id.firstSheet), "Check Internet Connection", Snackbar.LENGTH_LONG);
//
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                Snackbar snackbar = Snackbar.make(findViewById(R.id.firstSheet), "Check Internet Connection", Snackbar.LENGTH_LONG);
//
//            }
//        });

//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    private void handleFacebookAccessToken(AccessToken token) {
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                     }
//                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                progressBuilder.close_dialog();
//   //             startActivity(new Intent(MainActivity.this, chatPage.class));
////                Toast.makeText(MainActivity.this,authResult.getUser().getPhotoUrl()+" email "+ authResult.getUser().getEmail()
////                        +" name"+ authResult.getUser().getDisplayName(),Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
}

