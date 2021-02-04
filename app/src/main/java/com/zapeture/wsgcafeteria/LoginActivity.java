package com.zapeture.wsgcafeteria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText textInputLayoutLogin, textInputLayoutPassword;
    Button Login;
    Button callSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        callSignUp = findViewById(R.id.signup_screen);
        Login = findViewById(R.id.btn_Login);
        textInputLayoutLogin = findViewById(R.id.login);
        textInputLayoutPassword = findViewById(R.id.password);
       final  AsyncHttpClient client = new AsyncHttpClient();
       final  SharedPreferences preferences = getSharedPreferences("userPreferences", Activity.MODE_PRIVATE);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login, password;

                login = String.valueOf(textInputLayoutLogin.getText());
                password = String.valueOf(textInputLayoutPassword.getText());

                if(login.isEmpty() || password.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle(R.string.errorTitle)
                            .setMessage(R.string.errorEmptyFields)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{

                    String url = "http://dev.imagit.pl/mobilne/api/login/"+login+"/"+password;
                    client.get(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);
                            if(android.text.TextUtils.isDigitsOnly(response)){
                                SharedPreferences.Editor preferencesEditor = preferences.edit();
                                preferencesEditor.putString("userId",response);
                                preferencesEditor.commit();
                                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(LoginActivity.this, R.string.errorLoginIncorrect, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }

            }


        });


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

}