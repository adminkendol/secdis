package lampung.dispenda.cctv;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Chandra on 4/2/2016.
 */
public class Session {
    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    private SharedPreferences pref;
    int PRIVATE_MODE = 0;
    //String PREF_NAME = "sesdata";
    public Session(Context context){
        //this._context = context;
        pref = context.getSharedPreferences("sesdata", Context.MODE_PRIVATE);
        editor = pref.edit();
    }



    public void setdata(String data) {
        editor.putString("data", data).commit();
        editor.commit();
    }

    public String getdata() {
        String data = pref.getString("data","");
        return data;
    }

    public void setToken(String data) {
        editor.putString("token", data).commit();
        editor.commit();
    }

    public String getToken() {
        String data = pref.getString("token","");
        return data;
    }
}
