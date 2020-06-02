package www.MagnaticKush.com.Trace;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class CallLogMailer extends AppCompatActivity {

    public final String LOG_TAG = "CallLogMailer";
    private PhoneCallListener phoneListener = null;
    public static String id1 = "test_channel_01";
    Button submitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log_mailer);
        submitbutton = (Button) findViewById(R.id.submitbutton);
        requestPermissions();
        createchannel();

        SharedPreferences preferences = getSharedPreferences("LogMailer", Context.MODE_PRIVATE);
         preferences.getString("rEmailId", "");
         preferences.getString("sEmailId", "");
        preferences.getString("sPassword", "");

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i(LOG_TAG, "button clicked");

                SharedPreferences preferences = getSharedPreferences("LogMailer", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("rEmailId", "krishanjangir0003@gmail.com");
                editor.putString("sEmailId", "krishanjangir0003@gmail.com");
                editor.putString("sPassword", "hp13b0003");
                editor.apply();
                editor.commit();


                Intent number5 = new Intent(getBaseContext(), PhoneService.class);
                number5.putExtra("times", 5);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startForegroundService(number5);
                } else {
                    //lower then Oreo, just start the service.
                    startService(number5);
                }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_call_log_mailer, menu);
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


    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel mChannel = new NotificationChannel(id1,
                    getString(R.string.app_name),  //name of the channel
                    NotificationManager.IMPORTANCE_LOW);   //importance level
            //important level: default is is high on the phone.  high is urgent on the phone.  low is medium, so none is low?
            // Configure the notification channel.
            mChannel.setDescription(getString(R.string.my_string));
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this channel, if the device supports this feature.
            mChannel.setShowBadge(true);
            nm.createNotificationChannel(mChannel);
        }
    }


    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_CALL_LOG)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_CALL_LOG,
                        Manifest.permission.INTERNET,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CHANGE_NETWORK_STATE,


                }, 0);
            }
        }


    }
}
