package DialogWindows;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.wearlearn.SettingsActivity;

/**
 * Created by Michał on 5/20/2017.
 */

public class PickerString {

    public void  chooseDailyGoal(SettingsActivity activity){

        AlertDialog.Builder b = new AlertDialog.Builder(activity);
        b.setTitle("Ustal dzinny cel");
        String[] kinds = {"10 slowek", "20 slowek", "30 slowek", "40 slowek"};
        b.setItems(kinds , new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:

                    break;
                    case 1:
                        //onCategoryRequested();
                        break;

                    case 2:
                        //onCategoryRequested();
                        break;

                    case 3:
                        //onCategoryRequested();
                        break;
                }
            }

        });

        b.show();
    }
}
