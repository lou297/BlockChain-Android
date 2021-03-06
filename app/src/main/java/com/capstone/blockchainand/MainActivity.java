package com.capstone.blockchainand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capstone.blockchainand.ChannelListView.ChannelList;
import com.capstone.blockchainand.ChannelListView.ChannelRecyclerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.requestQueue;
import static com.capstone.blockchainand.Keys.DataKey.*;
import static com.capstone.blockchainand.Keys.RequestServerUrl.*;

public class MainActivity extends AppCompatActivity {
    public static String LOAD_SERVER_URL = "http://34.85.90.33:8989";
    private RecyclerView rvChannelList;
    private ArrayList<String> mChannelList;

    private boolean searchView = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Intent intent = getIntent();
        startAppSetting(intent);
    }

    @Override
    public void onBackPressed() {
        if(searchView) {
            loadList();
        } else {
            super.onBackPressed();
        }
    }


    private void initView() {
        rvChannelList = findViewById(R.id.MainChannelRecyclerView);
        TextView tvTitleBar = findViewById(R.id.tvTopChannelTitle);
        ImageView ivBackButton = findViewById(R.id.ivBackButton);
        final EditText etSearchChannel = findViewById(R.id.etSearchChannel);
        ImageView ivSearchButton = findViewById(R.id.ivSearchButton);

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Keyword = etSearchChannel.getText().toString().trim();
                if(Keyword.length()>=1) {
                    loadSearchList(Keyword);
                }
            }
        });

        getSupportActionBar().hide();
        tvTitleBar.setText(getString(R.string.main_activity_label));
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
                        Toast.makeText(MainActivity.this, "MainActivity 오류" + error.getMessage(), Toast.LENGTH_LONG).show();
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
            setRecyclerView(mChannelList);
            searchView = false;
        }
        else
            Toast.makeText(this, "통신에 성공했으나 목록을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
    }

    private void loadSearchList(String Keyword) {
        ArrayList<String> newChannelList = new ArrayList<>();
        for(String channel : mChannelList) {
            if(channel.contains(Keyword)) {
                newChannelList.add(channel);
            }
        }
        searchView = true;
        setRecyclerView(newChannelList);
    }

    private void setRecyclerView(ArrayList<String> list) {
        if(list!= null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvChannelList.setLayoutManager(layoutManager);

            ChannelRecyclerAdapter adapter = new ChannelRecyclerAdapter(this, list);
            rvChannelList.setAdapter(adapter);
        }
    }

    private void selectChannelAction(int position) {
        Intent intent = new Intent(getApplicationContext(), MainTabMenuActivity.class);

        String title = (String) mChannelList.get(position);
        if(title != null) {
            intent.putExtra(CHANELL_TITLE, title);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "잘못된 리스트 입니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
