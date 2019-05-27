package com.capstone.blockchainand;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.blockchainand.FloatingMenuActivity.CreateActivity;
import com.capstone.blockchainand.FloatingMenuActivity.DonateActivity;
import com.capstone.blockchainand.MainTabMenu.ViewPagerAdapter.MainViewPagerAdapter;

import static com.capstone.blockchainand.Keys.DataKey.CHANELL_TITLE;

public class MainTabMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvChannelTitle;
    private TabLayout MainMenuTabLayout;
    private ViewPager MainMenuViewPager;

    private ImageView ivBackButton;

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

        ivBackButton = findViewById(R.id.ivBackButton);

        ivBackButton.setOnClickListener(this);

    }

    private void setTabMenu(FragmentManager FM) {
        //TabLayout과 ViewPager 합치기
        MainMenuTabLayout.addTab(MainMenuTabLayout.newTab().setText(getString(R.string.details_of_usage)));
        MainMenuTabLayout.addTab(MainMenuTabLayout.newTab().setText(getString(R.string.asset_status)));
//        MainMenuTabLayout.addTab(MainMenuTabLayout.newTab().setText(getString(R.string.group_info)));
        MainMenuTabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.textBlue)));

        if(FM != null) {
            MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(FM);

            MainMenuViewPager.setAdapter(mainViewPagerAdapter);

            MainMenuViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(MainMenuTabLayout));
            MainMenuTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    MainMenuViewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    private void loadTitle(Intent intent) {
        //MainActivity의 리스트에서 선택된 채널이름 불러오기.
        if(intent != null) {
            mChannelTitle = intent.getStringExtra(CHANELL_TITLE);

            if(mChannelTitle != null) {
                tvChannelTitle.setText(mChannelTitle);
                getSupportActionBar().hide();
            } else {
                tvChannelTitle.setText(getString(R.string.no_infomation));
            }
        }
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivBackButton:
                finish();
                break;
        }
    }

    public void goToMakeGroupActivity() {
        Intent createIntent = new Intent(this, CreateActivity.class);
        createIntent.putExtra(CHANELL_TITLE, mChannelTitle);
        startActivity(createIntent);
    }



    //fragment간 통신
    public String returnChannelTitle() {
        return mChannelTitle;
    }
}
