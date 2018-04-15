package com.example.asus.newman;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/11/10.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<MyFragment> fragmentList;
    private List<String> title = new ArrayList<>();

    public MyFragmentAdapter(FragmentManager fm, List<MyFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        title.add("新闻1");
        title.add("新闻2");
        title.add("新闻3");
        title.add("新闻4");
        title.add("新闻5");
    }

    @Override
    public Fragment getItem(int arg0) {

        return fragmentList.get(arg0);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
