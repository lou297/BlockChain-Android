package com.capstone.blockchainand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.capstone.blockchainand.Keys.DataKey.*;

public class MainActivity extends AppCompatActivity {
    private ListView ChannelListView;
    private ArrayList mChannelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadList();
    }

    private void initView() {
        ChannelListView = findViewById(R.id.MainChannelListView);
    }

    private void loadList() {
        mChannelList = new ArrayList<>();

        mChannelList.add("HanyangChnnel");
        mChannelList.add("MyChannel");
        mChannelList.add("TestChannel");

        setListView(mChannelList);
    }

    private void setListView(ArrayList<String> list) {
        if(list!= null) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    list
            );

            ChannelListView.setAdapter(arrayAdapter);

            ChannelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectChannelAction(position);
                }
            });
        }
    }

    private void selectChannelAction(int position) {
        Intent intent = new Intent(getApplicationContext(), MainTabMenuActivity.class);

        String title = (String) mChannelList.get(position);
        if(title != null) {
            intent.putExtra(ChannelTitle, title);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "잘못된 리스트 입니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
