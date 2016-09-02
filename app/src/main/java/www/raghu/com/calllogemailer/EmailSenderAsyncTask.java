package www.raghu.com.calllogemailer;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Raghu on 28-03-2015.
 */
public class EmailSenderAsyncTask extends AsyncTask<EmailSenderThread,Void,Void> {

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(EmailSenderThread... params) {
        try {
            params[0].run();
        } catch (Exception e) {
            Log.i("AsyncTask", e.getMessage(), e);
        }
        return null;
    }
}
