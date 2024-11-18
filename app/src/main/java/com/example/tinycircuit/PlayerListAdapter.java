package com.example.tinycircuit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlayerListAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> playerList;

    // Constructor to initialize context and player list
    public PlayerListAdapter(Context context, ArrayList<String> playerList) {
        super(context, R.layout.item_player, playerList);
        this.context = context;
        this.playerList = playerList;
    }

    // Get view for each player in the list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        }

        // Set player name to the TextView
        TextView tvPlayerName = convertView.findViewById(R.id.tvPlayerName);
        tvPlayerName.setText(playerList.get(position));

        return convertView;
    }
}
