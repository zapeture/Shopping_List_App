package com.zapeture.wsgcafeteria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class Add extends AppCompatActivity {
    TextInputEditText textInputLayoutName, textInputLayoutDesc;
    Button Product_Add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add);


       textInputLayoutName = findViewById(R.id.name);
       textInputLayoutDesc = findViewById(R.id.description);
        Product_Add = findViewById(R.id.insert_Product);


        SharedPreferences preferences = getSharedPreferences("userPreferences", Activity.MODE_PRIVATE);
        String userId = preferences.getString("userId", "0");

        Product_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user, name, desc;

                name = String.valueOf(textInputLayoutName.getText());
                desc = String.valueOf(textInputLayoutDesc.getText());

                String url =  "http://dev.imagit.pl/wsg_zaliczenie/api/item/add";

                RequestParams params = new RequestParams();
                params.put("user",userId);
                params.put("name",name);
                params.put("desc",desc);

                AsyncHttpClient client = new AsyncHttpClient();
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        if(response.equals("OK")){
                            Toast.makeText(Add.this, "Product Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add.this, Dashboard.class);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(Add.this, "API Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


            }
        });


    }
}