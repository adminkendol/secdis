package lampung.dispenda.cctv.module;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import lampung.dispenda.cctv.adapter.PostData;
import lampung.dispenda.cctv.config.Basic;

/**
 * Created by Chandra on 10/12/2016.
 */
public class Setup_form_loc extends android.app.Dialog {

    JSONParser jParser = new JSONParser();
    private static final String TAG_SUCCESS ="success";
    ScrollView svForm;
    ProgressBar pbForm;
    Spinner spProv;
    Spinner spCity;
    Context context;
    String idx;
    EditText locName;

    public Setup_form_loc(Context context, LocklikListener myclick,String idx) {
        super(context);
        this.myListener = myclick;
        this.context=context;
        setContentView(R.layout.location_form);
        this.idx=idx;
        this.locName = (EditText) findViewById(R.id.locName);
        this.spProv = (Spinner) findViewById(R.id.spProv);
        this.spCity = (Spinner) findViewById(R.id.spCity);
        this.svForm=(ScrollView) findViewById(R.id.svForm);
        this.pbForm=(ProgressBar) findViewById(R.id.pbLocForm);
    }
    public LocklikListener myListener;
    public interface LocklikListener {
        void onButtonClick();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button submit = (Button) findViewById(R.id.btn_save);
        final Basic api = new Basic();
        String prov = api.listProv();
        Log.d("STRING PROV:",prov);
        JSONArray dataProv = null;
        try {
            dataProv = new JSONArray(prov);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*-------------function prov-----------------*/
        final EditText provId = (EditText) findViewById(R.id.provId);
        int h;
        final ArrayList<String> prov_name = new ArrayList<String>();
        final ArrayList<String> prov_id = new ArrayList<String>();
        for(h=0;h<dataProv.length();h++){
            JSONObject b =null;
            try {
                b = dataProv.getJSONObject(h);
                //Log.d("ARRAY PROV", "DATA:" + b);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                prov_id.add(b.getString("province_id"));
                prov_name.add(b.getString("province"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final EditText cityId = (EditText) findViewById(R.id.cityId);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_dropdown_item_1line, prov_name);
        spProv.setAdapter(adp1);
        spProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                provId.setText(prov_id.get(arg2).toString());
                if(idx.equals("nok")) {
                /*-------------function city-----------------*/
                    String city = api.listCity(prov_id.get(arg2).toString());
                    Log.d("STRING CITY:", city);
                    JSONArray dataCity = null;
                    try {
                        dataCity = new JSONArray(city);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int h;
                    final ArrayList<String> city_name = new ArrayList<String>();
                    final ArrayList<String> city_id = new ArrayList<String>();
                    for (h = 0; h < dataCity.length(); h++) {
                        JSONObject b = null;
                        try {
                            b = dataCity.getJSONObject(h);
                            //Log.d("ARRAY PROV", "DATA:" + b);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            city_id.add(b.getString("city_id"));
                            city_name.add(b.getString("city_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                            (getContext(), android.R.layout.simple_dropdown_item_1line, city_name);
                    spCity.setAdapter(adp1);
                    spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int arg2, long arg3) {
                            cityId.setText(city_id.get(arg2).toString());
                        }

                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                /*-------------end function city-----------------*/
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        /*-------------end function prov-----------------*/
        Log.d("ID LOC:", idx);
        if(idx!="nok"){
            ReadDataId m= (ReadDataId) new ReadDataId().execute();
            submit.setText("UPDATE DATA LOCATION");
        }

        /*-------------submit form-----------------*/
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editable getCity = cityId.getText();
                Editable getlocName = locName.getText();
                Editable[] arg = {getCity, getlocName};
                if (!validate(arg)) {
                    onSuck();
                } else {
                    String getC = String.valueOf(cityId.getText());
                    String getL = String.valueOf(locName.getText());
                    String settype;
                    Log.d("ID LOC SUBMIT:", idx);
                    if (idx.equals("nok")) {
                        settype = "1";
                    } else {
                        settype = "2";
                    }
                    List<NameValuePair> param = new ArrayList<NameValuePair>();
                    param.add(new BasicNameValuePair("settype", settype));
                    param.add(new BasicNameValuePair("location_id", idx));
                    param.add(new BasicNameValuePair("city_id", getC));
                    param.add(new BasicNameValuePair("location_name", getL));
                    new PostData(getContext(), param,"loc").execute();
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
        Editable cityId =arg[0];Editable locName =arg[1];
        String val1 = cityId.toString();String val2 = locName.toString();
        if(val1.isEmpty() || val2.isEmpty() ){
            valid = false;
        }
        return valid;
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
                Log.d("Array data JSON LOC ID", String.valueOf(result));
                try {
                    Log.d("Array data Loc", String.valueOf(dataUsers));

                    JSONObject c = dataUsers.getJSONObject(0);
                    String loc_id = c.getString("location_id");
                    String p_id = c.getString("province_id");
                    String loc_name = c.getString("location_name");
                    final String city_nameB = c.getString("city_name");
                    final String city_id = c.getString("city_id");
                    String province = c.getString("province");
                    locName.setText(loc_name);

                    spProv.setSelection(((ArrayAdapter<String>) spProv.getAdapter()).getPosition(province));

                    /*-------------function city-----------------*/
                    final EditText cityId_T = (EditText) findViewById(R.id.cityId);
                    final Basic api = new Basic();
                    String city = api.listCity(p_id);
                    Log.d("STRING CITY:", city);
                    JSONArray dataCity = null;
                    try {
                        dataCity = new JSONArray(city);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int h;
                    final ArrayList<String> city_name = new ArrayList<String>();
                    final ArrayList<String> city_idA = new ArrayList<String>();
                    for(h=0;h<dataCity.length();h++){
                        JSONObject b =null;
                        try {
                            b = dataCity.getJSONObject(h);
                            //Log.d("ARRAY PROV", "DATA:" + b);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            city_idA.add(b.getString("city_id"));
                            city_name.add(b.getString("city_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                            (getContext(), android.R.layout.simple_dropdown_item_1line, city_name);
                    spCity.setAdapter(adp1);
                    spCity.setSelection(((ArrayAdapter<String>) spCity.getAdapter()).getPosition(city_nameB));
                    spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int arg2, long arg3) {
                            cityId_T.setText(city_idA.get(arg2).toString());
                        }

                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                    /*-------------end function city-----------------*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        private String BuildTable() {
            Basic config = new Basic();
            String url = config.getSETUP_LIST_LOC_ID();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("location_id", idx));
            Log.d("URL DATA LOC ID", url+"\nParams:"+parameter);
            try {
                JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
                if(String.valueOf(json).equals("null")){
                    return "Exception Caught";
                }else {
                    Log.d("RESULT DATA LOC ID", String.valueOf(json));
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
