package com.example.d3ll.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d3ll.firebaseproject.fragment_class.Chat;
import com.example.d3ll.firebaseproject.fragment_class.Requests;
import com.example.d3ll.firebaseproject.fragment_class.fragmentSwipeAdapter;
import com.example.d3ll.firebaseproject.fragment_class.registerFrag;
import com.example.d3ll.firebaseproject.utility_class.AlertBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.security.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class chatPage extends Fragment {


    private Context context;
    private Unbinder unbinder;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.basebar)
    CoordinatorLayout coordinatorLayout;


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_chat_page, container, false);
        unbinder=ButterKnife.bind(this,view);
         bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        bottomNavigationView.setItemIconSize(50);
         ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.chatcontainer,new Chat(),null)
                .addToBackStack(null)
                .commit();
         return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener=new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
             switch (menuItem.getItemId())
             {
                 case R.id.chat:
                     ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.chatcontainer,new Chat(),null)
                             .addToBackStack(null)
                             .commit();
                     break;

                 case R.id.request:
                     ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.chatcontainer,new Requests(),null)
                             .addToBackStack(null)
                             .commit();
                     Toast.makeText(context,"Request",Toast.LENGTH_LONG).show();
                     break;

                 case R.id.status:
                     Toast.makeText(context,"status",Toast.LENGTH_LONG).show();
                     break;

             }
             return false;
         }
     };



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


     }

