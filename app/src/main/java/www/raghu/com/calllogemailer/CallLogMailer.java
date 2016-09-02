package www.raghu.com.calllogemailer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CallLogMailer extends ActionBarActivity {

    public final String LOG_TAG = "CallLogMailer";
    private PhoneCallListener phoneListener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log_mailer);


        SharedPreferences preferences = getSharedPreferences("LogMailer", Context.MODE_PRIVATE);
        String recepientEmail = preferences.getString("rEmailId", "");
        String senderEmail = preferences.getString("sEmailId", "");
        String senderPassword = preferences.getString("sPassword", "");
        Boolean enabled = preferences.getBoolean("lEnabled", false);
        if (senderEmail.equals("") == false) {

            EditText senderEmailView = (EditText) findViewById(R.id.senderEmail);
            senderEmailView.setText(senderEmail);
        }
        if (recepientEmail.equals("") == false) {
            EditText recipientEmailView = (EditText) findViewById(R.id.email);
            recipientEmailView.setText(recepientEmail);
        }
        if (senderPassword.equals("") == false) {
            EditText password = (EditText) findViewById(R.id.senderPassword);
            password.setText(senderPassword);
        }
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxinstant);
            checkBox.setChecked(enabled);

        enableListener(checkBox.isChecked());

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
    public void onClick(View view) {
        Log.i(LOG_TAG,"button clicked");
        EditText emailIdView = (EditText) findViewById(R.id.email);
        String emailId = emailIdView.getText().toString();
        String sendingEmailId = ((EditText) findViewById(R.id.senderEmail)).getText().toString();
        String sendingEmailIdPassword = ((EditText) findViewById(R.id.senderPassword)).getText().toString();
        SharedPreferences preferences = getSharedPreferences("LogMailer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("rEmailId", emailId);
        editor.putString("sEmailId", sendingEmailId);
        editor.putString("sPassword", sendingEmailIdPassword);
        editor.putBoolean("lEnabled", ((CheckBox) findViewById(R.id.checkBoxinstant)).isChecked());
        editor.apply();
        editor.commit();
        Toast tst = Toast.makeText(this,"settings saved", Toast.LENGTH_SHORT);
        tst.show();



    }

    public void listenStatus(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBoxinstant);
         enableListener(checkBox.isChecked());
    }

    public void enableListener(Boolean status) {

        if (phoneListener == null) {
            phoneListener = new PhoneCallListener(this);
        }
        if (status == true) {

            TelephonyManager telephonyManager = (TelephonyManager) this
                    .getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) this
                    .getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneListener,
                    PhoneStateListener.LISTEN_NONE);
        }
    }



}
