package com.example.linjiaxin.twtnews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by linjiaxin on 2017/11/13.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private String[] title = {"天大要闻", "校园公告", "社团风采", "院系动态", "视点观察"};

    public FragmentAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MyFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}