package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        final String url = getString(R.string.url);
        final LinearLayout menucarcare = (LinearLayout) findViewById(R.id.listcar);
        final LinearLayout logout = (LinearLayout) findViewById(R.id.menulogout);
        final LinearLayout queue = (LinearLayout) findViewById(R.id.queue);
        final LinearLayout history = (LinearLayout) findViewById(R.id.history);
        final LinearLayout listattribute = (LinearLayout) findViewById(R.id.attribute);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menuqcar.this,ContactActivity.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            id = bundle.getString("member_id");
            carcare_id = bundle.getString("carcare_id");
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
                    Toast.makeText(menuqcar.this,"สำเร็จส่งID"+id+"และ"+carcare_id,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(menuqcar.this,MenuattributeActivity.class);
                    intent.putExtra("member_id",id);
                    intent.putExtra("carcare_id",carcare_id);
                    startActivity(intent);
                }
            });
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(menuqcar.this,ReportActivity.class);
                    startActivity(intent);
                }
            });

        }



    }

}