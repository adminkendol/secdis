package lampung.dispenda.cctv;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lampung.dispenda.cctv.chart.Bar;
import lampung.dispenda.cctv.config.Basic;

/**
 * Created by Chandra on 5/5/2016.
 * Function Activity List CCTV
 */
public class ListCctv extends AppCompatActivity {
    ListView list;
    JSONParser jParser = new JSONParser();
    ArrayList<Cctv> daftar_cctv = new ArrayList<Cctv>();
    JSONArray daftarCctv = null;
    private Basic config;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_CCTV = "data";
    public static final String TAG_ID_CCTV = "camera_id";
    public static final String TAG_STATUS = "status";
    public static final String TAG_IP_CCTV = "ip";
    public static final String TAG_NAMA_CCTV = "camera_name";
    public static final String TAG_LOC_CCTV = "location_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cctv_main);
        ReadCctvTask m= (ReadCctvTask) new ReadCctvTask().execute();
        list = (ListView) findViewById(R.id.listview_cctv);

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
    }





    class ReadCctvTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListCctv.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            Intent iin= getIntent();
            Bundle b = iin.getExtras();
            Object id = b.get("id");
            Object loc = b.get("loc");
            String ids = (String) id;
            String locs;
            Log.d("INTENT", "ISI:" + id);
            if (id.equals("1")) {
                setTitle("CCTV Online");
                locs = "0";
            }else if (id.equals("2")) {
                setTitle("CCTV Offline");
                locs = "0";
            }else{
                locs = (String) loc;
                Object t = b.get("title");
                String title = (String) t;
                setTitle(title);
            }
            String returnResult = getCctvList(ids,locs);
            return returnResult;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Intent iin= getIntent();
            Bundle b = iin.getExtras();
            Object id = b.get("id");
            Object loc = b.get("loc");
            String ids = (String) id;
            String locs;
            String title;
            if (id.equals("1")) {
                title = "CCTV Online";
                locs = "0";
            }else if (id.equals("2")) {
                title = "CCTV Offline";
                locs = "0";
            }else{
                locs = (String) loc;
                Object t = b.get("title");
                title = (String) t;

            }
            if(result.equalsIgnoreCase("Exception Caught")){
                Toast.makeText(ListCctv.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
                reload(ids,locs,title);
            }
            if(result.equalsIgnoreCase("no results")){
                Toast.makeText(ListCctv.this, "Data empty", Toast.LENGTH_LONG).show();
                reload(ids, locs, title);
            }
            else{
                list.setAdapter(new ListAdapter(ListCctv.this,daftar_cctv));
            }
        }
        public String getCctvList(String id, String loc) {
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("id", id));
            parameter.add(new BasicNameValuePair("loc", loc));
            config = new Basic();
            String url = config.getCAMLIST();
            String urlCover = config.getCAMCOVER();
            String urlOffline = config.getCAMOFFLINE();
            try {
                JSONObject json = jParser.makeHttpRequest(url,"POST", parameter);
                Log.i("RESULT DATA", json.toString());
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) { //Ada record Data (SUCCESS = 1)
                    //Getting Array of daftar_cctv
                    daftarCctv = json.getJSONArray(TAG_CCTV);

                    for (int i = 0; i < daftarCctv.length() ; i++){
                        Cctv tempCctv = new Cctv();
                        JSONObject c = daftarCctv.getJSONObject(i);
                        tempCctv.setCctvId(c.getString(TAG_ID_CCTV));
                        tempCctv.setCctvIp(c.getString(TAG_IP_CCTV));
                        tempCctv.setCctvName(c.getString(TAG_NAMA_CCTV));
                        tempCctv.setCctvStatus(c.getString(TAG_STATUS));
                        tempCctv.setLocCctv(c.getString(TAG_LOC_CCTV));
                        tempCctv.setLocList(loc);
                        tempCctv.setIdList(id);
                        if(c.getString(TAG_STATUS).equals("1")) {
                            tempCctv.setCapture(urlCover + c.getString(TAG_ID_CCTV) + ".jpg");
                        }else{
                            tempCctv.setCapture(urlOffline);
                        }
                        Log.i("RESULT IMG", urlCover + c.getString(TAG_ID_CCTV));
                        daftar_cctv.add(tempCctv);
                    }
                    return "OK";
                }
                else {
                    //Tidak Ada Record Data (SUCCESS = 0)
                    return "no results";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }

    }
    private void reload(final String ids, final String locs, final String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Reconnecting....")
                .setCancelable(false)
                .setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Intent intent;
                                intent = new Intent(ListCctv.this, ListCctv.class);
                                intent.putExtra("id", ids);
                                intent.putExtra("loc", locs);
                                intent.putExtra("title", title);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Tidak",new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                Intent intent;
                                intent = new Intent(ListCctv.this, Bar.class);
                                startActivity(intent);
                            }

                        }).show();
    }
    /*private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Kamu Benar-Benar ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                ListCctv.this.finish();
                            }
                        })
                .setNegativeButton("Tidak",new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                dialog.cancel();
                            }

                        }).show();
    }*/

}
