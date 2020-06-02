package www.MagnaticKush.com.Trace;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

//import java.util.logging.Handler;

/**
 * Created by Raghu on 26-03-2015.
 */
public class PhoneCallListener extends PhoneStateListener {

    private boolean isPhoneCalling = false;
    private final String LOG_TAG = "PhoneCallListener";
    private Context context = null;
    //CallLogMailer mailer  = null;

    private String lastCallNumber = "";
   public PhoneCallListener() {

   }
   public PhoneCallListener(Context context) {
       this.context = context;
       //this.mailer = context;
   }
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        if (TelephonyManager.CALL_STATE_RINGING == state) {
            // phone ringing
            Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
           isPhoneCalling = true;
        }

        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            // active
            Log.i(LOG_TAG, "OFFHOOK");
            isPhoneCalling = true;

        }

        if (TelephonyManager.CALL_STATE_IDLE == state) {
            // run when class initial and phone call ended, need detect flag
            // from CALL_STATE_OFFHOOK
            Log.i(LOG_TAG, "IDLE number");

            if (isPhoneCalling) {

               Handler handler = new Handler();

                //Put in delay because call log is not updated immediately when state changed
                // The dialler takes a little bit of time to write to it 500ms seems to be enough

                Toast to = Toast.makeText(context,"accessing sharedPreferences", Toast.LENGTH_SHORT);
                to.show();
                SharedPreferences preferences = context.getSharedPreferences("LogMailer",Context.MODE_PRIVATE);
                final String recepientEmail = preferences.getString("rEmailId", "");
                final String senderEmail = preferences.getString("sEmailId", "");
                final String senderPassword = preferences.getString("sPassword", "");
                if (recepientEmail.equals("") || senderEmail.equals("") || senderPassword.equals("")) {
//                   Toast tst = Toast.makeText(context, "no values provided in settings", Toast.LENGTH_SHORT);
//                    tst.show();
                }else {
                    Toast tst = Toast.makeText(context,"sending Email to"+ senderEmail ,Toast.LENGTH_SHORT);
                    tst.show();
                    isPhoneCalling = false;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new EmailSenderAsyncTask().execute(new EmailSenderThread(context,recepientEmail,senderEmail,senderPassword));
                        }
                    },1000);
                    /*new EmailSenderAsyncTask().execute(new EmailSenderThread(context,recepientEmail,senderEmail,senderPassword));*/
                }
                        /*editor.putString("rEmailId", emailId);
        editor.putString("sEmailId", sendingEmailId);
        editor.putString("sPassword", sendingEmailIdPassword);*/



                isPhoneCalling = false;
            }

        }
    }
}