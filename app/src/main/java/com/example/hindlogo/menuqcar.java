package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class menuqcar extends AppCompatActivity {

    private String id;
    private String carcare_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuqcar);
        final String urlcheckstatus = getString(R.string.checkstatus);
        final String urlmenucarcare = getString(R.string.menucarcare);
        final String urlmenuattribute = getString(R.string.menuattribute);
        final String urlmenuqueue = getString(R.string.menuqueue);
        final String url = getString(R.string.url);
        final String urlmenureport = getString(R.string.menureport);
        final LinearLayout menucarcare = (LinearLayout) findViewById(R.id.listcar);
        final LinearLayout logout = (LinearLayout) findViewById(R.id.menulogout);
        final LinearLayout queue = (LinearLayout) findViewById(R.id.queue);
        final LinearLayout history = (LinearLayout) findViewById(R.id.history);
        final LinearLayout listattribute = (LinearLayout) findViewById(R.id.attribute);

        SharedPreferences check_login = getSharedPreferences("CHECK_LOGIN",MODE_PRIVATE);
        boolean check = check_login.getBoolean("login_status",false);
        id =check_login.getString("member_id",null);
        carcare_id =check_login.getString("carcare_id",null);
        Intent intent;
        if (check == true && id != null) {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(menuqcar.this,ContactActivity.class));
                }
            });
            menucarcare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ion.with(menuqcar.this)
                            .load(url+urlmenucarcare)
                            .setBodyParameter("member_id",id)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result!=null){
                                        String carcare_id = result.get("carcare_id").getAsString();
                                        String carcare_name = result.get("carcare_name").getAsString();
                                        String carcare_opent = result.get("carcare_opent").getAsString();
                                        String carcare_close = result.get("carcare_close").getAsString();
                                        String carcare_status = result.get("carcare_status").getAsString();
                                        String carcare_lat=result.get("carcare_lat").toString();
                                        String carcare_long=result.get("carcare_long").toString();

                                        switch (carcare_lat){
                                            case "null":carcare_lat = null;
                                                break;
                                            case "":carcare_lat = null;
                                                break;
                                            default:carcare_lat = result.get("carcare_lat").getAsString();
                                        }
                                        switch (carcare_long){
                                            case "null":carcare_long = null;
                                                break;
                                            case "":carcare_long = null;
                                            default:carcare_long = result.get("carcare_long").getAsString();
                                        }

//                                        Toast.makeText(menuqcar.this,carcare_status, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(menuqcar.this, carcare.class);
                                        intent.putExtra("member_id",id);
                                        intent.putExtra("carcare_id",carcare_id);
                                        intent.putExtra("carcare_name",carcare_name);
                                        intent.putExtra("carcare_opent",carcare_opent);
                                        intent.putExtra("carcare_close",carcare_close);
                                        intent.putExtra("carcare_status",carcare_status);
                                        intent.putExtra("carcare_lat",carcare_lat);
                                        intent.putExtra("carcare_long",carcare_long);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(menuqcar.this,"ไม่มีสิทธิ์เข้าใช้", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

            listattribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ion.with(menuqcar.this)
                            .load(url+urlmenuattribute)
                            .setBodyParameter("member_id",id)
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    int check = Integer.parseInt(result);
                                    if (check==1){
                                        Intent intent = new Intent(menuqcar.this,MenuattributeActivity.class);
                                        intent.putExtra("member_id",id);
                                        intent.putExtra("carcare_id",carcare_id);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(menuqcar.this,"ไม่ได้รับอนุญาติ",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                }
            });
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ion.with(menuqcar.this)
                            .load(url+urlmenureport)
                            .setBodyParameter("member_id",id)
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    int check = Integer.parseInt(result);
                                    if (check==1){
                                        Intent intent = new Intent(menuqcar.this,ReportActivity.class);
                                        intent.putExtra("member_id",id);
                                        intent.putExtra("carcare_id",carcare_id);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(menuqcar.this,"ไม่ได้รับอนุญาติ",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
            queue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ion.with(menuqcar.this)
                            .load(url+urlmenuqueue)
                            .setBodyParameter("member_id",id)
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    int check = Integer.parseInt(result);
                                    if (check==1){
                                        Intent intent = new Intent(menuqcar.this,QueueActivity.class);
                                        intent.putExtra("member_id",id);
                                        intent.putExtra("carcare_id",carcare_id);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(menuqcar.this,"ไม่ได้รับอนุญาติ",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
        }else {
            intent = new Intent(menuqcar.this, login.class);
            startActivity(intent);
            finish();
        }
    }
}