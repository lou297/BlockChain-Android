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
    ArrayList<AssetData> assetList;

    public AssetRecyclerAdapter(Context context, ArrayList<AssetData> assetList) {
        this.context = context;
        this.assetList = assetList;
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
        AssetData assetData = assetList.get(i);

        if(assetData != null) {
            viewHolder.tvAssetNumber.setText(assetData.getNumber());
            viewHolder.tvAssetPrevious.setText(assetData.getPrevious_hash());
            viewHolder.tvAssetData.setText(assetData.getData_hash());
        }
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAssetNumber;
        private TextView tvAssetPrevious;
        private TextView tvAssetData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAssetNumber = itemView.findViewById(R.id.tvAssetNumber);
            tvAssetPrevious = itemView.findViewById(R.id.tvAssetPrevious);
            tvAssetData = itemView.findViewById(R.id.tvAssetData);
        }
    }
}
