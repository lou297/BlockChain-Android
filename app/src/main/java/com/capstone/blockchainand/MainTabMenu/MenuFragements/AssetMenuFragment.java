package com.capstone.blockchainand.MainTabMenu.MenuFragements;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.capstone.blockchainand.AssetListView.AssetRecyclerAdapter;
import com.capstone.blockchainand.MainActivity;
import com.capstone.blockchainand.MainTabMenuActivity;
import com.capstone.blockchainand.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.requestQueue;
import static com.capstone.blockchainand.Keys.RequestParamsKey.CHANNEL_NAME;
import static com.capstone.blockchainand.Keys.RequestParamsKey.TXLIST;
import static com.capstone.blockchainand.MainActivity.LOAD_SERVER_URL;
import static com.capstone.blockchainand.Keys.RequestServerUrl.*;

public class AssetMenuFragment extends Fragment {

    private RecyclerView rvAssetList;

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
        sendAssetRequest();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_asset_menu, container, false);

        rvAssetList = viewGroup.findViewById(R.id.rvAssetList);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        sendAssetRequest();
        super.onActivityCreated(savedInstanceState);
    }

    private void sendAssetRequest() {
        String url = LOAD_SERVER_URL+BLOCK_REQUEST;

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
                            Toast.makeText(mActivity, "자산 현황 (channels/block)에서 에러 발생" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("", mChannelTitle);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {

//        Log.d("Asset Result", response);
        Log.d("Asset Result Channel", mChannelTitle);
        Gson gson = new Gson();
        ArrayList<ArrayList<String>> totalList = gson.fromJson(response, new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());

        ArrayList<ArrayList<String>> filterList = new ArrayList<ArrayList<String>>();
        for(ArrayList<String> iter : totalList) {
            if(iter.get(0).equals("donateMoney")) {
                filterList.add(iter);
            }
        }
//
        setRecyclerView(filterList);
    }

    private void setRecyclerView(ArrayList<ArrayList<String>> donateList) {
        if(donateList != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
            rvAssetList.setLayoutManager(layoutManager);

            if(rvAssetList.getItemDecorationCount() == 0){
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mActivity, ((LinearLayoutManager) layoutManager).getOrientation());
                rvAssetList.addItemDecoration(dividerItemDecoration);
            }


            AssetRecyclerAdapter adapter = new AssetRecyclerAdapter(mActivity, donateList);

            rvAssetList.setAdapter(adapter);
        }
    }
}
