package www.MagnaticKush.com.Trace;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raghu on 27-03-2015.
 */
public class EmailSenderThread implements Runnable {
    Context context = null;
    String recipientMail = "";
    String senderMail = "";
    String senderPassword = "";
    public EmailSenderThread(Context context, String recepientMail, String senderMail, String senderPassword){
        this.context = context;
        this.recipientMail = recepientMail;
        this.senderMail = senderMail;
        this.senderPassword = senderPassword;
    }
    @Override
    public void run() {
        // get start of cursor
        Log.i("CallLogDetailsActivity", "Getting Log activity...");
        String[] projection = new String[]{CallLog.Calls.NUMBER, android.provider.CallLog.Calls.CACHED_NAME, android.provider.CallLog.Calls.DATE, android.provider.CallLog.Calls.TYPE};
        Cursor cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, CallLog.Calls.DATE +" desc");
        cur.moveToFirst();
        //String lastCallnumber = cur.getString();
        String lastCallnumber =    cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
        String callName = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
        String callDate = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.DATE));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        try {

            date = dateFormat.parse(callDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Date callDayTime = new Date(Long.valueOf(callDate));
        String callType = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.TYPE));
        if (callType.equals("1")) {
            callType = "INCOMING";
        } else if (callType.equals("2")) {
            callType = "OUTGOING";
        }else {
            callType = "MISSED";
        }
        String message = "\n CALL SUMMARY" +
                "\nNUMBER :" + lastCallnumber
                + "\nName :" + callName
                + "\nDate :" + date.toString()
                + "\nCallType :" + callType;

        try {
            GMailSender sender = new GMailSender(senderMail , senderPassword);
            sender.sendMail("You have received a call",
                    message ,
                    senderMail,
                    recipientMail);
            Log.i("SendMail", "email send");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
}

