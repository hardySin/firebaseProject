package com.example.d3ll.firebaseproject.fragment_class;

import android.content.Context;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.d3ll.firebaseproject.HolderPage;
import com.example.d3ll.firebaseproject.chatPage;

public class fragment_swipe_page extends FragmentStatePagerAdapter {
    private int position;
    private Context context;

     public fragment_swipe_page(FragmentManager fm) {
        super(fm);

     }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                chatPage page=new chatPage();
                return page;
            case 1:
                profile_setting setting=new profile_setting();
                return setting;

                default:
                   return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
