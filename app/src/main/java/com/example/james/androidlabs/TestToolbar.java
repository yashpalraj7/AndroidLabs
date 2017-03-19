package com.example.james.androidlabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    private final String ACTIVITY_NAME = "TestToolbar";
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Floating Action button clicked yes!!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case R.id.action_music:
                Log.d(ACTIVITY_NAME, "Music selected");
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), (message.equals("") ? "Option 1 selected": message), Snackbar.LENGTH_SHORT);
                snack.show();
                return true;
            case R.id.action_robot:
                Log.d(ACTIVITY_NAME, "Robot selected");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.robot_dialog);

                builder.setPositiveButton(R.string.robot_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.robot_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            case R.id.action_skull:
                Log.d(ACTIVITY_NAME, "Skull selected");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                final EditText editMessage = (EditText) dialogView.findViewById(R.id.new_message);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder2.setView(dialogView)
                        // Add action buttons
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Log.i(ACTIVITY_NAME, String.valueOf(editMessage==null));
                                message = editMessage.getText().toString();
                                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), (message.equals("") ? "Option 1 selected": message), Snackbar.LENGTH_SHORT);
                                snack.show();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                return true;
            case R.id.action_about:
                Log.d(ACTIVITY_NAME, "About selected");
                Toast toast = Toast.makeText(TestToolbar.this, "V 1.0 by James Thibaudeau", Toast.LENGTH_LONG);
                toast.show();
                return true;
        }
        return false;
    }

}
