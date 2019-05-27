package com.capstone.blockchainand.UsageListView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.blockchainand.FloatingMenuActivity.DonateActivity;
import com.capstone.blockchainand.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.capstone.blockchainand.Keys.DataKey.*;

public class UsageRecyclerAdapter extends RecyclerView.Adapter<UsageRecyclerAdapter.ViewHolder> {
    private Context context;
    private String channelTitle;
    private ArrayList<UsageData> usageList;

    public UsageRecyclerAdapter(Context context, String channelTitle, ArrayList<UsageData> usageList) {
        this.context = context;
        this.channelTitle = channelTitle;
        this.usageList = usageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycler_usage_view, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final UsageData usageData = usageList.get(i);

        if(usageData != null) {
            viewHolder.tvUsageId.setText(usageData.getKey().substring(7));
            viewHolder.tvUsageGroup.setText(usageData.getRecord().getCompanyname());
            viewHolder.tvUsageMoney.setText(String.valueOf(usageData.getRecord().getMoney()));

            viewHolder.btnUsageDonate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToDonateActivity(channelTitle, usageData.getKey(), usageData.getRecord().getCompanyname());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return usageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsageId;
        private TextView tvUsageGroup;
        private TextView tvUsageMoney;
        private TextView btnUsageDonate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsageId = itemView.findViewById(R.id.tvUsageId);
            tvUsageGroup = itemView.findViewById(R.id.tvUsageGroup);
            tvUsageMoney = itemView.findViewById(R.id.tvUsageMoney);
            btnUsageDonate = itemView.findViewById(R.id.btnDonate);
        }
    }

    private void goToDonateActivity(String channelTitle, String donateId, String donateGroup) {
        Intent intent = new Intent(context, DonateActivity.class);
        intent.putExtra(CHANELL_TITLE, channelTitle);
        intent.putExtra(DONATE_ID, donateId);
        intent.putExtra(DONATE_GROUP, donateGroup);
        context.startActivity(intent);
    }
}
