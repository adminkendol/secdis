package lampung.dispenda.cctv;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by chandra on 2/13/2016.
 */
public class respon extends DrawerActivity {
//public class respon extends NavigationActivity {
    private int mccA;
    private int mncA;
    //private Toolbar toolbar;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String isi = bundle.getString("isi");
        String data = bundle.getString("data");
        setTitle(isi);
        setContentView(R.layout.respon);
        TextView txtView = (TextView) findViewById(R.id.isi);
        txtView.setText(data);
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imsiA = tel.getSubscriberId();
        String networkOperatorA = tel.getNetworkOperator();
        String networkOperator = tel.getNetworkOperatorName();
        String angka = tel.getNetworkCountryIso();
        //CellLocation lok = tel.getCellLocation();

        if (networkOperator != null) {
            mccA = Integer.parseInt(networkOperatorA.substring(0, 3));
            mncA = Integer.parseInt(networkOperatorA.substring(3));
        }else{
            mccA = Integer.parseInt("");
            mncA = Integer.parseInt("");
        }
        EditText mcc=(EditText) findViewById(R.id.mcc);
        EditText mnc=(EditText) findViewById(R.id.mnc);
        EditText mccB=(EditText) findViewById(R.id.mccB);
        EditText mncB=(EditText) findViewById(R.id.mncB);
        mcc.setText(String.valueOf(angka));
        mnc.setText(String.valueOf(networkOperator));
        mccB.setText(String.valueOf(mccA));
        mncB.setText(String.valueOf(mncA));

    }



}
