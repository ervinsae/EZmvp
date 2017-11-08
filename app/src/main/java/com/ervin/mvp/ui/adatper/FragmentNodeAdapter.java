package com.ervin.mvp.ui.adatper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ervin.mvp.ui.activity.AllNodeActivity;
import com.ervin.mvp.ui.fragment.AllNodeFragment;

import java.util.List;

/**
 * Created by Ervin on 2017/11/7.
 */

public class FragmentNodeAdapter extends FragmentPagerAdapter {

    private List<AllNodeFragment> fragments;
    public FragmentNodeAdapter(FragmentManager fm,List<AllNodeFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AllNodeActivity.typeStr[position];
    }
}
