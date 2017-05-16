package com.example.wearlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Michał on 5/14/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    @InjectView(R.id.editName)
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
    Button _saveSettings;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        _spinner.setPrompt("Title");

    }
}
