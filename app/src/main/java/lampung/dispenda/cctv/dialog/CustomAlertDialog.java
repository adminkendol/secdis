package lampung.dispenda.cctv.dialog;

/**
 * Created by Chandra on 9/30/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lampung.dispenda.cctv.R;

public class CustomAlertDialog extends DialogFragment implements
        View.OnClickListener {
    /**
     * Interface for receiving the wanted callbacks
     * */
    public interface CallbacksListener
    {
        public void onPositiveButtonClicked();

        public void onNegativeButtonClicked();
    }

    private CallbacksListener callbacksListener;

    public void setCallbacksListener(CallbacksListener callbacksListener)
    {
        this.callbacksListener = callbacksListener;
    }

    public CustomAlertDialog()
    {
        //empty constructor
    }

    private String titleString;
    private String messageString;
    private String positiveString;
    private String negativeString;

    @Override
    public void setArguments(Bundle bundle)
    {
        titleString = bundle.getString("titleString");
        messageString = bundle.getString("messageString");
        positiveString = bundle.getString("positiveString");
        negativeString = bundle.getString("negativeString");
    }

    public static CustomAlertDialog newInstance(AlertDialogStrings alertDialogStrings)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog();
        Bundle b = new Bundle();
        b.putString("titleString", alertDialogStrings.titleString);
        b.putString("messageString", alertDialogStrings.messageString);
        b.putString("negativeString", alertDialogStrings.negativeString);
        b.putString("positiveString", alertDialogStrings.positiveString);
        customAlertDialog.setArguments(b);

        return customAlertDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        TextView titleTV = (TextView) v.findViewById(R.id.title_customAlertDialog);
        TextView messageTV = (TextView) v.findViewById(R.id.message_customAlertDialog);
        Button positiveButton = (Button) v.findViewById(R.id.okBtn_customAlertDialog);
        Button negativeButton = (Button) v.findViewById(R.id.cancelBtn_customAlertDialog);
        titleTV.setText(titleString);
        messageTV.setText(messageString);
        positiveButton.setText(positiveString);
        negativeButton.setText(negativeString);
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);

        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.okBtn_customAlertDialog:
                callbacksListener.onPositiveButtonClicked();
                dismiss();
                break;
            case R.id.cancelBtn_customAlertDialog:
                callbacksListener.onNegativeButtonClicked();
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            callbacksListener = (CallbacksListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement CallbacksListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        callbacksListener = null;
    }

    /**
     * Class for saving the wanted Strings we want to have on our CustomDialog implementation
     * */
    public static class AlertDialogStrings
    {
        public String titleString;
        public String messageString;
        public String positiveString;
        public String negativeString;

        public AlertDialogStrings(String title, String message, String positiveString, String negativeString)
        {
            this.messageString = message;
            this.titleString = title;
            this.positiveString = positiveString;
            this.negativeString = negativeString;
        }
    }
}
