package lampung.dispenda.cctv.module;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lampung.dispenda.cctv.JSONParser;
import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.adapter.GmailAdapter;
import lampung.dispenda.cctv.config.Basic;
import lampung.dispenda.cctv.module.Setup_form_loc.LocklikListener;
/**
 * Created by Chandra on 10/9/2016.
 */
public class Setup_location extends AppCompatActivity {
    JSONParser jParser = new JSONParser();
    private static final String TAG_SUCCESS ="success";
    RecyclerView recyclerView;
    GmailAdapter adapter;

    public LocklikListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        setContentView(R.layout.setup_location);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.gmail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);


        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab);
        ReadDataTask m= (ReadDataTask) new ReadDataTask().execute();
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myListener = new Setup_form_loc.LocklikListener() {
                    @Override
                    public void onButtonClick() {
                        finish();
                        startActivity(getIntent());
                        CoordinatorLayout cl = (CoordinatorLayout) findViewById(R.id.gmail_coordinator);
                        Snackbar snackbar = Snackbar.make(cl, "Menambahkan location berhasil", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                };
                Setup_form_loc form = new Setup_form_loc(Setup_location.this,myListener,"nok");

                form.setTitle("FORM LOCATION");
                form.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class ReadDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Setup_location.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... sText) {
            String returnResult = BuildTable();
            return returnResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            recyclerView = (RecyclerView) findViewById(R.id.gmail_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(Setup_location.this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            if (result.equalsIgnoreCase("Exception Caught")) {
                Log.d("RESULT API", "data:" + String.valueOf(result) + ":END");
                Toast.makeText(Setup_location.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();

            }else if (result.equalsIgnoreCase("no results")) {
                Toast.makeText(Setup_location.this, "Data empty", Toast.LENGTH_LONG).show();

            } else {
                JSONArray dataLoc = null;
                try {
                    dataLoc = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Array data JSON LOCATION", String.valueOf(result));
                try {
                    Log.d("Array data Location", String.valueOf(dataLoc));
                    List<String> listDataA = new ArrayList<String>();
                    List<String> listDataB = new ArrayList<String>();
                    List<String> listDataC = new ArrayList<String>();
                    List<String> listDataD = new ArrayList<String>();
                    for (int i = 0; i < dataLoc.length(); i++) {
                        JSONObject c = dataLoc.getJSONObject(i);
                        listDataA.add(c.getString("menu_id"));
                        listDataB.add(c.getString("menu_name"));
                        listDataC.add(c.getString("city_name"));
                        listDataD.add(c.getString("province"));
                    }
                    //if (adapter == null) {
                        adapter = new GmailAdapter(Setup_location.this, listDataA, listDataB, listDataC, listDataD);
                        recyclerView.setAdapter(adapter);
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        private String BuildTable() {
            Basic config = new Basic();
            String url = config.getSETUP_LIST_LOC();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("id", "0"));
            parameter.add(new BasicNameValuePair("loc", "1"));
            Log.d("URL DATA LOCATION", url);
            try {
                JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
                if(String.valueOf(json).equals("null")){
                    return "Exception Caught";
                }else {
                    Log.d("RESULT DATA LOCATION", String.valueOf(json));
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        JSONArray daftarUser = json.getJSONArray("data");
                        return String.valueOf(daftarUser);
                    } else {
                        return "no results";
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
                Log.d("URL DATA ERROR", String.valueOf(e));
                return "Exception Caught";
            }
            //return "Exception Caught";
        }
    }

}
