package lampung.dispenda.cctv;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;


public class Show extends Activity {

    VideoView videoView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final ProgressDialog pd = new ProgressDialog(Show.this,R.style.AppTheme_Dark_Dialog);
        // Set progress dialog style horizontal
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Connecting...please wait");
        // Set the progress dialog background color transparent
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.setIndeterminate(false);
        // Finally, show the progress dialog
        pd.show();
        videoView = (VideoView) this.findViewById(R.id.video);
        Bundle bundle = getIntent().getExtras();
        String isi;
        if(bundle.getString("live").equals("1")) {
            isi = bundle.getString("ip");
        }else{
            isi = bundle.getString("rec");
        }

        Log.d("IP", "URL:" + isi);
        videoView.setVideoPath(isi);
        videoView.requestFocus();
        videoView.start();
        //progressBar.setVisibility(View.VISIBLE);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        pd.hide();
                        mp.start();
                        //new MediaController(Show.this).show(50000);
                    }
                });
            }
        });
        MediaController mediaController = new MyMediaController(this, true);
        mediaController.show();

    }
    /*public void onBackPressed(){
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        String loc = bundle.getString("loc");
        String loc_name = bundle.getString("loc_name");
        Intent intent;
        intent = new Intent(Show.this, ListCctv.class);
        intent.putExtra("id", id);
        intent.putExtra("loc", loc);
        intent.putExtra("title", loc_name);
        startActivity(intent);
    }*/
    public class MyMediaController extends MediaController {

        public MyMediaController(Context context, boolean useFastForward) {
            super(context, useFastForward);
        }

        /*@Override
        public void hide() {
            mediaController.show(0);
        }*/

    }
}