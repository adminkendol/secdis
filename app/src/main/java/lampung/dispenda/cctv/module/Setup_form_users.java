package lampung.dispenda.cctv.module;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import lampung.dispenda.cctv.adapter.PostData;
import lampung.dispenda.cctv.config.Basic;

/**
 * Created by Chandra on 5/21/2016.
 * Setup form user activity
 */
public class Setup_form_users extends Dialog {

    private Toolbar supportActionBar;
    JSONParser jParser = new JSONParser();
    private static final String TAG_SUCCESS ="success";

    ScrollView svForm;
    ProgressBar pbForm;
    Spinner loc;
    Spinner gt;
    Context context;
    String idx;
    EditText un;
    EditText fn;
    EditText emailA;
    EditText pass;

    public Setup_form_users(Context context, myOnClickListener myclick,String idx) {
        super(context);
        this.myListener = myclick;
        this.context=context;
        setContentView(R.layout.activity_signup);
        this.idx=idx;
        this.un = (EditText) findViewById(R.id.username);
        this.fn = (EditText) findViewById(R.id.input_name);
        this.emailA = (EditText) findViewById(R.id.input_email);
        this.pass = (EditText) findViewById(R.id.input_password);
        this.gt = (Spinner) findViewById(R.id.gt);
        this.loc = (Spinner) findViewById(R.id.location);
        this.svForm=(ScrollView) findViewById(R.id.svForm);
        this.pbForm=(ProgressBar) findViewById(R.id.pbForm);
    }
    public myOnClickListener myListener;
    public interface myOnClickListener {
        void onButtonClick();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //getContext().setTheme(R.style.DialogTheme);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_signup);
        //RelativeLayout rl = (RelativeLayout)findViewById(R.id.rlA);
        //rl.setBackgroundColor(Color.LTGRAY);

