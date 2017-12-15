package com.mehulgarg.ghanta;

/**
 * Created by Mehul Garg on 12-10-2017.
 */

/* public class SettingsActivity extends AppCompatActivity {

    Button ringtone;
    String chosenRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ringtone=(Button)findViewById(R.id.ringtonepicker);
        ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, 5);
            }
        });
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
    {
        String title="";
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
            {
                this.chosenRingtone = uri.toString();
                SharedPreferences x1 = getSharedPreferences(PREFS1,0);
                SharedPreferences.Editor editor = x1.edit();
                editor.putString("ringtone",this.chosenRingtone);
                editor.commit();
                Ringtone ringtone = RingtoneManager.getRingtone(this, Uri.parse(this.chosenRingtone));
                title = ringtone.getTitle(this);
            }
            else
            {
                this.chosenRingtone = null;
            }
        }
        Toast.makeText(SettingsActivity.this,"Ringtone Selected: "+title,Toast.LENGTH_SHORT).show();
    }
} */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.mehulgarg.ghanta.MainActivity.PREFS1;
import static com.mehulgarg.ghanta.MainActivity.PREFS2;
import static com.mehulgarg.ghanta.MainActivity.PREFS3;

public class SettingsActivity extends AppCompatActivity {

    Button ringtone,number,message;
    String chosenRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ringtone=(Button)findViewById(R.id.ringtonepicker);
        ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, 5);
            }
        });
        number=(Button)findViewById(R.id.numberpicker);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.custom_dialog2, null);
                final TextView question = (TextView) mView.findViewById(R.id.textView);
                final EditText number =  mView.findViewById(R.id.edit_text);
                Button ok = (Button) mView.findViewById(R.id.button);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String num = number.getText().toString();
                        if(num.equals(""))
                            Toast.makeText(SettingsActivity.this, "Please enter a number!!", Toast.LENGTH_SHORT).show();
                        else{
                            SharedPreferences p = getSharedPreferences(PREFS2,0);
                            SharedPreferences.Editor ed = p.edit();
                            ed.putString("numb",num);
                            ed.commit();
                            Toast.makeText(SettingsActivity.this, "Number entered: "+num, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        message=(Button)findViewById(R.id.defaultmessage);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(SettingsActivity.this);
                final View mView1 = getLayoutInflater().inflate(R.layout.custom_dialog3, null);
                final TextView question = (TextView) mView1.findViewById(R.id.textView);
                final EditText mesg =  mView1.findViewById(R.id.edit_text);
                Button ok1 = (Button) mView1.findViewById(R.id.button);

                mBuilder1.setView(mView1);
                final AlertDialog dialog1 = mBuilder1.create();
                dialog1.show();

                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String msg = mesg.getText().toString();
                        if(msg.equals(""))
                            Toast.makeText(SettingsActivity.this, "Please enter the message!!", Toast.LENGTH_SHORT).show();
                        else{
                            SharedPreferences p1 = getSharedPreferences(PREFS3,0);
                            SharedPreferences.Editor ed1 = p1.edit();
                            ed1.putString("messg",msg);
                            ed1.commit();
                            dialog1.dismiss();
                        }
                    }
                });
            }
        });

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
    {
        String title="";
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
            {
                this.chosenRingtone = uri.toString();
                SharedPreferences x1 = getSharedPreferences(PREFS1,0);
                SharedPreferences.Editor editor = x1.edit();
                editor.putString("ringtone",this.chosenRingtone);
                editor.commit();
                Ringtone ringtone = RingtoneManager.getRingtone(this, Uri.parse(this.chosenRingtone));
                title = ringtone.getTitle(this);
            }
            else
            {
                this.chosenRingtone = null;
            }
        }
        Toast.makeText(SettingsActivity.this,"Ringtone Selected: "+title,Toast.LENGTH_SHORT).show();
    }


}
