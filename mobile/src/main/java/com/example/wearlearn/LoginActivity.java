package com.example.wearlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private static final String ADDRESS = "http://wl-api.herokuapp.com/";
    private ProgressDialog progressDialog;

    @InjectView(R.id.input_username) EditText _usernameText;
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
        SharedPreferences sp1=this.getSharedPreferences("Login",0);

        tryAutoLogin();
    }

    private void tryAutoLogin(){
        SharedPreferences sp1=this.getSharedPreferences("Login",0);

        String unm=sp1.getString("username", null);
        String pass = sp1.getString("password", null);
        if (unm!=null && pass != null) {
            _passwordText.setText(pass);
            _usernameText.setText(unm);
        }
    }
    private void saveAutoLoginData(String username, String password){
        SharedPreferences sp=getSharedPreferences("Login", 0);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("username",username );
        Ed.putString("password",password);
        Ed.commit();
    }

    public void loginButton(){
        Log.d(TAG, "LoginButton");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);



        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        login(username, password);

    }
    private void login(String username, String password) {
        Log.d(TAG, "Login");



        RetrofitWrapper retro = RetrofitWrapper.getSingleton();

        Authentication webService = retro.getRetrofit().create(Authentication.class);

        Call<ResponseBody> call = webService.postData(username, password);
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
                    {
                        onLoginFailed();
                        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Log.d("LOGIN", " response not successful");
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    onLoginFailed();
                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

                Log.d("LOGIN", "wyjatek " + t.toString());
                if (progressDialog != null)
                    progressDialog.dismiss();
                onLoginFailed();
                Toast.makeText(getBaseContext(), "Could not connect with server", Toast.LENGTH_LONG).show();
            }
        });

        saveAutoLoginData(username, password);

    }


    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences sp1=this.getSharedPreferences("Login",0);



        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {


        _loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String password = _passwordText.getText().toString();

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