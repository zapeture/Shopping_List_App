package com.zapeture.wsgcafeteria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import cz.msebera.android.httpclient.Header;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText textInputLayoutLogin , textInputLayoutEmail, textInputLayoutPassword;
    Button callLogin;
    Button callSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        callLogin = findViewById(R.id.callLogin);
        textInputLayoutLogin =  findViewById(R.id.login);
        textInputLayoutEmail = findViewById(R.id.email);
        textInputLayoutPassword = findViewById(R.id.password);
        callSignUp = findViewById(R.id.btn_SignUp);
        AsyncHttpClient client = new AsyncHttpClient();



        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login, email, password;

                login = String.valueOf(textInputLayoutLogin.getText());
                email = String.valueOf(textInputLayoutEmail.getText());
                password = String.valueOf(textInputLayoutPassword.getText());

                if (login.isEmpty() || password.isEmpty() || email.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle(R.string.errorTitle)
                            .setMessage(R.string.errorEmptyFields)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }       else {

                    String url = "http://dev.imagit.pl/mobilne/api/register";

                    RequestParams params = new RequestParams();
                    params.put("login", login);
                    params.put("pass", password);
                    params.put("email", email);

                    client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String response = new String(responseBody);

                            if (response.equals("OK")) {
                                Toast.makeText(SignupActivity.this, "Sign Up Sucessful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this, "Account Already Exists", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }

            };



    });
        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}

