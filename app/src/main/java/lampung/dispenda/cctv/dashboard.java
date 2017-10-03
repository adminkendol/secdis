package lampung.dispenda.cctv;

import android.os.Bundle;

/**
 * Created by Chandra on 4/3/2016.
 */
public class dashboard extends DrawerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle = getIntent().getExtras();
        //String isi = bundle.getString("isi");
        setTitle("Online");
        setContentView(R.layout.dashboard);

    }
}