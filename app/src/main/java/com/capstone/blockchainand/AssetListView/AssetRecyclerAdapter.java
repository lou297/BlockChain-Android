package com.capstone.blockchainand.AssetListView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.blockchainand.R;

import java.util.ArrayList;

public class AssetRecyclerAdapter extends RecyclerView.Adapter<AssetRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<ArrayList<String>> DonateList;

    public AssetRecyclerAdapter(Context context, ArrayList<ArrayList<String>> DonateList) {
        this.context = context;
        this.DonateList = DonateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycler_asset_view, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
         ArrayList<String> Donate = DonateList.get(i);

        if(Donate != null) {
            viewHolder.tvUserId.setText("User2");
            viewHolder.tvDonateGroup.setText(Donate.get(1).substring(7));
            viewHolder.tvDonateMoney.setText(Donate.get(2));
        }
    }

    @Override
    public int getItemCount() {
        return DonateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserId;
        private TextView tvDonateGroup;
        private TextView tvDonateMoney;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserId = itemView.findViewById(R.id.tvDonateUserId);
            tvDonateGroup = itemView.findViewById(R.id.tvDonateGroup);
            tvDonateMoney = itemView.findViewById(R.id.tvDonateMoney);
        }
    }
}
