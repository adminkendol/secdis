package lampung.dispenda.cctv.module;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;

import lampung.dispenda.cctv.MainActivity;
import lampung.dispenda.cctv.UpdateApp;
import lampung.dispenda.cctv.dialog.CustomAlertDialog;

/**
 * Created by Chandra on 9/30/2016.
 */
public class Dialog extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //popUpAlertDialog();
        Bundle bundle = getIntent().getExtras();
        final String type = bundle.getString("type");
        String title = bundle.getString("title");
        String body = bundle.getString("body");
        final String url = bundle.getString("url");
        String tom;
        if(type.equals("1")){
            tom = "OK";
        }else{
            tom = "UPDATE";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(body)
                .setCancelable(false)
                .setPositiveButton(tom, new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(type.equals("2")) {
                                    ProgressDialog mProgressDialog;
                                    mProgressDialog = new ProgressDialog(Dialog.this);
                                    mProgressDialog.setMessage("Download on progress...");
                                    mProgressDialog.setIndeterminate(false);
                                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                    mProgressDialog.setProgressNumberFormat(null);
                                    UpdateApp atualizaApp = new UpdateApp(mProgressDialog);
                                    atualizaApp.setContext(Dialog.this);
                                    atualizaApp.execute(url);
                                }else{
                                    Intent intent = new Intent(Dialog.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
        .setNegativeButton("CLOSE",new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
        AlertDialog a=builder.create();
        a.show();
        Button bq = a.getButton(DialogInterface.BUTTON_POSITIVE);
        Button bqB = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        bq.setBackgroundColor(Color.BLACK);
        bq.setTextColor(Color.WHITE);
        bqB.setBackgroundColor(Color.RED);
        bqB.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) bqB.getLayoutParams();
        positiveButtonLL.gravity = Gravity.LEFT;
        bqB.setLayoutParams(positiveButtonLL);
        //bqB.getOffsetForPosition(100,0);
    }
    private void popUpAlertDialog()
    {
        String title = "My title here?";
        String message = "My Message here";
        String positiveString = "OK";
        String negativeString = "Cancel";
        CustomAlertDialog.AlertDialogStrings customDialogStrings =
                new CustomAlertDialog.AlertDialogStrings
                        (title, message, positiveString, negativeString);
        CustomAlertDialog.AlertDialogStrings alertDialogStrings = null;
        CustomAlertDialog customAlertDialog =
                CustomAlertDialog.newInstance(alertDialogStrings);
        customAlertDialog.show(getSupportFragmentManager(), "customAlertDialog");
        customAlertDialog.setCallbacksListener(new CustomAlertDialog.CallbacksListener() {
            @Override
            public void onPositiveButtonClicked() {
                //do something
            }

            @Override
            public void onNegativeButtonClicked() {
                //do something
            }
        });
    }
}
