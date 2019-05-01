package com.capstone.blockchainand.MainTabMenu.MenuFragements;

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

import com.capstone.blockchainand.MainTabMenuActivity;
import com.capstone.blockchainand.R;

public class InfoMenuFragment extends Fragment {

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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_info_menu, container, false);
        tvResult = viewGroup.findViewById(R.id.tvResult);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        tvResult.append("그룹 정보..");
        super.onActivityCreated(savedInstanceState);
    }
}
