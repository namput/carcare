package com.example.hindlogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    String id,carcare_id;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        String url = getString(R.string.url);
        String urlreport = getString(R.string.listreportqueue);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SharedPreferences check_login = getSharedPreferences("CHECK_LOGIN",MODE_PRIVATE);
        boolean check = check_login.getBoolean("login_status",false);
        String member_id =check_login.getString("member_id",null);
        String carcare_id =check_login.getString("carcare_id",null);
        Intent intent;
        if (check == true && member_id != null) {
            Ion.with(ReportActivity.this)
                    .load(url+urlreport)
                    .setBodyParameter("carcare_id",carcare_id)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
//                            Toast.makeText(ReportActivity.this,""+result,Toast.LENGTH_LONG).show();
                            ArrayList<ReportcustomItem> itemArray=new ArrayList<>();
                            if (result != null) {
                                int len = result.size();
                                for (int i=0;i<len;i++){
                                    JsonObject item=(JsonObject)result.get(i);
                                    String permissions_id =item.get("dates").getAsString();
                                    String member_name =item.get("num").getAsString();
                                    itemArray.add(new ReportcustomItem(permissions_id,member_name));
                                }
                            }else {
                                Toast.makeText(ReportActivity.this,""+result,Toast.LENGTH_LONG).show();
                            }

                            ReportcustomAdapter adapter = new ReportcustomAdapter(getBaseContext(),itemArray);
                            listView=(ListView)findViewById(R.id.listView);
                            listView.setAdapter(adapter);
                        }
                    });
        }else {
            intent = new Intent(ReportActivity.this, login.class);
            startActivity(intent);
            finish();
        }



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
           Intent intent = new Intent(ReportActivity.this, menuqcar.class);
            startActivity(intent);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}