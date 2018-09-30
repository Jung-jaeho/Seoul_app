package uis.slider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class VPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> views;
    public VPagerAdapter(FragmentManager fm, ArrayList<Fragment> items) {
        super(fm);
        views = items;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }




}