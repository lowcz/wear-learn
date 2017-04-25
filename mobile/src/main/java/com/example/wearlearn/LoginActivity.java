package com.example.wearlearn;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

import Interfaces.Authentication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import wrappers.RetrofitWrapper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static boolean emailMode = true;
    private static final String ADDRESS = "http://wl-api.herokuapp.com/";
    private ProgressDialog progressDialog;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginButton();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                //finish();
            }
        });
    }

    public void loginButton(){
        Log.d(TAG, "LoginButton");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);



        String user = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        login(user, password);

    }
    private void login(String user, String password) {
        Log.d(TAG, "Login");



        RetrofitWrapper retro = new RetrofitWrapper(ADDRESS, GsonConverterFactory.create())
                .enableCookies()
                .enableLogging()
                .build();
        Authentication webService = retro.getRetrofit().create(Authentication.class);

        Call<ResponseBody> call = webService.postData(user, password);
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
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    if(response.code()==200)
                        onLoginSuccess();
                    else
                        onLoginFailed();
                }
                else
                {
                    Log.d("LOGIN", " response not successful");
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    onLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("LOGIN","wyjatek " + t.toString());
                if (progressDialog!=null)
                    progressDialog.dismiss();
                final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
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
                                        onLoginFailed();
                                }
                            }, 500);
                }


            }
        });

    }


    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailMode = false;
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "Back from signup");
        if (resultCode == RESULT_OK && requestCode == REQUEST_SIGNUP) {
            if (intent.hasExtra("user")&&intent.hasExtra("password")) {
                Bundle data = intent.getExtras();
                String user = data.getString("user");
                String password = data.getString("password");
                login(user, password);
            }
        }
    }

}