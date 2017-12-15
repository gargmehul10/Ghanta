package com.mehulgarg.ghanta;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    Context context;

    public static final String PREFS = "examplePrefs";
    public static final String PREFS1 = "examplePrefs1";
    public static final String PREFS2 = "examplePrefs2";
    public static final String PREFS3 = "examplePrefs3";

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
        listView = (ListView) findViewById(R.id.list);

        dataModels = new ArrayList<>();

        this.context = this;

        SharedPreferences ex1 = getSharedPreferences(PREFS, 0);
        String c = ex1.getString("message", "empty");
        if (!c.equals("empty")) {
            String f1 = ex1.getString("message", "not found");

            Log:d("Tag0 :: ", f1);

            int ij;
            String g[] = f1.split(" ");

            for (ij = 0; ij < g.length; ij++)
                if (!g[ij].equals(""))
                    dataModels.add(new DataModel(g[ij]));

            adapter = new CustomAdapter(dataModels, getApplicationContext(), getLayoutInflater(), MainActivity.this);
            listView.setAdapter(adapter);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
                // initialize our time picker
                final TimePicker alarm_timepicker = (TimePicker) mView.findViewById(R.id.alarm_timePicker);
                Button ok = (Button) mView.findViewById(R.id.ok_timepicker);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // get the int values of the hour and minute
                        String hour = String.valueOf(alarm_timepicker.getCurrentHour());
                        String minute = String.valueOf(alarm_timepicker.getCurrentMinute());
                        String ans = hour + ':' + minute;

                        Log.d("Tag :: ", ans);

                        SharedPreferences exampleprefs = getSharedPreferences(PREFS, 0);
                        String j = exampleprefs.getString("message", "not found");

                        if (j.equals("not found")) {
                            SharedPreferences.Editor editor = exampleprefs.edit();
                            editor.putString("message", ans + " ");
                            editor.commit();
                        } else {
                            SharedPreferences.Editor editor = exampleprefs.edit();
                            editor.putString("message", j + ans + " ");
                            editor.commit();
                        }

                        SharedPreferences example = getSharedPreferences(PREFS, 0);
                        String f1 = example.getString("message", "not found");

                        Log:d("Tag :: ", f1);

                        dataModels.add(new DataModel(ans));
                        adapter = new CustomAdapter(dataModels, getApplicationContext(), getLayoutInflater(), MainActivity.this);
                        listView.setAdapter(adapter);
                        dialog.dismiss();
                        Intent intent100 = getIntent();
                        finish();
                        startActivity(intent100);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent12 = new Intent(this.context, SettingsActivity.class);
            this.context.startActivity(intent12);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
