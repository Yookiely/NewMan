package com.example.asus.newman;

import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.asus.newman.R.id.line_tab;


public class MainActivity extends AppCompatActivity {
    private ViewPager myViewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        MyFragment a = new MyFragment(1);
        MyFragment b = new MyFragment(2);
        MyFragment c = new MyFragment(3);
        MyFragment d = new MyFragment(4);
        MyFragment e = new MyFragment(5);

        List<MyFragment> fragmentList = new ArrayList<>();
        fragmentList.add(a);
        fragmentList.add(b);
        fragmentList.add(c);
        fragmentList.add(d);
        fragmentList.add(e);
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentAdapter);
        tabLayout.setupWithViewPager(myViewPager);
        myViewPager.setOffscreenPageLimit(5);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_LONG).
                        show();
                break;
            default:
        }
        return true;
    }
}
