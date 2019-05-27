package com.capstone.blockchainand.ChannelListView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.blockchainand.MainTabMenuActivity;
import com.capstone.blockchainand.R;

import java.util.ArrayList;

import static com.capstone.blockchainand.Keys.DataKey.*;

public class ChannelRecyclerAdapter extends RecyclerView.Adapter<ChannelRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> channelList;

    public ChannelRecyclerAdapter(Context context, ArrayList<String> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.recycler_channel_view, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String channelTitle = channelList.get(i);

        if (channelTitle != null) {
            viewHolder.tvChannelTitle.setText(channelTitle);
            viewHolder.tvChannelTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTabMenu(channelTitle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChannelTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChannelTitle = itemView.findViewById(R.id.tvChannelTitle);
        }
    }

    private void goToTabMenu(String title) {
        Intent intent = new Intent(context, MainTabMenuActivity.class);

        if (title != null) {
            intent.putExtra(CHANELL_TITLE, title);
            context.startActivity(intent);
        }
    }
}
