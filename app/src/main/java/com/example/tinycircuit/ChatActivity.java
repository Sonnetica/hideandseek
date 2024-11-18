package com.example.tinycircuit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private EditText messageInput;
    private Button sendMessageButton;

    private DatabaseReference chatRef;
    private String playerRole;  // Either "Hider" or "Seeker"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get player's role (Hider or Seeker)
        playerRole = getIntent().getStringExtra("ROLE");  // Pass this from the previous activity

        // Initialize Firebase reference
        chatRef = FirebaseDatabase.getInstance().getReference("chatMessages");

        // Setup RecyclerView to display messages
        recyclerView = findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter();
        recyclerView.setAdapter(chatAdapter);

        // Setup message input and button
        messageInput = findViewById(R.id.messageInput);
        sendMessageButton = findViewById(R.id.sendMessageButton);

        // Send button click listener
        sendMessageButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = messageInput.getText().toString();
        if (!message.isEmpty()) {
            String messageId = chatRef.push().getKey();
            ChatMessage chatMessage = new ChatMessage(playerRole, message);

            if (playerRole.equals("Hider")) {
                chatRef.child("Hiders").child(messageId).setValue(chatMessage);
            } else if (playerRole.equals("Seeker")) {
                chatRef.child("Seekers").child(messageId).setValue(chatMessage);
            }

            messageInput.setText("");  // Clear input after sending message
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Listen for new messages and update RecyclerView
        chatRef.child(playerRole).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatAdapter.clearMessages();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = messageSnapshot.getValue(ChatMessage.class);
                    chatAdapter.addMessage(chatMessage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
