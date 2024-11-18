package com.example.tinycircuit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class WaitingForPlayersActivity extends AppCompatActivity {

    private TextView tvLobbyTitle, tvPlayersInGame, tvTimeRemaining, tvWaitingMessage;
    private LinearLayout scoreboardContainer;
    private Button btnStartGame;
    private int playersInGame = 1; // Start with one player
    private int maxPlayers = 4; // Max players for the game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_players);

        // Initialize UI components
        tvLobbyTitle = findViewById(R.id.tvLobbyTitle);
        tvPlayersInGame = findViewById(R.id.tvPlayersInGame);
        tvTimeRemaining = findViewById(R.id.tvTimeRemaining);
        tvWaitingMessage = findViewById(R.id.tvWaitingMessage);
        scoreboardContainer = findViewById(R.id.scoreboardContainer);
        btnStartGame = findViewById(R.id.btnStartGame);

        // Set initial values
        tvLobbyTitle.setText("Lobby: Game Room");
        updatePlayerCount();

        // Set a countdown for the time remaining
        tvTimeRemaining.setText("Time Remaining: 02:30");

        // Add a player to the scoreboard
        addPlayerToScoreboard("Player1", 50);

        // Check if "Start Game" button should be enabled
        checkStartGameAvailability();
    }

    // Method to add players to the scoreboard
    private void addPlayerToScoreboard(String playerName, int score) {
        LinearLayout playerRow = new LinearLayout(this);
        playerRow.setOrientation(LinearLayout.HORIZONTAL);
        playerRow.setPadding(8, 8, 8, 8);

        TextView playerNameText = new TextView(this);
        playerNameText.setText(playerName);
        playerNameText.setTextSize(14);
        playerNameText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        TextView playerScoreText = new TextView(this);
        playerScoreText.setText("Score: " + score);
        playerScoreText.setTextSize(14);

        playerRow.addView(playerNameText);
        playerRow.addView(playerScoreText);

        scoreboardContainer.addView(playerRow);
    }

    // Update the number of players in the game
    private void updatePlayerCount() {
        tvPlayersInGame.setText("Players in Game: " + playersInGame);
    }

    // Check if the "Start Game" button should be enabled
    private void checkStartGameAvailability() {
        if (playersInGame >= maxPlayers) {
            btnStartGame.setEnabled(true);
            tvWaitingMessage.setText("All players have joined. Game is starting soon!");
        } else {
            btnStartGame.setEnabled(false);
            tvWaitingMessage.setText("Waiting for players to join...");
        }
    }

    // This method should be called when a player joins the game
    public void playerJoined() {
        playersInGame++;
        updatePlayerCount();
        addPlayerToScoreboard("Player" + playersInGame, 0); // Add new player to scoreboard
        checkStartGameAvailability();
    }
}
