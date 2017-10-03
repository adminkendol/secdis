package lampung.dispenda.cctv;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chandra on 5/7/2016.
 */
public class Dashboards extends DrawerActivity {
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private static final String API_URL = "http://182.23.32.90:8030/app/api/dashboard";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    Handler progressHandler = new Handler();
    Handler progressHandlerA = new Handler();
    Handler progressHandlerB = new Handler();
    Handler progressHandlerC = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboards);
        setTitle("Dashboard");

        //Toast.makeText(getApplicationContext(), "Touch a bar to see progress", Toast.LENGTH_LONG).show();
        final ProgressDialog pd = new ProgressDialog(Dashboards.this);
        // Set progress dialog style horizontal
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Set the progress dialog background color transparent
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.setIndeterminate(false);
        // Finally, show the progress dialog
        pd.show();
        // Set the progress status zero on each button click
        progressStatus = 0;
        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    // Update the progress status
                    progressStatus +=1;
                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Update the progress status
                            pd.setProgress(progressStatus);
                            // If task execution completed
                            if(progressStatus == 100){
                                String data = null;
                                try {
                                    data = api();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(data!=null){
                                    // Dismiss/hide the progress dialog
                                    show_dash(data);
                                }else{
                                    //alert();
                                    reload();
                                }
                                pd.dismiss();
                            }
                        }
                    });
                }
            }
        }).start(); // Start the operation
    }
    public void show_dash(String data) {
        /*ProgressBarView pb = (ProgressBarView) v;
        int numDivisions = pb.getNumDivisions ();
        int val = pb.getValue ();
        val++;
        if (val > numDivisions) val = 0;
        pb.setValue (val);
        pb.invalidate ();*/
        Log.d("response!", "data" + data);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int online = 0;
        int offline = 0;
        int mem_usage = 0;
        int mem_tot = 0;
        int hd_usage = 0;
        int hd_tot = 0;
        try {
            online = jsonObj.getInt(String.valueOf("online"));
            offline = jsonObj.getInt(String.valueOf("offline"));
            mem_usage = (int) Math.round(jsonObj.getInt(String.valueOf("mem_usage")));
            mem_tot = (int) Math.round(jsonObj.getInt(String.valueOf("mem_tot")));
            hd_usage = (int) Math.round(jsonObj.getInt(String.valueOf("hd_usage")));
            hd_tot = (int) Math.round(jsonObj.getInt(String.valueOf("hd_tot")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressBar bar1 = (ProgressBar) findViewById(R.id.bar1);
        ProgressBar bar2 = (ProgressBar) findViewById(R.id.bar2);
        ProgressBar bar3 = (ProgressBar) findViewById(R.id.bar3);
        ProgressBar bar5 = (ProgressBar) findViewById(R.id.bar5);
        final String totalCctv = String.valueOf(online + offline);
        final int finalOnline = online;
        final int finalOffline = offline;
        final int finalmem_usage = mem_usage;
        final int finalhd_usage = hd_usage;
        bar1.setMax(Integer.parseInt(String.valueOf(totalCctv)));
        new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (i < finalOnline) {
                    i += 2;
                    final int finalI = i;
                    progressHandler.post(new Runnable() {
                        public void run() {
                            bar1.setProgress(finalI);
                            //progressingTextView.setText("" + i + " cctv");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public String api() throws JSONException {
        String data = null;
        JSONParser jsonParser = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String uid = "1";
        String loc = "0";
        params.add(new BasicNameValuePair("uid", uid));
        params.add(new BasicNameValuePair("loc", loc));
        final JSONObject json = jsonParser.makeHttpRequest(API_URL, "POST", params);
        if (json != null) {
            //excecute code
            data = json.getString(TAG_DATA);
            Log.d("response json!", "isi " + json);
        } else {
            data=null;
            Log.d("response json!", "isi " + json);
        }

        return data;
    }
    public void getOnline(){
        Intent intent;
        intent = new Intent(Dashboards.this, ListCctv.class);
        startActivity(intent);
    }
    private void reload(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Reconnecting....")
                .setCancelable(false)
                .setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = null;
                                //intent.putExtra("isi","");
                                intent = new Intent(Dashboards.this, Dashboards.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Tidak", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                Intent intent;
                                intent = new Intent(Dashboards.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }).show();
    }
}
