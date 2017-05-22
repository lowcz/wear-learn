package AlertWindows;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.wearlearn.NewWordActivity;
import com.example.wearlearn.OpenFileChooserActivity;


/**
 * Created by Michał on 5/10/2017.
 */

public class AlertDialogActivity {

    public static void alertChooseFailFile(OpenFileChooserActivity openFileChooserActivity) {
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


    public static void alertFiledImportFile(OpenFileChooserActivity openFileChooserActivity) {
        AlertDialog alertDialog = new AlertDialog.Builder(openFileChooserActivity).create();
        alertDialog.setTitle("Nie udalo dodac sie pliku !");
        alertDialog.setMessage("Sprobuj ponownie.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void alertSuccesImportFile(OpenFileChooserActivity openFileChooserActivity) {
        AlertDialog alertDialog = new AlertDialog.Builder(openFileChooserActivity).create();
        alertDialog.setTitle("Pilk zostal wyslany.");
        alertDialog.setMessage("Slowka zostaly dodane pod odpowiednie tagi !");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void alertSuccesAddFile(NewWordActivity newWordActivity) {
        AlertDialog alertDialog = new AlertDialog.Builder(newWordActivity).create();
        alertDialog.setTitle("Udalo sie dodac slowko!");
        alertDialog.setMessage("Slowko zostalo dodane pod odpowiedni tag.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public static void alertFiledAddFile(NewWordActivity newWordActivity) {
        AlertDialog alertDialog = new AlertDialog.Builder(newWordActivity).create();
        alertDialog.setTitle("Nie udalo dodac sie dodac slowka !");
        alertDialog.setMessage("Sprobuj ponownie.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void alertMustSetValue(NewWordActivity newWordActivity) {
        AlertDialog alertDialog = new AlertDialog.Builder(newWordActivity).create();
        alertDialog.setTitle("Podaj wymagane wartosci ! !");
        alertDialog.setMessage("Podaj slowko, tlumaczenie oraz wybierz co najmniej jeden tag.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
