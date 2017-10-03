package lampung.dispenda.cctv;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chandra on 9/25/2016.
 */
public class UpdateApp extends AsyncTask<String, Integer, String> {
    private Context context;
    ProgressDialog mProgressDialog;
    private PowerManager.WakeLock mWakeLock;
    public UpdateApp(ProgressDialog mProgressDialog) {
        this.mProgressDialog = mProgressDialog;
    }

    public void setContext(Context contextf){context = contextf;}

    @Override
    protected String doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            int fileLength = c.getContentLength();
            String PATH = "/mnt/sdcard/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "app-debug.apk");
            if(outputFile.exists()){
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[4096];
            long total = 0;
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                total += len1;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));

                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/Download/app-debug.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("UpdateAPP", "Update error! " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);

    }
    @Override
    protected void onPostExecute(String result) {
        Log.i("DownloadTask", "Work Done! PostExecute");
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (result != null)
            Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"File Downloaded", Toast.LENGTH_SHORT).show();
    }
}
