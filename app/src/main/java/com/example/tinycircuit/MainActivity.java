package com.example.tinycircuit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvGameStatus;
    private TextView tvRemainingTime;
    private TextView tvHidingPlayers;
    private TextView tvDebugStatus;
    private HorizontalScrollView powerupScrollView;

    private int gameTimeRemaining = 60; // Example game duration
    private int hidingPlayers = 5; // Example initial player count
    private Handler handler = new Handler();

    private Button btnOpenChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        tvGameStatus = findViewById(R.id.tvGameStatus);
        tvRemainingTime = findViewById(R.id.tvRemainingTime);
        tvHidingPlayers = findViewById(R.id.tvHidingPlayers);
        tvDebugStatus = findViewById(R.id.tvDebugStatus);
        powerupScrollView = findViewById(R.id.powerupScrollView);

        btnOpenChat = findViewById(R.id.btnOpenChat);

        // Setup button for opening ChatActivity (only for Hiders or Seekers)
        btnOpenChat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra("ROLE", "Hider"); // or "Seeker" based on the role
            startActivity(intent);
        });

        // Start the game countdown timer
        startGameCountdown();
    }

    private void startGameCountdown() {
        tvGameStatus.setText("Game Status: In Progress");

        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (gameTimeRemaining > 0) {
                    gameTimeRemaining--;
                    tvRemainingTime.setText("Time Remaining: " + gameTimeRemaining + " seconds");
                    showPowerupsIfNeeded();
                    handler.postDelayed(this, 1000); // Update every second
                } else {
                    tvGameStatus.setText("Game Status: Game Over");
                    Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        handler.post(timerRunnable);
    }

    private void showPowerupsIfNeeded() {
        if (gameTimeRemaining <= 30) {
            powerupScrollView.setVisibility(View.VISIBLE);
        } else {
            powerupScrollView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Stop timer on activity destroy
    }
}
