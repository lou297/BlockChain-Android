package com.capstone.blockchainand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.capstone.blockchainand.Keys.RequestServerUrl.*;
import static com.capstone.blockchainand.Keys.DataKey.*;

public class ServerSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLocalServer;
    private Button btnTokyoServer;
    private Button btnOsakaServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_select);
        initView();
    }

    private void initView() {
        btnLocalServer = findViewById(R.id.btnLocalServer);
        btnTokyoServer = findViewById(R.id.btnTokyoServer);
        btnOsakaServer = findViewById(R.id.btnOsakaServer);

        btnLocalServer.setOnClickListener(this);
        btnTokyoServer.setOnClickListener(this);
        btnOsakaServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLocalServer:
                handOverServerUrl(LOCAL_SERVER);
                break;
            case R.id.btnTokyoServer:
                handOverServerUrl(TOKYO_SERVER);
                break;
            case R.id.btnOsakaServer:
                handOverServerUrl(OSAKA_SERVER);
                break;
        }
    }

    private void handOverServerUrl(String ServerUrl) {
        if(ServerUrl != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(SERVER_URL,ServerUrl);
            startActivity(intent);
        }
    }
}
