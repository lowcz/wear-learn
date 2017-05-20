package com.example.wearlearn;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import DialogWindows.TimePickerFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Michał on 5/14/2017.
 */



public class SettingsActivity extends AppCompatActivity {

    String[] types = {"10 slowek", "20 slowek", "30 slowek", "40 slowek"};

   /* @InjectView(R.id.editName)
    EditText _editName;
    @InjectView(R.id.editEmail)
    EditText _editEmail;
    @InjectView(R.id.oldPassword)
    EditText _oldPasswoard;
    @InjectView(R.id.newPassword)
    EditText _newPassword;
    @InjectView(R.id.reEnterNewPassword)
    EditText _reEnterNewPassword;
    @InjectView(R.id.dailyGoal)
    Switch _dailyGoal;
    @InjectView(R.id.reminders)
    Spinner _reminders;
    @InjectView(R.id.spinner)
    Spinner _spinner;
    @InjectView(R.id.saveSettings)
    Button _saveSettings;*/

    @InjectView(R.id.goal)
    TextView _goal;

    @InjectView(R.id.choose_goal)
    ImageButton _chooseGoal;

    @InjectView(R.id.time_reminder)
    TextView _timeReminder;

    @InjectView(R.id.choose_time_reminder)
    ImageButton _chooseTimeReminder;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);




        _chooseGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chooseGoal();
            }

        });


        _chooseTimeReminder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chooseTimeReminder();

            }

        });


    }


    private void chooseTimeReminder() {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");

    }



    public void setFieldGoal(int val) {
        _goal.setText(types[val]);
    }
    private void chooseGoal() {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Ustal dzienny cel");

        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch(which){
                    case 0:
                        //onZipRequested();
                        setFieldGoal(which);
                        break;
                    case 1:
                        setFieldGoal(which);
                        break;

                    case 2:
                        setFieldGoal(which);
                        break;

                    case 3:
                        setFieldGoal(which);
                        break;
                }
            }

        });

        b.show();

    }
}

