package com.capstone.blockchainand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capstone.blockchainand.DataClass.ChannelList;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.requestQueue;
import static com.capstone.blockchainand.Keys.DataKey.*;
import static com.capstone.blockchainand.Keys.RequestServerUrl.*;

public class MainActivity extends AppCompatActivity {
    public static String LOAD_SERVER_URL = "http://34.85.90.33:8989";
    private ListView ChannelListView;
    private ArrayList mChannelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Intent intent = getIntent();
        startAppSetting(intent);
    }

    private void initView() {
        ChannelListView = findViewById(R.id.MainChannelListView);
    }

    private void startAppSetting(Intent intent) {
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(this);

        if(intent != null) {
            LOAD_SERVER_URL = intent.getStringExtra(SERVER_URL);
        } else {
            Toast.makeText(this, "서버를 읽지 못했습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        sendListRequest();
    }

    private void sendListRequest() {
        String url = LOAD_SERVER_URL + CHANNEL_LIST_REQUESTE;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );

        requestQueue.add(stringRequest);
    }
    
    private void handleResponse(String response) {
        Gson gson = new Gson();
        ChannelList channels = gson.fromJson(response, ChannelList.class);

        if(channels != null) {
            mChannelList = channels.getChannellist();
            loadList();
        }
    }

    private void loadList() {
        if(mChannelList != null ) {
            if(mChannelList.size() == 0)
                Toast.makeText(this, "채널이 0개 입니다.", Toast.LENGTH_SHORT).show();
            setListView(mChannelList);
        }
        else
            Toast.makeText(this, "통신에 성공했으나 목록을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
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
