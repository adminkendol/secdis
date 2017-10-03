package lampung.dispenda.cctv;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lampung.dispenda.cctv.config.Basic;
import lampung.dispenda.cctv.config.UtilLocation;

/**
 * Created by Chandra on 9/6/2016.
 */
public class Signin extends AppCompatActivity{

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;

    final Basic config = new Basic();
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        UtilLocation ul = new UtilLocation(this);
        String networkInfo= null;
        try {
            networkInfo = ul.getNWInfo("/");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        String[] network = {};
        if(networkInfo==null){
            Log.d("NETWORK INFO:", "ERROR");
        }else {
            Log.d("NETWORK INFO:", networkInfo);
            network = networkInfo.split("/");
        }
        final String[] finalNetwork = network;
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadDataTask m = (ReadDataTask) new ReadDataTask(finalNetwork).execute();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyFirebaseInstanceIDService mfs= new MyFirebaseInstanceIDService(Signin.this);
                mfs.onTokenRefresh();
                //String [] allDevice=config.getDevice();
            }
        });
    }

    class ReadDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        String[] network;
        public ReadDataTask(String[] network) {
            this.network=network;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Signin.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... sText) {
            String returnResult = null;
            try {
                returnResult = login(network);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return returnResult;
        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            ScrollView sv =(ScrollView) findViewById(R.id.sv_login);
            if (result.equalsIgnoreCase("Exception Caught")) {
                //Toast.makeText(Signin.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
                _loginButton.setEnabled(true);
                Snackbar snackbar = Snackbar.make(sv, "Login gagal, cek koneksi internet", Snackbar.LENGTH_LONG);
                snackbar.show();
                //reload("Users");
            }
            if (result.equalsIgnoreCase("no results")) {
                //Toast.makeText(Signin.this, "Data empty", Toast.LENGTH_LONG).show();
                _loginButton.setEnabled(true);
                Snackbar snackbar = Snackbar.make(sv, "Login gagal, cek koneksi internet", Snackbar.LENGTH_LONG);
                snackbar.show();
                //reload("Users");

            } else {
                //_loginButton.setEnabled(false);
                try {
                    JSONObject resLogin = new JSONObject(result);
                    int success = resLogin.getInt("success");
                    if (success == 1) {
                        //final Basic config=new Basic();
                        String ver =config.getVer();
                        String data = resLogin.getString("data");
                        JSONObject jsonObj = new JSONObject(data);
                        JSONArray data_profile = jsonObj.getJSONArray("version");
                        final JSONObject b = data_profile.getJSONObject(0);
                        Log.d("device version", ver+"/"+b.getString("param1"));
                        if(ver.equals(b.getString("param1"))){
                        //if(ver.equals("1.0.1")){
                            Log.d("data session", data);
                            Intent intentB = new Intent(Signin.this, Barlay.class);
                            intentB.putExtra("isi", "Sukses Login");
                            intentB.putExtra("data", data);
                            //global variable
                            Session session = new Session(getApplicationContext());
                            session.setdata(data);
                            startActivity(intentB);
                            _loginButton.setEnabled(true);

                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Signin.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                            builder.setTitle("Info Update");
                            builder.setMessage("Version:"+b.getString("param1")+"\nInfo: "+b.getString("info"))
                                    .setCancelable(false)
                                    .setPositiveButton("UPDATE", new
                                            DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //android.os.Process.killProcess(android.os.Process.myPid());
                                                    ProgressDialog mProgressDialog;
                                                    mProgressDialog = new ProgressDialog(Signin.this);
                                                    mProgressDialog.setMessage("Download on progress...");
                                                    mProgressDialog.setIndeterminate(false);
                                                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                                    mProgressDialog.setProgressNumberFormat(null);
                                                    UpdateApp atualizaApp = new UpdateApp(mProgressDialog);
                                                    atualizaApp.setContext(getApplicationContext());
                                                    try {
                                                        atualizaApp.execute(b.getString("param2"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        android.os.Process.killProcess(android.os.Process.myPid());
                                                    }
                                                }
                                            });
                            AlertDialog a=builder.create();
                            a.show();
                            Button bq = a.getButton(DialogInterface.BUTTON_POSITIVE);
                            bq.setBackgroundColor(Color.BLACK);
                            bq.setTextColor(Color.WHITE);
                        }
                    }else{
                        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                        _loginButton.setEnabled(true);
                        Snackbar snackbar = Snackbar.make(sv, "Login gagal, user atau password salah", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        private String login(String[] network) throws Throwable {
            //Basic config = new Basic();
            //global variable
            Session session = new Session(getApplicationContext());

            String allDevice=config.getDeviceInfo("/");
            Log.d("DEVICE INFO:",allDevice);
            String [] device =allDevice.split("/");
            String token = session.getToken();
            Log.d("FBase token", token);

            String url = config.getLogin();
            JSONParser jParser = new JSONParser();
            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("un", email));
            parameter.add(new BasicNameValuePair("pass", password));
            parameter.add(new BasicNameValuePair("token", token));
            parameter.add(new BasicNameValuePair("device_id", device[12]));
            parameter.add(new BasicNameValuePair("brand", device[1]));
            parameter.add(new BasicNameValuePair("operator", network[2]));
            parameter.add(new BasicNameValuePair("mcc", network[3]));
            parameter.add(new BasicNameValuePair("mnc", network[4]));
            parameter.add(new BasicNameValuePair("lac", network[0]));
            parameter.add(new BasicNameValuePair("cid", network[1]));
            parameter.add(new BasicNameValuePair("address",""));
                    Log.d("URL SIGNIN", url);
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
