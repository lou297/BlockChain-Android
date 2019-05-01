package com.capstone.blockchainand.FloatingMenuActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import static com.capstone.blockchainand.Keys.DataKey.ChannelTitle;
import static com.capstone.blockchainand.Keys.RequestParamsKey.CHANNEL_NAME;
import static com.capstone.blockchainand.Keys.RequestParamsKey.ID;
import static com.capstone.blockchainand.Keys.RequestParamsKey.MONEY;
import static com.capstone.blockchainand.Keys.RequestParamsKey.NAME;

public class DonateActivity extends AppCompatActivity {

    private EditText etId;
    private EditText etMoney;

    private String mChannelTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        initView();
        Intent intent = getIntent();
        loadChannelTitle(intent);
    }

    private void initView() {
        etId = findViewById(R.id.etId);
        etMoney = findViewById(R.id.etMoney);

        Button btnDonate = findViewById(R.id.btnDonate);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBlank()) {
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

    private void loadChannelTitle(Intent intent) {
        if(intent != null) {
            mChannelTitle = intent.getStringExtra(ChannelTitle);
        }
    }

    private boolean checkBlank() {
        String strId = etId.getText().toString().trim();
        String strMoney = etMoney.getText().toString().trim();

        if (strId.length() == 0)
            return false;

        if (strMoney.length() == 0)
            return false;

        return true;
    }

    private void sendDonateRequest() {
        String url = "http://192.168.0.6:8989/channels/donate";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
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
                params.put(ID, etId.getText().toString());
                params.put(MONEY, etMoney.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
