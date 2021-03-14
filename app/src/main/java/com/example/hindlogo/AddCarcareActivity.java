package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AddCarcareActivity extends AppCompatActivity {

    String url;
    String urlcarcare;
    String urladdname;
    String mid;
    String mname;
    EditText namecarcare;
    AlertDialog.Builder dialogBuilder;
    View layoutView;
    AlertDialog alertDialog;
    Button getout;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_carcare);

        url= getString(R.string.url);
        urlcarcare =getString(R.string.addcarcare);
        urladdname = getString(R.string.addname);

        namecarcare=(EditText)findViewById(R.id.namecar);
        Button savename=(Button)findViewById(R.id.savecarcare);
        Button skip = (Button)findViewById(R.id.skip);

//เช็คว่ามีการส่งค่ามาไหม
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            mid = bundle.getString("member_id");
            mname = bundle.getString("member_name");

            savename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String mcarcare = namecarcare.getText().toString();
                    int len=mcarcare.length();
                    if(len>=3) {
                        Ion.with(AddCarcareActivity.this)
                                .load(url + urlcarcare)
                                .setBodyParameter("member_id", mid)
                                .setBodyParameter("member_name", mname)
                                .setBodyParameter("carcare_name", mcarcare)
                                .setBodyParameter("type", "2")
                                .asString()
                                .setCallback(new FutureCallback<String>() {
                                    @Override
                                    public void onCompleted(Exception e, String result) {
                                        if (result != null) {
                                            Toast.makeText(AddCarcareActivity.this, "เพิ่มข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AddCarcareActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(AddCarcareActivity.this, "เพิ่มข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else {
                        Toast.makeText(AddCarcareActivity.this,"หากคุณมเป็นพนักงานนให้กดที่ปุ่มข้าม",Toast.LENGTH_LONG).show();
                    }
                }
            });
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mcarcare = namecarcare.getText().toString();

                    Ion.with(AddCarcareActivity.this)
                            .load(url+ urladdname)
                            .setBodyParameter("member_id",mid)
                            .setBodyParameter("member_name", mname)
                            .setBodyParameter("type","2")
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    if (result!=null){
                                        Toast.makeText(AddCarcareActivity.this,"เพิ่มข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(AddCarcareActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(AddCarcareActivity.this,"เพิ่มข้อมูลไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
        else {
            Intent intent=new Intent(AddCarcareActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}


