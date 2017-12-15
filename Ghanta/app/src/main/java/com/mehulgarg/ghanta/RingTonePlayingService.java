package com.mehulgarg.ghanta;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.view.GestureDetector.SimpleOnGestureListener;

import static com.mehulgarg.ghanta.MainActivity.PREFS1;
import static com.mehulgarg.ghanta.MainActivity.PREFS2;
import static com.mehulgarg.ghanta.MainActivity.PREFS3;

/**
 * Created by Mehul Garg on 23-09-2017.
 */

public class RingTonePlayingService extends Service implements View.OnTouchListener {

    MediaPlayer media_song;
    boolean isRunning;
    int startId;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // fetch the extra string values
        String state = intent.getExtras().getString("extra");
        Log.d("TAG::","inside service"+state);
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }



        // if else conditions

        // if music is not playing and the user says alarm on then music should start playing
        if (!this.isRunning && startId == 1) {

            SharedPreferences e1234 = getSharedPreferences(PREFS1, 0);
            String rt = e1234.getString("ringtone", "not found");
            if(rt.equals("not found")){

            // create an instace of an media player
            media_song = MediaPlayer.create(this, R.raw.analog_watch_alarm);
            }
            else {
                Uri uri = Uri.parse(rt);
                media_song = MediaPlayer.create(this, uri);
            }


            media_song.setLooping(true);

            // start the ringtone
            media_song.start();



            SharedPreferences x3 = getSharedPreferences(PREFS2,0);
            String num = x3.getString("numb","Not Found");
            SharedPreferences x4 = getSharedPreferences(PREFS3,0);
            String msg = x4.getString("messg","Not Found");

            if(!num.equals("Not Found")&&!msg.equals("Not Found")){
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("tel:"+num,null,msg,null,null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            else if(num.equals("Not Found")) {
                Toast.makeText(getApplicationContext(), "Your message couldn't be sent.\nEnter a default number in Settings.",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Your message couldn't be sent.\nEnter a default message in Settings.",
                        Toast.LENGTH_LONG).show();
            }






            this.isRunning = true;
            this.startId = 0;

            // Notification
            // set up the Notification Service
            NotificationManager notify_manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            // set up an intent that goes to the main activity
            Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);

            // set up a pending intent
            PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0, intent_main_activity, 0);

            // make the notification parameters

            Log.d("TAG::","startid="+startId);
            this.context=this;

            Notification notification_popup = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.notification_icon))
                    .setContentTitle("Wake up ! Wake up !! Wake up !!!")
                    .setContentText("Tap to close the alarm.")
                    .setContentIntent(pending_intent_main_activity)
                    .setAutoCancel(true)
                    .build();

            notification_popup.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
            notification_popup.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
            notification_popup.ledARGB = 0xFFFFA500;
            notification_popup.ledOnMS = 800;
            notification_popup.ledOffMS = 1000;

            // set up a notification call command
            notify_manager.notify(0, notification_popup);
        }

        // if there is music playing and the user says alarm off then music should stop playing
        if (this.isRunning && startId == 0) {

            Log.d("TAG::","inside if ");

            // stop the ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        // if the user presses random button just to remove any sort of bugs
        // if music is not playing and the user says alarm off then do nothing
        if (!this.isRunning && startId == 0) {

            this.isRunning = false;
            this.startId = 0;
        }

        // if there is music playing and the user says alarm on then do nothing
        if (this.isRunning && startId == 1) {

            this.isRunning = true;
            this.startId = 1;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.

        super.onDestroy();
        this.isRunning = false;
    }
    private GestureDetector gestureDetector;

    public void OnSwipeTouchListener(Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return gestureDetector.onTouchEvent(motionEvent);
    }
    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
        Intent intentXXX = new Intent(context,MainActivity.class);
        context.startActivity(intentXXX);
    }

    public void onSwipeLeft() {
        Intent intentXXY = new Intent(context,MainActivity.class);
        context.startActivity(intentXXY);
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}
