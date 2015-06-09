package com.example.rbukka.background_service;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.logging.LogRecord;


public class MainActivityBackground extends ActionBarActivity {

    Button playingButton, stopedButton, getingStatusButton;
    static Handler activityHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_background);

        playingButton = (Button) findViewById(R.id.buttonPlay);
        stopedButton = (Button) findViewById(R.id.buttonStop);
        getingStatusButton = (Button) findViewById(R.id.buttonStatus);
        activityHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg != null){
                    String response = (String) msg.obj;
                    Toast.makeText(MainActivityBackground.this, response, Toast.LENGTH_LONG).show();
                }
            }
        };


        playingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent musicIntent;
                musicIntent = new Intent(MainActivityBackground.this, MusicService.class);
                startService(musicIntent);

            }
        });
        stopedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent musicIntent;
                musicIntent = new Intent(MainActivityBackground.this, MusicService.class);
                stopService(musicIntent);

            }
        });
        getingStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message requestMessage = new Message();
                requestMessage.what = 1;
                MusicService.serviceHandler.sendMessage(requestMessage);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_background, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
