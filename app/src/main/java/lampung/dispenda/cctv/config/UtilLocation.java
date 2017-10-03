package lampung.dispenda.cctv.config;

/**
 * Created by Chandra on 10/1/2016.
 */

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;

import java.util.List;

public class UtilLocation extends AppCompatActivity{
    Context mContext;
    public UtilLocation(Context mContext) {
        this.mContext = mContext;
    }
    public static Location getLastKnownLoaction(boolean enabledProvidersOnly, Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location utilLocation = null;
        List<String> providers = manager.getProviders(enabledProvidersOnly);
        for(String provider : providers){

            utilLocation = manager.getLastKnownLocation(provider);
            if(utilLocation != null) return utilLocation;
        }
        return null;
    }
    public int [] getMcc(){
        TelephonyManager tel = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();
        List<NeighboringCellInfo> neighbours = tel.getNeighboringCellInfo();
        Log.d("LIST TOWER:", String.valueOf(neighbours));
        int mcc = 0;
        int mnc = 0;
        if (TextUtils.isEmpty(networkOperator) == false) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
        }
        int [] response ={mcc,mnc};
        return response;
    }
    public String getNWInfo(String p_seperator) throws Throwable {
    //public void getNWInfo() throws JSONException {
        /**
         * <uses-permission android:name="android.permission.READ_PHONE_STATE"
         * /> <uses-permission
         * android:name="android.permission.ACCESS_NETWORK_STATE"/>
         */

        TelephonyManager telephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);


        /*if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            final GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
            if (location != null) {
                //msg.setText("LAC: " + location.getLac() + " CID: " + location.getCid());
            }
        }*/
        GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();

        String networkOperator = telephonyManager.getNetworkOperator();
        int mcc = 0, mnc = 0;
        if (networkOperator != null) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
        }

        String SimNumber = telephonyManager.getLine1Number();

        String SimSerialNumber = telephonyManager.getSimSerialNumber();
        String countryISO = telephonyManager.getSimCountryIso();
        String operatorName = telephonyManager.getSimOperatorName();
        String operator = telephonyManager.getSimOperator();
        int simState = telephonyManager.getSimState();

        String voicemailNumer = telephonyManager.getVoiceMailNumber();
        String voicemailAlphaTag = telephonyManager.getVoiceMailAlphaTag();

        // Getting connected network iso country code
        String networkCountry = telephonyManager.getNetworkCountryIso();
        // Getting the connected network operator ID
        String networkOperatorId = telephonyManager.getNetworkOperator();
        // Getting the connected network operator name
        String networkName = telephonyManager.getNetworkOperatorName();

        int networkType = telephonyManager.getNetworkType();

        String network = "";
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (cm.getActiveNetworkInfo().getTypeName().equals("MOBILE"))
                network = "Cell Network/3G";
            else if (cm.getActiveNetworkInfo().getTypeName().equals("WIFI"))
                network = "WiFi";
            else
                network = "N/A";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("INFO DEVICE:","network :" + network +
                "\n"+"LAC:"+location.getLac()+
                "\n"+"CID:"+location.getCid()+
                "\n" + "countryISO : " + countryISO + "\n" + "operatorName : "
                + operatorName + "\n" + "operator :      " + operator + "\n"
                + "simState :" + simState + "\n" + "Sim Serial Number : "
                + SimSerialNumber + "\n" + "Sim Number : " + SimNumber + "\n"
                + "Voice Mail Numer" + voicemailNumer + "\n"
                + "Voice Mail Alpha Tag" + voicemailAlphaTag + "\n"
                + "Sim State" + simState + "\n" + "Mobile Country Code MCC : "
                + mcc + "\n" + "Mobile Network Code MNC : " + mnc + "\n"
                + "Network Country : " + networkCountry + "\n"
                + "Network OperatorId : " + networkOperatorId + "\n"
                + "Network Name : " + networkName + "\n" + "Network Type : "
                + networkType);
        //Basic config =new Basic();
        //String pointTower = config.getTower(String.valueOf(mcc),String.valueOf(mnc),String.valueOf(location.getLac()), String.valueOf(location.getCid()));
        //config.getTower(String.valueOf(mcc),String.valueOf(mnc),String.valueOf(location.getLac()), String.valueOf(location.getCid()));
        String m_data = "";
        StringBuilder m_builder = new StringBuilder();
        m_builder.append(String.valueOf(location.getLac()) + p_seperator);
        m_builder.append(String.valueOf(location.getCid()) + p_seperator);
        m_builder.append(operatorName + p_seperator);
        m_builder.append(String.valueOf(mcc) + p_seperator);
        m_builder.append(String.valueOf(mnc) + p_seperator);
        m_data = m_builder.toString();
        return m_data;
    }
}
