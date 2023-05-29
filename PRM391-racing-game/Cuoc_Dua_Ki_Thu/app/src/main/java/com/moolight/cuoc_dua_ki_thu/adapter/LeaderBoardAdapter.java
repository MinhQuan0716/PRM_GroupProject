package com.moolight.cuoc_dua_ki_thu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moolight.cuoc_dua_ki_thu.R;
import com.moolight.cuoc_dua_ki_thu.dtos.Player;

import java.util.List;

public class LeaderBoardAdapter extends BaseAdapter {

    private Context context;
    private List<Player> players;

    public LeaderBoardAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflate the custom layout file
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_player, parent, false);

        // Get the views from the layout file and populate them with data
        TextView nameTextView = (TextView) itemView.findViewById(R.id.tvName);
        TextView scoreTextView = (TextView) itemView.findViewById(R.id.tvScore);

        Player player = players.get(position);
        nameTextView.setText(player.name);
        scoreTextView.setText(String.valueOf(player.score));

        return itemView;
    }
}