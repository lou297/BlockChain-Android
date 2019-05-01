package com.capstone.blockchainand.MainTabMenu.MenuFragements;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.capstone.blockchainand.MainActivity;
import com.capstone.blockchainand.MainTabMenuActivity;
import com.capstone.blockchainand.R;

import static com.capstone.blockchainand.AppHelper.NetworkHelper.requestQueue;

public class AssetMenuFragment extends Fragment {

    private TextView tvResult;
    private MainTabMenuActivity mActivity;

    @Override
    public void onAttach(Context context) {
        mActivity = (MainTabMenuActivity) getActivity();
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

        tvResult = viewGroup.findViewById(R.id.tvResult);

        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        sendAssetRequest();
        super.onActivityCreated(savedInstanceState);
    }

    private void sendAssetRequest() {
        String url = "http://192.168.0.6:8989/channels/block";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(mActivity != null) {
                            Toast.makeText(mActivity, "통신 성공", Toast.LENGTH_SHORT).show();
                        }
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mActivity != null) {
                            Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

        );

        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        tvResult.append(response);
    }
}