package lampung.dispenda.cctv;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lampung.dispenda.cctv.config.Basic;

/**
 * Created by Chandra on 5/5/2016.
 * Function Activity Dashboard CCTV
 */
public class DashboardForAndroidApp extends DrawerActivity {
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private Basic config;
    private Basic fake;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final String TAG_INFO = "info";
    ProgressBar myprogressBar;
    TextView progressingTextView;
    ProgressBar myprogressBarA;
    TextView progressingTextViewA;
    ProgressBar myprogressBarB;
    TextView progressingTextViewB;
    ProgressBar myprogressBarC;
    TextView progressingTextViewC;
    GradientDrawable topD;
    Handler progressHandler = new Handler();
    Handler progressHandlerA = new Handler();
    Handler progressHandlerB = new Handler();
    Handler progressHandlerC = new Handler();
    int i = 0;
    int a = 0;
    int c = 0;
    int d = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle = getIntent().getExtras();
        //String isi = bundle.getString("isi");
        setTitle("Dashboard");
        setContentView(R.layout.dashboard);
        final ProgressDialog pd = new ProgressDialog(DashboardForAndroidApp.this);
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
                                String info = null;
                                /*try {
                                    data = api();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    info = api_info();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                                fake =new Basic();
                                data = fake.getResponse(5);
                                info = fake.getResponse(3);
                                Log.d("response API INFO RETURN!", "isi " + info);
                                if(data!=null){
                                        // Dismiss/hide the progress dialog
                                    show_dash(data);
                                    try {
                                        show_info(info);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
    public void show_info(String info) throws JSONException {
        TextView infoA = (TextView) findViewById(R.id.info1);
        JSONObject jsonObjA = null;
        try {
            jsonObjA = new JSONObject(String.valueOf(info));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String info_s = jsonObjA.getString("OS");
        JSONObject distro_array = jsonObjA.getJSONObject("Distro");
        String distro = distro_array.getString("name");
        String version = distro_array.getString("version");
        String host = jsonObjA.getString("HostName");
        JSONObject uptime = jsonObjA.getJSONObject("UpTime");
        String uptime_text = uptime.getString("text");
        JSONArray cpu = jsonObjA.getJSONArray("CPU");
        JSONObject cpu_0 = cpu.getJSONObject(0);
        String cpu_0_model = cpu_0.getString("Model");
        JSONArray temps = jsonObjA.getJSONArray("Temps");
        JSONObject temps_0 = temps.getJSONObject(5);
        String temps_0_temp = temps_0.getString("temp");
        String temps_0_unit = temps_0.getString("unit");
        String numLoggedIn = jsonObjA.getString("numLoggedIn");
        infoA.setText(String.valueOf(info_s));

        String fon;
        String fonA;
        if (Integer.valueOf(temps_0_temp) > 50){
            fon = "red";
        }else{
            fon = "black";
        }
        if (Integer.valueOf(numLoggedIn) > 0){
            fonA = "red";
        }else{
            fonA = "black";
        }
        infoA.setText(Html.fromHtml("<html><body style='background:white'>OS : "+String.valueOf(info_s)+"<br>"+
                "Kernel : "+String.valueOf(distro)+"<br>"+
                "Version : "+String.valueOf(version)+"<br>"+
                "Hostname : "+String.valueOf(host)+"<br>"+
                "Uptime : "+String.valueOf(uptime_text)+"<br>"+
                "CPU : "+String.valueOf(cpu_0_model)+"<br>"+
                "Temp : <font color='"+fon +"'>"+String.valueOf(temps_0_temp)+"</font> Degree " + temps_0_unit+"<br>"+
                "Root Loggin : <font color='"+fonA +"'>"+String.valueOf(numLoggedIn)+"</font> Users<br>"+
                "</body></html>"));
        infoA.setMovementMethod(new ScrollingMovementMethod());
    }
    public void show_dash(String data){
        Log.d("response!", "data" + data);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myprogressBar = (ProgressBar) findViewById(R.id.progressBar_uiA);
        progressingTextView = (TextView) findViewById(R.id.textBarA);
        myprogressBarA = (ProgressBar) findViewById(R.id.progressBar_uiB);
        progressingTextViewA = (TextView) findViewById(R.id.textBarB);
        myprogressBarB = (ProgressBar) findViewById(R.id.progressBar_uiC);
        progressingTextViewB = (TextView) findViewById(R.id.textBarC);
        myprogressBarC = (ProgressBar) findViewById(R.id.progressBar_uiD);
        progressingTextViewC = (TextView) findViewById(R.id.textBarD);
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
        //Log.d("cctv online", "data:" + online);
        final String totalCctv = String.valueOf(online + offline);
        progressingTextView.setText(String.valueOf(totalCctv));
        myprogressBar.setMax(Integer.parseInt(String.valueOf(totalCctv)));
        progressingTextViewA.setText(String.valueOf(totalCctv));
        myprogressBarA.setMax(Integer.parseInt(String.valueOf(totalCctv)));
        progressingTextViewB.setText(String.valueOf(mem_tot));
        myprogressBarB.setMax(Integer.parseInt(String.valueOf(mem_tot)));
        progressingTextViewC.setText(String.valueOf(hd_tot));
        myprogressBarC.setMax(Integer.parseInt(String.valueOf(hd_tot)));
        final int finalOnline = online;
        final int finalOffline = offline;
        final int finalmem_usage = mem_usage;
        final int finalhd_usage = hd_usage;
        new Thread(new Runnable() {
            public void run() {
                while (i < finalOnline) {
                    i += 2;
                    progressHandler.post(new Runnable() {
                        public void run() {
                            myprogressBar.setProgress(i);
                            progressingTextView.setText("" + i + " cctv");
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                while (a < finalOffline) {
                    a += 2;
                    progressHandlerA.post(new Runnable() {
                        public void run() {
                            myprogressBarA.setProgress(a);
                            progressingTextViewA.setText("" + a + " cctv");
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                while (c < finalmem_usage) {
                    c += 2;
                    progressHandlerB.post(new Runnable() {
                        public void run() {
                            myprogressBarB.setProgress(c);
                            progressingTextViewB.setText("" + c + " GB");
                            if (c > 10){
                                progressingTextViewB.setTextColor(Color.RED);
                                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                                anim.setDuration(150); //You can manage the time of the blink with this parameter
                                anim.setStartOffset(20);
                                anim.setRepeatMode(Animation.REVERSE);
                                anim.setRepeatCount(Animation.INFINITE);
                                progressingTextViewB.startAnimation(anim);
                            }
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                while (d < finalhd_usage) {
                    d += 2;
                    progressHandlerC.post(new Runnable() {
                        public void run() {
                            myprogressBarC.setProgress(d);
                            progressingTextViewC.setText("" + d + " GB");
                            if (d > 200){
                                progressingTextViewC.setTextColor(Color.RED);
                                //myprogressBarC.setBackgroundColor(Color.RED);
                                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                                anim.setDuration(150); //You can manage the time of the blink with this parameter
                                anim.setStartOffset(20);
                                anim.setRepeatMode(Animation.REVERSE);
                                anim.setRepeatCount(Animation.INFINITE);
                                progressingTextViewC.startAnimation(anim);
                            }
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        myprogressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id ="1";
                getOnline(id);
            }
        });
        myprogressBarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id ="2";
                getOnline(id);
            }
        });
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
        config = new Basic();
        String url = config.getDASHBOARD();
        final JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
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
    public JSONObject api_info() throws JSONException {
        JSONObject info = null;
        JSONParser jsonParser = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String uid = "json";
        params.add(new BasicNameValuePair("out", uid));
        config = new Basic();
        String url = config.getInfoServer();
        final JSONObject jsons = jsonParser.makeHttpRequest(url, "GET", params);
        if (jsons != null) {
            //excecute code
            info = jsons;
            Log.d("response API INFO!", "isi " + jsons);
        } else {
            info=null;
            Log.d("response API INFO!", "isi " + jsons);
        }

        return info;
    }
    protected void getOnline(String i){
        Intent intentS = new Intent(this, ListCctv.class);
        intentS.putExtra("id",i);
        Log.d("INTENT", "BEFORE:" + i);
        startActivity(intentS);
    }
    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tidak tersedia layanan internet").setCancelable(false).setPositiveButton("Okay", new
            DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //DrawerActivity.this.finish();
                    Intent intent;
                    intent = new Intent(DashboardForAndroidApp.this, LoginActivity.class);
                    startActivity(intent);
                    //Toast.makeText(DashboardForAndroidApp.this, "Tidak ada layanan internet", Toast.LENGTH_SHORT).show();
                }}).show();
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
                                intent = new Intent(DashboardForAndroidApp.this, DashboardForAndroidApp.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Tidak", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                Intent intent;
                                intent = new Intent(DashboardForAndroidApp.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }).show();
    }

}


