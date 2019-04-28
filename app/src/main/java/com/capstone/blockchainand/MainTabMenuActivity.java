package com.capstone.blockchainand;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.blockchainand.MainTabMenu.MainViewPagerAdapter;

import static com.capstone.blockchainand.Keys.DataKey.ChannelTitle;

public class MainTabMenuActivity extends AppCompatActivity {
    private TextView tvChannelTitle;
    private TabLayout MainMenuTabLayout;
    private ViewPager MainMenuViewPager;
    private FloatingActionButton fabMenuButton;

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
        fabMenuButton = findViewById(R.id.fabMenuButton);

        fabMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainTabMenuActivity.this, "버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTabMenu(FragmentManager FM) {
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

}
