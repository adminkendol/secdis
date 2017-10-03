package lampung.dispenda.cctv;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lampung.dispenda.cctv.chart.Bar;
import lampung.dispenda.cctv.config.Basic;

/**
 * Created by Chandra on 5/5/2016.
 * Function Activity login CCTV
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String LOGIN_URL = "http://222.124.4.226:8030/app/api/signin";
    //private static final String LOGIN_URL = "http://192.168.42.115/cctv/api/signin";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_RESPON = 0;
    private Session session;//global variable

    private Basic fakedata;
    private Basic config;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    //@Bind(R.id.link_signup) TextView _signupLink;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
       /*
        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });*/
    }
    /*public String login() {
        _loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...please wait");
        progressDialog.show();
        final String data;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        fakedata = new Basic();
        data = fakedata.getResultSignin();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess(data);
                        progressDialog.dismiss();
                    }
                }, 1000);
        return data;
    }*/
    public String login() {
        Log.d(TAG, "Login");
        /*if (!validate()) {
            onLoginFailed();
            return null;
        }*/
        _loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...please wait");
        progressDialog.show();
        int success;
        final String data;
        JSONParser jsonParser;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        // TODO: Implement your own authentication logic here.
        try {
            // Building Parameters
            jsonParser = new JSONParser();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("un", email));
            params.add(new BasicNameValuePair("pass", password));
            Log.d("request!",LOGIN_URL + String.valueOf(params));
            final JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
            Log.d("response login!", String.valueOf(json));
            // check your log for json response
            //Log.d("Login attempt", json.toString());
            // json success tag
            //if(json.isNull(TAG_SUCCESS)){
            if(json==null){
                onLoginFailed();
            }
            success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                data = json.getString(TAG_DATA);
                Log.d("Login Successful!", json.toString());
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                onLoginSuccess(data);
                                progressDialog.dismiss();
                            }
                        }, 1000);
                return json.getString(TAG_DATA);

            }else{
                Log.d("Login Failure!", "Internet kacau");
                progressDialog.dismiss();
                onLoginFailed();
            }
        } catch (JSONException e) {

            Log.d("Login Failure!", "Internet kampret");
            e.printStackTrace();
            progressDialog.dismiss();
            onLoginFailed();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
        //finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /*int backButtonCount = 0;
    public void onBackPressed(){
        if(backButtonCount >= 1){
            finish();
        }else{
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }*/
    public void onLoginSuccess(String data) {
        //startActivity(intentB);
        Log.d("data session", data);
        Intent intentB = new Intent(this, Bar.class);
        intentB.putExtra("isi","Sukses Login");
        intentB.putExtra("data",data);
        //session = new Session(context);
        session = new Session(getApplicationContext());
        session.setdata(data);
        startActivity(intentB);
        _loginButton.setEnabled(true);
        //finish();
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
