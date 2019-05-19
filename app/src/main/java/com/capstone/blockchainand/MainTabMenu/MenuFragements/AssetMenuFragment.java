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
import com.capstone.blockchainand.AssetListView.AssetData;
import com.capstone.blockchainand.AssetListView.AssetRecyclerAdapter;
import com.capstone.blockchainand.MainActivity;
import com.capstone.blockchainand.MainTabMenuActivity;
import com.capstone.blockchainand.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.requestQueue;
import static com.capstone.blockchainand.Keys.RequestParamsKey.CHANNEL_NAME;
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
                params.put(CHANNEL_NAME, mChannelTitle);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        Log.d("Asset Result", response);
        Gson gson = new Gson();
        AssetData[] assetResult = gson.fromJson(response, AssetData[].class);
        ArrayList<AssetData> assetList = new ArrayList<>(Arrays.asList(assetResult));

        setRecyclerView(assetList);
    }

    private void setRecyclerView(ArrayList<AssetData> assetList) {
        if(assetList != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
            rvAssetList.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mActivity, ((LinearLayoutManager) layoutManager).getOrientation());
            rvAssetList.addItemDecoration(dividerItemDecoration);

            AssetRecyclerAdapter adapter = new AssetRecyclerAdapter(mActivity, assetList);

            rvAssetList.setAdapter(adapter);
        }
    }
}
