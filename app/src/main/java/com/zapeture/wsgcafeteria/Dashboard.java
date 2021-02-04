package com.zapeture.wsgcafeteria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Dashboard extends AppCompatActivity {


    FloatingActionButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        add_button = findViewById(R.id.add_button);
        ListView lv_ShoppingList = findViewById(R.id.lv_shopping);
        SharedPreferences preferences = getSharedPreferences("userPreferences", Activity.MODE_PRIVATE);
        String userId = preferences.getString("userId","0"); //TODO check it out

        final ArrayList<String> shoppingList = new ArrayList<String>();
        ArrayAdapter<String> shoppingAdapter  = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_list_item_1,shoppingList);
        lv_ShoppingList.setAdapter(shoppingAdapter);

        AsyncHttpClient client  = new AsyncHttpClient();
        String url = "http://dev.imagit.pl/wsg_zaliczenie/api/items/"+userId;

         client.get(url, new AsyncHttpResponseHandler() {
             @Override
             public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                 String JSON = new String(responseBody);
                 try {
                     JSONArray jArray = new JSONArray(JSON);

                     for(int i = 0;i < jArray.length();i++){
                         JSONObject jObject = jArray.getJSONObject(i);

                         String itemUser = jObject.getString("ITEM_USER");
                         String itemId = jObject.getString("ITEM_ID");
                         String itemName = jObject.getString("ITEM_NAME");
                         String itemDescription = jObject.getString("ITEM_DESCRIPTION");

                         shoppingList.add(itemUser+", "+itemId+", "+itemName+", "+itemDescription);
                     }
                     lv_ShoppingList.setAdapter(shoppingAdapter);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

             }
         });

         add_button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(Dashboard.this, Add.class);
             startActivity(intent);

         }
     });

    }
}