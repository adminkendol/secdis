package lampung.dispenda.cctv.module;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lampung.dispenda.cctv.JSONParser;
import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.adapter.ArsipViewAdapter;
import lampung.dispenda.cctv.config.Basic;

/**
 * Created by Chandra on 9/23/2016.
 */
public class Arsip_cam extends AppCompatActivity {


    private FragmentActivity activity;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bundle bundle = getIntent().getExtras();
        final String camId = bundle.getString("camId");
        String camName = bundle.getString("camName");
        String camLoc = bundle.getString("camLoc");
        String camImg = bundle.getString("camImg");
        setContentView(R.layout.arsip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
        setTitle("Arsip " + camName + " " + camLoc);
        Date date = new Date();
        final EditText fromdate=(EditText)findViewById(R.id.fromdate);
        fromdate.setTextColor(Color.BLACK);
        String tgl = dateFormat.format(date);
        fromdate.setText(tgl+" 00:00:00");
        final EditText enddate=(EditText)findViewById(R.id.todate);
        enddate.setTextColor(Color.BLACK);
        enddate.setText(tgl + " 23:59:59");
        getDialogTime(fromdate, 1);
        getDialogTime(enddate, 2);
        Button cari =(Button)findViewById(R.id.buttonCari);
        cari.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        ReadDataTask m=(ReadDataTask) new ReadDataTask(camId,fromdate.getText().toString(),enddate.getText().toString()).execute();
                                    }
                                });

        ReadDataTask m= (ReadDataTask) new ReadDataTask(camId, fromdate.getText().toString(), enddate.getText().toString()).execute();
    }

    private void getDialogTime(final EditText src, final int i) {

        src.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Arsip_cam.this);
                LayoutInflater inflater = Arsip_cam.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.form_tgl, null);
                final CalendarView CV =(CalendarView) dialogView.findViewById(R.id.calendarView);
                final TimePicker TP =(TimePicker) dialogView.findViewById(R.id.timePicker);
                TP.is24HourView();

                if(i==1){
                    TP.setCurrentHour(0);
                    TP.setCurrentMinute(0);
                }else {
                    TP.setCurrentHour(23);
                    TP.setCurrentMinute(59);
                }
                builder.setView(dialogView);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long cv_Text = CV.getDate();
                        String cv_date = dateFormat.format(cv_Text);
                        long tp_H = TP.getCurrentHour();
                        long tp_M = TP.getCurrentMinute();
                        src.setText(cv_date+" "+tp_H+":"+tp_M+":00");
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }






    class ReadDataTask extends AsyncTask<String, Void, String> {
        ListView mListView;
        //Activity mContex;
        ProgressDialog pDialog;
        String camId;
        String fromdate;
        String enddate;
        public ReadDataTask(String camId, String fromdate, String enddate) {
            this.camId=camId;
            this.fromdate=fromdate;
            this.enddate=enddate;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Arsip_cam.this);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... sText) {
            String returnResult = null;
            try {
                returnResult = getData(camId,fromdate,enddate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return returnResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Basic config = new Basic();
            String url = config.getUrl();
            if (result.equalsIgnoreCase("Exception Caught")) {
                Log.d("RESULT API", "data:" + String.valueOf(result) + ":END");
                Toast.makeText(Arsip_cam.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }else if (result.equalsIgnoreCase("no results")) {
                Toast.makeText(Arsip_cam.this, "Data empty", Toast.LENGTH_LONG).show();
            } else {
                JSONObject data = null;
                try {
                    data = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Array data JSON REC", String.valueOf(result));
                try {
                    JSONArray cams;
                    cams = data.getJSONArray("data");
                    String[] rec_id = new String[cams.length()];
                    String[] rec_name = new String[cams.length()];
                    String[] camera_id = new String[cams.length()];
                    String[] location_name = new String[cams.length()];
                    String[] camera_name = new String[cams.length()];
                    String[] tgl = new String[cams.length()];
                    String[] jam = new String[cams.length()];
                    Log.d("Array data cams arsip", String.valueOf(cams));

                    for (int i = 0; i < cams.length(); i++) {
                        JSONObject c = cams.getJSONObject(i);
                        rec_id[i] = c.getString("rec_id");
                        rec_name[i] = c.getString("rec_name");
                        camera_id[i] = c.getString("camera_id");
                        location_name[i] = c.getString("location_name");
                        camera_name[i] = c.getString("camera_name");
                        tgl[i] = c.getString("tgl");
                        jam[i] = c.getString("jam");

                    }
                    ArsipViewAdapter adapter = new ArsipViewAdapter(Arsip_cam.this,rec_id,rec_name,camera_id,location_name,camera_name,tgl,jam);
                    RecyclerView myView =  (RecyclerView) findViewById(R.id.parentView);
                    myView.setHasFixedSize(true);
                    myView.setAdapter(adapter);
                    LinearLayoutManager llm = new LinearLayoutManager(Arsip_cam.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    myView.setLayoutManager(llm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        private String getData(String camId, String fromdate, String enddate) throws JSONException {
            Basic config = new Basic();
            String url = config.getCAMARSIP();
            JSONParser jParser = new JSONParser();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("id", camId));
            parameter.add(new BasicNameValuePair("last", ""));
            parameter.add(new BasicNameValuePair("page", "0"));
            parameter.add(new BasicNameValuePair("start_date", fromdate));
            parameter.add(new BasicNameValuePair("end_date", enddate));
            parameter.add(new BasicNameValuePair("start_time", ""));
            parameter.add(new BasicNameValuePair("end_time", ""));
            Log.d("URL PARAMS", fromdate+"/"+enddate);
            Log.d("URL CAMARSIP", url);
            JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
            if(String.valueOf(json).equals("null")){
                return "Exception Caught";
            }else {
                Log.d("RESULT DATA", String.valueOf(json));
                return String.valueOf(json);
            }
        }
    }


}
