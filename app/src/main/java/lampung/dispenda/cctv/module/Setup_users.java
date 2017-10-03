package lampung.dispenda.cctv.module;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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
import lampung.dispenda.cctv.adapter.AdapterListView;
import lampung.dispenda.cctv.config.Basic;
import lampung.dispenda.cctv.module.Setup_form_users.myOnClickListener;
/**
 * Created by Chandra on 5/21/2016.
 * Activity Setup users
 */
public class Setup_users extends AppCompatActivity {

    JSONParser jParser = new JSONParser();
    private static final String TAG_SUCCESS ="success";
    public myOnClickListener myListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        setContentView(R.layout.users);
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
        setTitle(title);
        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab);
        ReadDataTask m= (ReadDataTask) new ReadDataTask().execute();
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myListener = new myOnClickListener() {
                    @Override
                    public void onButtonClick() {
                        finish();
                        startActivity(getIntent());
                        LinearLayout ll_users =(LinearLayout) findViewById(R.id.ll_users);
                        Snackbar snackbar = Snackbar.make(ll_users, "Menambahkan user berhasil", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                };
                Setup_form_users form = new Setup_form_users(Setup_users.this,myListener,"nok");

                form.setTitle("FORM USER");
                form.show();
            }
        });

    }



    class ReadDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Setup_users.this);
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
            Basic config = new Basic();
            String url = config.getUrl();
            LinearLayout ll =(LinearLayout) findViewById(R.id.ll_users);
            if (result.equalsIgnoreCase("Exception Caught")) {
                Log.d("RESULT API", "data:" + String.valueOf(result) + ":END");
                Toast.makeText(Setup_users.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(ll, "Load data gagal", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReadDataTask m= (ReadDataTask) new ReadDataTask().execute();
                    }
                }).show();
                snackbar.show();
            }else if (result.equalsIgnoreCase("no results")) {
                Toast.makeText(Setup_users.this, "Data empty", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(ll, "Load data gagal", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReadDataTask m= (ReadDataTask) new ReadDataTask().execute();
                    }
                }).show();
                snackbar.show();
            } else {
                JSONArray dataUsers = null;
                try {
                    dataUsers = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Array data JSON USERS", String.valueOf(result));
                try {
                    String[] user_id=new String[dataUsers.length()];
                    String[] user_fullname=new String[dataUsers.length()];
                    String[] user_name=new String[dataUsers.length()];
                    String[] group_type_id=new String[dataUsers.length()];
                    String[] location_id=new String[dataUsers.length()];
                    String[] active_id=new String[dataUsers.length()];
                    String[] email=new String[dataUsers.length()];
                    String[] phone=new String[dataUsers.length()];
                    String[] mobile_phone=new String[dataUsers.length()];
                    String[] dp_name=new String[dataUsers.length()];
                    String[] status_name=new String[dataUsers.length()];
                    String[] group_type_name=new String[dataUsers.length()];
                    String[] location_name=new String[dataUsers.length()];
                    Log.d("Array data Users", String.valueOf(dataUsers));

                    for (int i = 0; i < dataUsers.length(); i++) {
                        JSONObject c = dataUsers.getJSONObject(i);
                        user_id[i] = c.getString("user_id");
                        user_fullname[i] = c.getString("user_fullname");
                        user_name[i] = c.getString("user_name");
                        group_type_id[i] = c.getString("group_type_id");
                        location_id[i] = c.getString("location_id");
                        active_id[i] = c.getString("active_id");
                        email[i] = c.getString("email");
                        phone[i] = c.getString("phone");
                        mobile_phone[i] = c.getString("mobile_phone");
                        dp_name[i] = c.getString("dp_name");
                        status_name[i] = c.getString("status_name");
                        group_type_name[i] = c.getString("group_type_name");
                        //location_name[i] = c.getString("location_name");
                    }
                    AdapterListView adapter = new AdapterListView(Setup_users.this,user_id,user_fullname,user_name,group_type_id,location_id,active_id,email,phone,mobile_phone,dp_name,status_name,group_type_name,location_name);
                    RecyclerView myView =  (RecyclerView) findViewById(R.id.parentView);
                    myView.setHasFixedSize(true);
                    myView.setAdapter(adapter);
                    LinearLayoutManager llm = new LinearLayoutManager(Setup_users.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    myView.setLayoutManager(llm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        private String BuildTable() {
            Basic config = new Basic();
            String url = config.getSETUP_LIST_USER();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("id", "0"));
            parameter.add(new BasicNameValuePair("loc", "1"));
            Log.d("URL DATA", url);
            try {
                JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
                if(String.valueOf(json).equals("null")){
                    //reload("Users");
                    return "Exception Caught";
                }else {
                    Log.d("RESULT DATA", String.valueOf(json));
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
