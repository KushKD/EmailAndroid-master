package www.MagnaticKush.com.Trace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Set;

/**
 * Created by Raghu on 26-03-2015.
 */
public class IncomingCall extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        try {


            Bundle bundle = intent.getExtras();
            Set<String> keySet = bundle.keySet();
            for (String key: keySet) {
                Log.i("RECEIVER", key + ":" +bundle.get(key));
            }

            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            PhoneCallListener PhoneListener = new PhoneCallListener(context);

            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneCallListener.LISTEN_CALL_STATE);


        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }

    }
}
