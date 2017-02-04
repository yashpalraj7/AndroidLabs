package com.example.james.androidlabs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    final String ACTIVITY_NAME = "ChatWindow";
    private ArrayList<String> messages;
    private ListView listView;
    private Button sendButton;
    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        messages = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.chat_listView);

        messageText = (EditText) findViewById(R.id.msg_text);

        final ChatAdapter messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        sendButton = (Button) findViewById(R.id.button2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "clicked send button");
                messages.add(messageText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                messageText.setText("");

            }
        });


    }

    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return messages.get(position);
        }
    }
}
