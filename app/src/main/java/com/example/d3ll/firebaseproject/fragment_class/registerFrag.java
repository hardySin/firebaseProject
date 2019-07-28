package com.example.d3ll.firebaseproject.fragment_class;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d3ll.firebaseproject.MainActivity;
import com.example.d3ll.firebaseproject.Model_class.createAcc_pojo;
import com.example.d3ll.firebaseproject.R;
import com.example.d3ll.firebaseproject.handler_class.firebase_Interface;
import com.example.d3ll.firebaseproject.handler_class.firebase_handler;
import com.example.d3ll.firebaseproject.utility_class.AlertBuilder;
import com.example.d3ll.firebaseproject.utility_class.InternetConnection;
import com.example.d3ll.firebaseproject.utility_class.progressBuilder;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class registerFrag extends Fragment {

    private Unbinder unbinder;
    private Animation animation;
    private InternetConnection connection;
    public static progressBuilder progressBuilder;

    @BindViews({R.id.user_name, R.id.email, R.id.password})
    public List<EditText> editTextList;
    @BindView(R.id.error_text)
    FrameLayout errorContent;
    @BindView(R.id.submit_form)
    Button submit;
    @BindViews({R.id.close, R.id.text_error})
    List<TextView> textViewList;

    private Context context;

    public registerFrag(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressBuilder = new progressBuilder(context);
        connection = new InternetConnection(context);
        animation = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        View view = inflater.inflate(R.layout.register_form, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.submit_form)
    public void submit_form() {
        String name = editTextList.get(0).getText().toString().trim();
        String email = editTextList.get(1).getText().toString().trim();
        String password = editTextList.get(2).getText().toString().trim();

        createAcc_pojo acc_pojo = new createAcc_pojo();

        if (checkVal()) {
            if (fieldVerify(email, password)) {
                progressBuilder.create_Dialog("Please Wait", "Your Account is Creating");

                if (connection.check()) {
                    acc_pojo.setName(name);
                    acc_pojo.setEmail_id(email);
                    acc_pojo.setPassword(password);
                    Toast.makeText(context, name + ":" + email + ":" + password, Toast.LENGTH_LONG).show();
                    firebase_Interface anInterface = new firebase_handler();
                    anInterface.CreateEmailAndPassowrd(acc_pojo, context);
                } else {
                    progressBuilder.close_dialog();
                    errorContent.startAnimation(animation);
                    textViewList.get(1).setText("No Internet Connection");
                }
            }
        }
    }

    @OnClick(R.id.close)
    public void textviews() {
        startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean checkVal() {
        String arr[] = {"Name Required", "Email Required", "Password Required"};
        boolean val = true;
        for (int i = 0; i < editTextList.size(); i++) {
            if (editTextList.get(i).getText().toString().equals("")) {
                errorContent.startAnimation(animation);
                textViewList.get(1).setText(arr[i]);
                val = false;
                break;
            }

        }
        return val;
    }

    public boolean fieldVerify(String email, String password) {
        boolean val = false;

        if (password.length() >= 7) {
            if (isValid(email)) {
                val = true;
            } else {
                errorContent.startAnimation(animation);
                textViewList.get(1).setText("Invalid Email ID");
            }
        } else {
            errorContent.startAnimation(animation);
            textViewList.get(1).setText("Password must be at least 7 character");
        }
        return val;
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        // it gives true
        return pat.matcher(email).matches();
    }

}


