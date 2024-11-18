package com.example.tinycircuit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> messages = new ArrayList<>();

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.senderText.setText(message.getSender());
        holder.messageText.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void clearMessages() {
        messages.clear();
        notifyDataSetChanged();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView senderText;
        public TextView messageText;

        public ChatViewHolder(View view) {
            super(view);
            senderText = view.findViewById(android.R.id.text1);
            messageText = view.findViewById(android.R.id.text2);
        }
    }
}
