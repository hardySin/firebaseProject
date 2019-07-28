package com.example.d3ll.firebaseproject.fragment_class;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class fragmentSwipeAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String> fragmenttitle=new ArrayList<>();

    public fragmentSwipeAdapter(FragmentManager fm) {
        super(fm);
     }

    public void addFragment(Fragment fragment,String title)
    {
        fragmentList.add(fragment);
        fragmenttitle.add(title);
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return "Chat";
    }

    @Override
    public int getCount()
    {
        return 5;
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0) {
            return new Chat();
        } else if (position == 1){
            return new Chat();
        }
        else if (position == 2){
            return new Chat();
        }
        else if (position == 3){
            return new Chat();
        }
        else {
            return new Chat();
        }
    }
}
