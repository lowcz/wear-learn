package AlertWindows;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.wearlearn.OpenFileChooserActivity;


/**
 * Created by Michał on 5/10/2017.
 */

public class AlertDialogActivity {

        public static void  alertChooseFailFile(OpenFileChooserActivity openFileChooserActivity) {
            AlertDialog alertDialog = new AlertDialog.Builder(openFileChooserActivity).create();
            alertDialog.setTitle("Zle rozszerzenie pliku !");
            alertDialog.setMessage("Aplikacja akceptuje pliki tylko w formacie CSV");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
    }
}
