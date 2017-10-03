package lampung.dispenda.cctv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chandra on 2/14/2016.
 * Function Activity Menu Utama CCTV
 */
public class DrawerActivity extends AppCompatActivity {
//public class DrawerActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener  {
    public LinearLayout frameLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Context context;
    private int child;
    public String group_type_id;
    public String loc_id;

    @Override
    public void setContentView(int layoutResID) {
        Session session =new Session(getApplicationContext());
        final String data = session.getdata();
        Log.d("Session Data","isi: "+data);
        DrawerLayout fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.home, null);
        frameLayout = (LinearLayout) fullLayout.findViewById(R.id.drawer_frame);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(fullLayout);
        //Your drawer content...
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        final Menu menu = navigationView.getMenu();

        try {
            JSONObject jsonObj = new JSONObject(data);

            JSONArray data_profile = jsonObj.getJSONArray("profile");
            JSONObject b = data_profile.getJSONObject(0);
            TextView name = (TextView)findViewById(R.id.username);
            TextView email = (TextView)findViewById(R.id.email);
            name.setText(b.getString("user_fullname"));
            email.setText(b.getString("email"));
            group_type_id=b.getString("group_type_id");
            loc_id=b.getString("location_id");
            JSONArray data_menu = jsonObj.getJSONArray("menu");
            Log.d("isi menu!", String.valueOf(data_menu));
            Resources resources = this.getResources();
            for (int i = 0; i < data_menu.length() ; i++){
                JSONObject c = data_menu.getJSONObject(i);
                final int resourceId = resources.getIdentifier(c.getString("icon"), "drawable", this.getPackageName());
                menu.add(R.id.friends, Integer.parseInt(c.getString("menu_id")), 0, c.getString("menu_name")).setIcon(resourceId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Log.d("get menu!", String.valueOf(menuItem.getItemId()));
                try {
                    String hrefs [] = get_href(String.valueOf(menuItem.getItemId()));
                    String href = hrefs[0];
                    String title = hrefs[1];
                    String cd = hrefs[2];
                    Log.d("array CHILD", "BEGO 3|||"+cd);
                    if(href.equals("exit")) {
                        exit();
                        return false;
                    }else if(href.equals("#")){

                        return true;
                    }else{
                        go_page(href,title,cd,group_type_id,loc_id);
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("gak ada page", "BEGO A");
                    return true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return true;
                }

            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state
        actionBarDrawerToggle.syncState();
    }
    private String[] get_href(String s) throws JSONException {
        Session session =new Session(getApplicationContext());
        final String data = session.getdata();
        JSONObject jsonObj = new JSONObject(data);
        JSONArray data_menu = jsonObj.getJSONArray("menu");
        Log.d("get href!", String.valueOf(data_menu));
        String href;
        for (int i = 0; i < data_menu.length() ; i++){
            JSONObject c = data_menu.getJSONObject(i);
            //Log.i("RESPONSE", menu.toString());
            if(c.getString("menu_id").equals(s)){
                href = c.getString("mob_href");
                String title = c.getString("menu_name");
                String cd = c.getString("child_data");
                String[] result;
                result = new String[]{href, title, cd};
                Log.d("isi get href!", s +"|"+ href);
                return result;

            }
        }
        return new String[]{data};
    }
    public void go_page(String href, String title, String cd, String group_type_id, String loc_id) throws ClassNotFoundException {
        Intent intent;
        intent = new Intent(DrawerActivity.this, Class.forName("lampung.dispenda.cctv."+href));
        intent.putExtra("isi", href);
        intent.putExtra("data", "RESPON");
        intent.putExtra("title", title);
        intent.putExtra("cd", cd);
        intent.putExtra("gt", group_type_id);
        intent.putExtra("loc_id", loc_id);
        startActivity(intent);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Kamu Benar-Benar ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //DrawerActivity.this.finish();
                                /*Intent intent;
                                intent = new Intent(DrawerActivity.this, LoginActivity.class);
                                startActivity(intent);*/
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                .setNegativeButton("Tidak", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                dialog.cancel();
                            }

                        }).show();
    }
    int backButtonCount = 0;
    public void onBackPressed(){
        if(backButtonCount >= 1){
            //moveTaskToBack(true);
            //finish();
            //android.os.Process.killProcess(android.os.Process.myPid());
            exit();
        }else{
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }



}
