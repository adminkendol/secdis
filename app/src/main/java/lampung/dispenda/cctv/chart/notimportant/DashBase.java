
package lampung.dispenda.cctv.chart.notimportant;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lampung.dispenda.cctv.DrawerActivity;
import lampung.dispenda.cctv.JSONParser;
import lampung.dispenda.cctv.R;
import lampung.dispenda.cctv.config.Basic;

/**
 * Baseclass of all Activities of Dashboard.
 *
 * @author Chandra
 */
public abstract class DashBase extends DrawerActivity {
    JSONParser jParser = new JSONParser();
    protected String[] mBar = new String[] {
            "Online", "Offline", "Memory", "Storage"
    };
    public String[] dash() throws JSONException {
        Basic config = new Basic();
        String url = config.getDASHBOARD();
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("uid", "1"));
        parameter.add(new BasicNameValuePair("loc", "0"));
        Log.d("URL DATA", url);
        try {
            JSONObject json = jParser.makeHttpRequest(url, "POST", parameter);
            if(String.valueOf(json).equals("null")){
                return new String[]{"Exception Caught"};
            }else {
                Log.d("RESULT DATA", String.valueOf(json));
                JSONObject isiDash = json.getJSONObject("data");
                String online = isiDash.getString("online");
                String offline = isiDash.getString("offline");
                int mem_usage = Math.round(isiDash.getInt(String.valueOf("mem_usage")));
                String mem = Integer.toString(mem_usage);
                int hd_usage = Math.round(isiDash.getInt(String.valueOf("hd_usage")));
                String hd = Integer.toString(hd_usage);
                String[] mNum = new String[]{online,offline,mem,hd};
                return mNum;
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d("URL DATA ERROR", String.valueOf(e));
            return new String[]{"Exception Caught"};
        }
    }
    public String[] infoServer() throws JSONException {
        Basic config = new Basic();
        String url = config.getInfoServer();
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("out", "json"));
        Log.d("URL DATA", url);
        try {
            JSONObject json = jParser.makeHttpRequest(url, "GET", parameter);
            if(String.valueOf(json).equals("null")){
                //reload("Users");
                return new String[]{"Exception Caught"};
            }else {
                Log.d("RESULT DATA", String.valueOf(json));
                String info_s = json.getString("OS");
                JSONObject distro_array = json.getJSONObject("Distro");
                String distro = distro_array.getString("name");
                String version = distro_array.getString("version");
                String host = json.getString("HostName");
                JSONObject uptime = json.getJSONObject("UpTime");
                String uptime_text = uptime.getString("text");
                JSONArray cpu = json.getJSONArray("CPU");
                JSONObject cpu_0 = cpu.getJSONObject(0);
                String cpu_0_model = cpu_0.getString("Model");
                JSONArray temps = json.getJSONArray("Temps");
                JSONObject temps_0 = temps.getJSONObject(5);
                String temps_0_temp = temps_0.getString("temp");
                String temps_0_unit = temps_0.getString("unit");
                String numLoggedIn = json.getString("numLoggedIn");
                String [] infoS = new String[]{info_s,distro,version,host,uptime_text,cpu_0_model,temps_0_temp,temps_0_unit,numLoggedIn};
                return infoS;
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d("URL DATA ERROR", String.valueOf(e));
            return new String[]{"Exception Caught"};
        }
        //return "Exception Caught";
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }


}
