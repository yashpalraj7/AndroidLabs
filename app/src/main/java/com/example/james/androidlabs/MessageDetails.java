package com.example.james.androidlabs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Bundle extras = getIntent().getExtras();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(extras);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.activity_message_details, fragment);
        ft.commit();

    }

    public static class MessageFragment extends Fragment {
        public ChatWindow window;
        public MessageFragment(){
            super();
        }

        public MessageFragment(ChatWindow window){
            this.window=window;
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.message_fragment, container, false);

            TextView idText = (TextView) view.findViewById(R.id.id_textView);
            idText.setText(String.valueOf(getArguments().getLong("_ID")));

            TextView messageText = (TextView) view.findViewById(R.id.message_textView);
            messageText.setText(getArguments().getString("MESSAGE"));

            Button delete = (Button) view.findViewById(R.id.delete_button);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(window!=null){
                        window.delete();
                        window.removeFragment();
                    } else {
                        getActivity().setResult(5);
                        getActivity().finish();
                    }
                }
            });

            return view;
        }
    }
}
