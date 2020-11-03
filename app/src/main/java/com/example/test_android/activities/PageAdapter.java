package com.example.test_android.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new userImageFragment();
           case 1:
               return new userAddressFragment();
           default:
               return null;
       }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
