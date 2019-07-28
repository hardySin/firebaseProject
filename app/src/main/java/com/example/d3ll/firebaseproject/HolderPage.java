package com.example.d3ll.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.d3ll.firebaseproject.broadcasts.connectionBroadcast;
import com.example.d3ll.firebaseproject.fragment_class.fragment_swipe_page;
import com.example.d3ll.firebaseproject.utility_class.AlertBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class
HolderPage extends AppCompatActivity {

    public static HolderPage mInstance = null;

    @BindView(R.id.viewpager)
     public ViewPager viewPager;
    //we cant declare as private or static

    public static connectionBroadcast broadcast;

    private fragment_swipe_page fragment_swipe_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder_page);
        broadcast=new connectionBroadcast();

        IntentFilter filter=new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
         registerReceiver(broadcast,filter);
         ButterKnife.bind(this);
        fragment_swipe_page=new fragment_swipe_page(getSupportFragmentManager());
        mInstance = this;

        viewPager.setAdapter(fragment_swipe_page);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

               // Toast.makeText(HolderPage.this,"onpageScrolled :"+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageSelected(int position) {

        //  Toast.makeText(HolderPage.this,"onPageSelected :"+position+viewPager.getCurrentItem(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(broadcast!=null)
        {
            unregisterReceiver(broadcast);
        }
          finishAffinity();
    }
}
