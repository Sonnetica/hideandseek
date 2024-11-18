package com.example.tinycircuit;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateLobbyActivity extends AppCompatActivity {

    private EditText etHidingTime;
    private EditText etSeekerTime;
    private EditText etPlayerCount;
    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        // Initialize Views
        etHidingTime = findViewById(R.id.etHidingTime);
        etSeekerTime = findViewById(R.id.etSeekerTime);
        etPlayerCount = findViewById(R.id.etPlayerCount);
        btnStartGame = findViewById(R.id.btnStartGame);

        // Start Game Button Click Listener
        btnStartGame.setOnClickListener(v -> {
            String hidingTimeStr = etHidingTime.getText().toString();
            String seekerTimeStr = etSeekerTime.getText().toString();
            String playerCountStr = etPlayerCount.getText().toString();

            if (hidingTimeStr.isEmpty() || seekerTimeStr.isEmpty() || playerCountStr.isEmpty()) {
                Toast.makeText(CreateLobbyActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int hidingTime = Integer.parseInt(hidingTimeStr);
            int seekerTime = Integer.parseInt(seekerTimeStr);
            int playerCount = Integer.parseInt(playerCountStr);

            if (hidingTime <= 0 || seekerTime <= 0 || playerCount <= 0) {
                Toast.makeText(CreateLobbyActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass game parameters to the next screen (Game Activity)
            Intent intent = new Intent(CreateLobbyActivity.this, MainActivity.class);
            intent.putExtra("hidingTime", hidingTime);
            intent.putExtra("seekerTime", seekerTime);
            intent.putExtra("playerCount", playerCount);
            startActivity(intent);
        });
    }
}
