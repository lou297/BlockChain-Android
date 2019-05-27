package com.capstone.blockchainand.FloatingMenuActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.capstone.blockchainand.R;

import java.util.HashMap;
import java.util.Map;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.requestQueue;
import static com.capstone.blockchainand.Keys.DataKey.CHANELL_TITLE;
import static com.capstone.blockchainand.Keys.RequestParamsKey.CHANNEL_NAME;
import static com.capstone.blockchainand.Keys.RequestParamsKey.ID;
import static com.capstone.blockchainand.Keys.RequestParamsKey.MONEY;
import static com.capstone.blockchainand.Keys.RequestParamsKey.NAME;

import static com.capstone.blockchainand.Keys.RequestServerUrl.*;
import static com.capstone.blockchainand.MainActivity.LOAD_SERVER_URL;
import static com.capstone.blockchainand.Keys.DataKey.*;

public class DonateActivity extends AppCompatActivity {
    private ImageView ivBackButton;

    private TextView tvDonateChannel;
    private TextView tvDonateId;
    private TextView tvDonateGroup;
    private EditText etMoney;

    private String mChannelTitle;
    private String mDonateId;
    private String mDonateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        initView();
        Intent intent = getIntent();
        loadDonateInfo(intent);
        parsingId(mDonateId);
    }

    private void initView() {
        getSupportActionBar().hide();

        ivBackButton = findViewById(R.id.ivBackButton);

        tvDonateChannel = findViewById(R.id.tvDonateChannel);
        tvDonateId = findViewById(R.id.tvDonateId);
        tvDonateGroup = findViewById(R.id.tvDonateGroup);
        etMoney = findViewById(R.id.etMoney);

        Button btnDonate = findViewById(R.id.btnDonate);
        Button btnCancel = findViewById(R.id.btnCancel);

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBlank()) {
                    Log.d("Money",etMoney.getText().toString());
                    Log.d("DonateId",mDonateId);
                    Log.d("DonateChannel", mChannelTitle);
                    sendDonateRequest();
                } else {
                    Toast.makeText(DonateActivity.this, "빈 칸이 존재합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadDonateInfo(Intent intent) {
        if(intent != null) {
            mChannelTitle = intent.getStringExtra(CHANELL_TITLE);
            mDonateId = intent.getStringExtra(DONATE_ID);
            mDonateGroup = intent.getStringExtra(DONATE_GROUP);

            tvDonateChannel.setText(mChannelTitle);
            tvDonateId.setText(mDonateId);
            tvDonateGroup.setText(mDonateGroup);
        }
    }

    private void parsingId(String donateId) {
        mDonateId = donateId.substring(7);

    }

    private boolean checkBlank() {
        String strMoney = etMoney.getText().toString().trim();

        if (strMoney.length() == 0)
            return false;

        return true;
    }

    private void sendDonateRequest() {
        String url = LOAD_SERVER_URL + DONATE_REQUEST;

        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DonateActivity.this, "통신 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DonateActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CHANNEL_NAME, mChannelTitle);
                params.put(ID, mDonateId);
                params.put(MONEY, etMoney.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
