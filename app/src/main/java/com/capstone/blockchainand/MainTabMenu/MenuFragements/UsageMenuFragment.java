package com.capstone.blockchainand.MainTabMenu.MenuFragements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.capstone.blockchainand.FloatingMenuActivity.CreateActivity;
import com.capstone.blockchainand.MainTabMenuActivity;
import com.capstone.blockchainand.R;
import com.capstone.blockchainand.UsageListView.UsageData;
import com.capstone.blockchainand.UsageListView.UsageRecyclerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.requestQueue;
import static com.capstone.blockchainand.Keys.DataKey.CHANELL_TITLE;
import static com.capstone.blockchainand.Keys.RequestParamsKey.CHANNEL_NAME;
import static com.capstone.blockchainand.MainActivity.LOAD_SERVER_URL;
import static com.capstone.blockchainand.Keys.RequestServerUrl.*;

public class UsageMenuFragment extends Fragment {
    private RecyclerView rvUsageList;
    private Button btnMakeGroup;

    private MainTabMenuActivity mActivity;
    private String mChannelTitle;

    @Override
    public void onAttach(Context context) {
        mActivity = (MainTabMenuActivity) getActivity();
        mChannelTitle = mActivity.returnChannelTitle();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if(mActivity != null) {
            mActivity = null;
        }
        super.onDetach();
    }

    @Override
    public void onResume() {
        sendUsageRequest();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_usage_menu, container, false);
        rvUsageList = viewGroup.findViewById(R.id.rvUsageList);
        btnMakeGroup = viewGroup.findViewById(R.id.btnMakeGroup);

        btnMakeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.goToMakeGroupActivity();
            }
        });
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        sendUsageRequest();
        super.onActivityCreated(savedInstanceState);
    }



    private void sendUsageRequest() {
        String url = LOAD_SERVER_URL + LEDGER_REQUEST;

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
                        if(mActivity != null) {
                            Toast.makeText(mActivity, "사용 내역 (channels/ledger)에서 에러 발생"+ error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(CHANNEL_NAME, mChannelTitle);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        Log.d("Usage Result", response);
        Gson gson = new Gson();
        UsageData[] usageResult = gson.fromJson(response, UsageData[].class);
        ArrayList<UsageData> usageList = new ArrayList<>(Arrays.asList(usageResult));

        setRecyclerView(usageList);
    }

    private void setRecyclerView(ArrayList<UsageData> usageList) {
        if(usageList != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
            rvUsageList.setLayoutManager(layoutManager);

            if(rvUsageList.getItemDecorationCount() == 0) {
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mActivity, ((LinearLayoutManager) layoutManager).getOrientation());
                rvUsageList.addItemDecoration(dividerItemDecoration);
            }
            UsageRecyclerAdapter adapter = new UsageRecyclerAdapter(mActivity, mChannelTitle, usageList);

            rvUsageList.setAdapter(adapter);
        }
    }
}
