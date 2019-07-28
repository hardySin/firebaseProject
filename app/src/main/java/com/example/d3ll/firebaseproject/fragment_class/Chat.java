package com.example.d3ll.firebaseproject.fragment_class;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.d3ll.firebaseproject.HolderPage;
import com.example.d3ll.firebaseproject.R;
import com.example.d3ll.firebaseproject.chatPage;
import com.example.d3ll.firebaseproject.utility_class.AlertBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Chat extends Fragment  {
    private Context context;
    private Unbinder unbinder;
    private int nextFragment;
    private ViewPager viewPager;
    @BindView(R.id.toolbar1)
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chat_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
//        Toast.makeText(context,"check context :"+context+" : getcontext" + getContext()+": (Activity) getContext()"+(Activity) getContext()+"; this"+this+":HolderPage.mInstance"+HolderPage.mInstance,Toast.LENGTH_LONG).show();

        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setTitle("Chat");
        viewPager = (ViewPager) HolderPage.mInstance.findViewById(R.id.viewpager);
        nextFragment = viewPager.getCurrentItem() + 1;


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        inflater.inflate(R.menu.setting_menu, menu);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                viewPager.setCurrentItem(nextFragment);

        }
        return super.onOptionsItemSelected(item);
    }



 }
