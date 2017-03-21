package com.example.james.androidlabs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    final String ACTIVITY_NAME = "ChatWindow";
    private ArrayList<String> messages;
    private ListView listView;
    private Button sendButton;
    private EditText messageText;
    private ChatDatabaseHelper dbHelper;
    public boolean isFragment;
    private Cursor c;
    private long idToDelete;
    private ChatAdapter messageAdapter;
    private int positionID;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_window);
        messages = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.chat_listView);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.chat_frameLayout);
        isFragment = frameLayout != null;
        Log.i(ACTIVITY_NAME, "Frame layout present: " + String.valueOf(frameLayout != null));

        //DB STUFF
        dbHelper = new ChatDatabaseHelper(getApplicationContext());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        c = db.query(false, "CHAT", new String[]{"_ID", "KEY_Message"}, null, null, null, null, null, null);
        String msg;

        c.moveToFirst();
        while (!c.isAfterLast()) {
            msg = c.getString(c.getColumnIndex((dbHelper.KEY_MESSAGE)));
            messages.add(msg);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE " + msg);
            c.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + c.getColumnCount());

        for (int i = 0; i < c.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, c.getColumnName(i));
        }


        messageText = (EditText) findViewById(R.id.msg_text);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        sendButton = (Button) findViewById(R.id.button2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "clicked send button");
                String msgText = messageText.getText().toString();

                ContentValues cValues = new ContentValues();
                cValues.put(dbHelper.KEY_MESSAGE, msgText);
                db.insert(dbHelper.TABLE_NAME, "NullPlaceHolder", cValues);

                messages.add(msgText);
                c = db.query(false, "CHAT", new String[]{"_ID", "KEY_Message"}, null, null, null, null, null, null);
                messageAdapter.notifyDataSetChanged();
                messageText.setText("");

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                intent.putExtra("_ID", messageAdapter.getItemId(position));
                intent.putExtra("MESSAGE", messageAdapter.getItem(position));

                idToDelete = messageAdapter.getItemId(position);
                positionID = position;


                if (isFragment) {
                    Fragment mFragment = new MessageDetails.MessageFragment(ChatWindow.this);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    mFragment.setArguments(intent.getExtras());
                    ft.replace(R.id.chat_frameLayout, mFragment).addToBackStack("STACK");
                    ft.commit();


                } else {
                    startActivityForResult(intent, 5);
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void delete(){
        Log.i(ACTIVITY_NAME, "DELETE");
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_NAME, dbHelper.KEY_ID + "=" + String.valueOf(idToDelete), null);
        Log.i(ACTIVITY_NAME, String.valueOf(idToDelete));
        messages.remove(positionID);
        c = db.query(false, "CHAT", new String[]{"_ID", "KEY_Message"}, null, null, null, null, null, null);
        messageAdapter.notifyDataSetChanged();
    }

    public void removeFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fm.popBackStack();
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 5) {
            delete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ChatWindow Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
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

            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return messages.get(position);
        }

        public long getItemId(int position) {
            c.moveToPosition(position);
            return c.getLong(c.getColumnIndex(dbHelper.KEY_ID));
        }
    }
}
