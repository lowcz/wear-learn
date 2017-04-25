package com.example.wearlearn;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import pojo.RegisterDataBody;
import Interfaces.Authentication;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import wrappers.RetrofitWrapper;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final String ADDRESS = "http://wl-api.herokuapp.com/";

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    @InjectView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                setResult(RESULT_CANCELED, null);
                startActivity(intent);
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        RetrofitWrapper retro = new RetrofitWrapper(ADDRESS, GsonConverterFactory.create())
                .enableCookies()
                .enableLogging()
                .build();
        Authentication webService = retro.getRetrofit().create(Authentication.class);

        Call<ResponseBody> call = webService.postData(new RegisterDataBody(name, password, email));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String stringResponse;
                    try {
                        stringResponse = response.body().string();
                    } catch (IOException e) {
                        stringResponse="error occurred: \n" + e.toString();
                    }
                    Log.d("LOGIN",response.code() +"\n" + stringResponse);
                    progressDialog.dismiss();
                    if(response.code()==200)
                        onSignupSuccess();
                    else
                        onSignupFailed();
                }
                else
                {
                    Log.d("LOGIN", " response not successful");
                    progressDialog.dismiss();
                    onSignupFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("LOGIN","wyjatek " + t.toString());
                progressDialog.dismiss();
                final AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Could not connect with server, please try again!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                while (alertDialog.isShowing()) {
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    if (!alertDialog.isShowing())
                                        onSignupFailed();
                                }
                            }, 500);
                }


            }
        });

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        Intent intent = new Intent();
        String user = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
        intent.putExtra("user", user);
        intent.putExtra("password", password);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Authentication failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }  else if (android.util.Patterns.EMAIL_ADDRESS.matcher(password).matches()){
            _passwordText.setError("email address cannot be a password");
            valid = false;
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Passwords do not match");
            valid = false;
        }

        return valid;
    }
}