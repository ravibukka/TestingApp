package com.example.rbukka.background_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;


import java.security.MessageDigest;
import java.util.logging.LogRecord;

/**
 * Created by RBukka on 08/06/15.
 */
public class MusicService extends Service {

    MediaPlayer mp;
    MusicThread musicThread = new MusicThread();
    static Handler serviceHandler = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(MusicService.this, R.raw.music);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!musicThread.isAlive()) {
            musicThread.start();
        }

        //if (mp.isPlaying()) handle it

       // mp.start();

//        try {
//
//            for (int i = 0; i < 10; i++) {
//                Thread.sleep(60000);
//                if(mp.isPlaying()){
//                    stopSelf();
//                }
//                if (i == 9) {
//                    stopSelf();
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (!mp.isPlaying()){
//            stopSelf();
//        }
        return super.onStartCommand(intent, flags, startId);
      //  return START_STICKY, START_REDELIVER_INTENT

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mp.stop();
        mp.release();
    }

    class  MusicThread extends Thread {
        @Override
        public void run () {
            super.run();

            mp.start();
            Looper.prepare();
            serviceHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                        super.handleMessage(msg);
                        int reqCode = msg.what;

                        if(reqCode == 1) {

                            int currentDuration = mp.getCurrentPosition();
                            int totalDuration = mp.getDuration();

                            Message responseObj = new Message();
                            responseObj.obj = "" + currentDuration + "/" + totalDuration;
                            MainActivityBackground.activityHandler.sendMessage(responseObj);
                        }
            }
        };
            Looper.loop();

        }
    }
}
