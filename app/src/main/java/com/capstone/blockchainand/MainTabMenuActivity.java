package com.capstone.blockchainand;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.blockchainand.FloatingMenuActivity.CreateActivity;
import com.capstone.blockchainand.FloatingMenuActivity.DonateActivity;
import com.capstone.blockchainand.MainTabMenu.ViewPagerAdapter.MainViewPagerAdapter;

import static com.capstone.blockchainand.Keys.DataKey.ChannelTitle;

public class MainTabMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvChannelTitle;
    private TabLayout MainMenuTabLayout;
    private ViewPager MainMenuViewPager;

    //Floating Anim
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fabMenuButton1, fabMenuButton3, fabMenuButton2;

    private String mChannelTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_menu);

        initView();
    }

    private void initView() {

        loadView();

        Intent intent = getIntent();
        loadTitle(intent);

        FragmentManager FM = getSupportFragmentManager();
        setTabMenu(FM);
    }

    private void loadView() {
        //객체 불러오기
        tvChannelTitle = findViewById(R.id.tvTopChannelTitle);
        MainMenuTabLayout = findViewById(R.id.MainMenuTabLayout);
        MainMenuViewPager = findViewById(R.id.MainMenuViewPager);
        fabMenuButton1 = findViewById(R.id.fabMenuButton1);
        fabMenuButton2 = findViewById(R.id.fabMenuButton2);
        fabMenuButton3 = findViewById(R.id.fabMenuButton3);

        fabMenuButton1.setOnClickListener(this);
        fabMenuButton2.setOnClickListener(this);
        fabMenuButton3.setOnClickListener(this);

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
    }

    private void setTabMenu(FragmentManager FM) {
        //TabLayout과 ViewPager 합치기
        MainMenuTabLayout.addTab(MainMenuTabLayout.newTab().setText(getString(R.string.details_of_usage)));
        MainMenuTabLayout.addTab(MainMenuTabLayout.newTab().setText(getString(R.string.asset_status)));
        MainMenuTabLayout.addTab(MainMenuTabLayout.newTab().setText(getString(R.string.group_info)));

        if(FM != null) {
            MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(FM);

            MainMenuViewPager.setAdapter(mainViewPagerAdapter);

            MainMenuViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(MainMenuTabLayout));
        }
    }

    private void loadTitle(Intent intent) {
        //MainActivity의 리스트에서 선택된 채널이름 불러오기.
        if(intent != null) {
            mChannelTitle = intent.getStringExtra(ChannelTitle);

            if(mChannelTitle != null) {
                tvChannelTitle.setText(mChannelTitle);
            } else {
                tvChannelTitle.setText(getString(R.string.no_infomation));
            }
        }
    }

    private void loadChannelInfo(String Title) {
        //선택된 채널에 대한 정보 불러오기

    }

    private void animFloatingBut() {
        if (isFabOpen) {
            fabMenuButton3.startAnimation(fab_close);
            fabMenuButton2.startAnimation(fab_close);
            fabMenuButton3.setClickable(false);
            fabMenuButton2.setClickable(false);
            isFabOpen = false;
        } else {
            fabMenuButton3.startAnimation(fab_open);
            fabMenuButton2.startAnimation(fab_open);
            fabMenuButton3.setClickable(true);
            fabMenuButton2.setClickable(true);
            isFabOpen = true;
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabMenuButton1:
                animFloatingBut();
                break;
            case R.id.fabMenuButton2:
                animFloatingBut();
                Intent donateIntent = new Intent(this, DonateActivity.class);
                donateIntent.putExtra(ChannelTitle, mChannelTitle);
                break;
            case R.id.fabMenuButton3:
                animFloatingBut();
                Intent createIntent = new Intent(this, CreateActivity.class);
                createIntent.putExtra(ChannelTitle, mChannelTitle);
                break;
        }
    }



    //fragment간 통신
    public String returnChannelTitle() {
        return mChannelTitle;
    }
}