        Button submit = (Button) findViewById(R.id.btn_signup);
        Log.d("ID USER:",idx);
        if(idx!="nok"){
            ReadDataId m= (ReadDataId) new ReadDataId().execute();
            submit.setText("UPDATE DATA USER");
        }
        //Bundle bundle = getIntent().getExtras();
        //String title = bundle.getString("title");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setTitle(title);
        Basic fake = new Basic();
        JSONArray location = null;
        JSONArray grouptype = null;
        try {
            location = fake.getSelect(1);
            grouptype = fake.getSelect(2);
            Log.d("ISI GT", "DATA:" + grouptype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //loc = (Spinner) findViewById(R.id.location);
        /*-------------function gt-----------------*/
        //gt = (Spinner) findViewById(R.id.gt);
        final EditText gtid = (EditText) findViewById(R.id.gtId);
        int h;
        final ArrayList<String> dataGt = new ArrayList<String>();
        final ArrayList<String> idGt = new ArrayList<String>();
        for(h=0;h<grouptype.length();h++){
            JSONObject b =null;
            try {
                b = grouptype.getJSONObject(h);
                Log.d("ARRAY GT", "DATA:" + b);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                dataGt.add(b.getString("group_type_name"));
                idGt.add(b.getString("group_type_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_dropdown_item_1line, dataGt);
        gt.setAdapter(adp1);
        gt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                gtid.setText(idGt.get(arg2).toString());
                if(idGt.get(arg2).toString().equals("3")){
                    loc.setVisibility(View.VISIBLE);
                }else{
                    loc.setVisibility(View.INVISIBLE);
                }
                //Toast.makeText(getBaseContext(), dataGt.get(arg2).toString(),Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
           }
        });
        /*-------------end function gt-----------------*/

        /*-------------function location-----------------*/
        final EditText id = (EditText) findViewById(R.id.locId);
        int i;
        final ArrayList<String> dataLoc = new ArrayList<String>();
        final ArrayList<String> idLoc = new ArrayList<String>();
        for(i=0;i<location.length();i++){
            JSONObject c =null;
            try {
                c = location.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                dataLoc.add(c.getString("location_name"));
                idLoc.add(c.getString("location_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_dropdown_item_1line, dataLoc);
        loc.setAdapter(adp2);
        loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                id.setText(idLoc.get(arg2).toString());
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        /*-------------end function location-----------------*/
        /*-------------submit form-----------------*/
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editable getUn = un.getText();
                Editable getFn = fn.getText();
                Editable getEmail = emailA.getText();
                Editable getPass = pass.getText();
                Editable getGtid = gtid.getText();
                Editable getIdloc = id.getText();
                Editable[] arg ={getUn,getFn,getEmail,getPass,getGtid,getIdloc};
                if (!validate(arg)) {
                    onSuck();
                }else{
                    String getUnX = String.valueOf(un.getText());
                    String getFnx = String.valueOf(fn.getText());
                    String getEmailx = String.valueOf(emailA.getText());
                    String getPassx = String.valueOf(pass.getText());
                    String getGtidx = String.valueOf(gtid.getText());
                    String getIdlocx;
                    String settype;
                    Log.d("ID USER SUBMIT:", idx);
                    if(idx.equals("nok")){
                        settype="1";
                    }else{
                        settype="2";
                    }
                    List<NameValuePair> param = new ArrayList<NameValuePair>();
                    param.add(new BasicNameValuePair("settype", settype));
                    param.add(new BasicNameValuePair("user_id", idx));
                    param.add(new BasicNameValuePair("user_fullname", getFnx));
                    param.add(new BasicNameValuePair("user_name", getUnX));
                    param.add(new BasicNameValuePair("email", getEmailx));
                    param.add(new BasicNameValuePair("user_password", getPassx));
                    param.add(new BasicNameValuePair("group_type_id", getGtidx));
                    if(getGtidx.equals("3")){
                        getIdlocx = String.valueOf(id.getText());
                    }else{
                        getIdlocx ="0";
                    }
                    param.add(new BasicNameValuePair("location_id", getIdlocx));
                    new PostData(getContext(),param,"user").execute();
                    myListener.onButtonClick();
                }

            }

        });
        /*-------------end submit form-----------------*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onSuck() {
        Toast.makeText(getContext(), "Submit error, please check parameter", Toast.LENGTH_LONG).show();
    }
    public boolean validate(Editable[] arg) {
        boolean valid = true;
        Editable un =arg[0];Editable fn =arg[1];Editable email =arg[2];Editable pass =arg[3];Editable gtId =arg[4];Editable idLoc =arg[5];
        String unT = un.toString();String fnT = fn.toString();String emailT = email.toString();String passT = pass.toString();String gtidT = gtId.toString();String idlocT = idLoc.toString();
        if(unT.isEmpty() || fnT.isEmpty() || emailT.isEmpty() || passT.isEmpty() || gtidT.isEmpty()){
            valid = false;
        }else{
            if(gtidT.equals("3")){
                if(idlocT.isEmpty()){
                    valid = false;
                }else{
                    valid = true;
                }
            }else{
                valid = true;
            }
            valid = true;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailT).matches()){
            valid = false;
        }else{
            valid = true;
        }

        return valid;
    }

    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }

    class ReadDataId extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbForm.setVisibility(View.VISIBLE);
            svForm.setVisibility(View.GONE);
        }
        @Override
        protected String doInBackground(String... sText) {
            String returnResult = BuildTable();
            return returnResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pbForm.setVisibility(View.GONE);
            svForm.setVisibility(View.VISIBLE);
            if (result.equalsIgnoreCase("Exception Caught")) {
                Log.d("RESULT API", "data:" + String.valueOf(result) + ":END");
                Toast.makeText(getContext(), "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();

            }else if (result.equalsIgnoreCase("no results")) {
                Toast.makeText(getContext(), "Data empty", Toast.LENGTH_LONG).show();

            } else {
                JSONArray dataUsers = null;
                try {
                    dataUsers = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Array data JSON USERS ID", String.valueOf(result));
                try {
                    Log.d("Array data Users", String.valueOf(dataUsers));

                    JSONObject c = dataUsers.getJSONObject(0);
                    String user_id = c.getString("user_id");
                    String user_fullname = c.getString("user_fullname");
                    String user_name = c.getString("user_name");
                    String group_type_id = c.getString("group_type_id");
                    String location_id = c.getString("location_id");
                    String active_id = c.getString("active_id");
                    String email = c.getString("email");
                    String phone = c.getString("phone");
                    String mobile_phone = c.getString("mobile_phone");
                    String dp_name = c.getString("dp_name");
                    String status_name = c.getString("status_name");
                    String group_type_name = c.getString("group_type_name");
                    String location_name = c.getString("location_name");
                    un.setText(user_name);
                    fn.setText(user_fullname);
                    emailA.setText(email);

                    gt.setSelection(((ArrayAdapter<String>)gt.getAdapter()).getPosition(group_type_name));
                    if(group_type_id.equals("3")){
                        loc.setSelection(((ArrayAdapter<String>)loc.getAdapter()).getPosition(location_name));
                    }
                    //pass.setText(email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        private String BuildTable() {
            Basic config = new Basic();
            String url = config.getSETUP_LIST_USER_ID();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("user_id", idx));
            parameter.add(new BasicNameValuePair("loc", "1"));
            Log.d("URL DATA USER ID", url+"\nParams:"+parameter);
            try {
                JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
                if(String.valueOf(json).equals("null")){
                    return "Exception Caught";
                }else {
                    Log.d("RESULT DATA USER ID", String.valueOf(json));
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
