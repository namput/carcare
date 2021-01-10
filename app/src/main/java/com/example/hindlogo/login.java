package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText phone=(EditText)findViewById(R.id.phone);
        final EditText password=(EditText)findViewById(R.id.password);
        final Button login=(Button)findViewById(R.id.login);
        final Button crecate=(Button)findViewById(R.id.create);

        //ลิงค์เชื่อต่อ
        final String url=getString(R.string.url);
        final String urllogin=getString(R.string.login);
        final String urlcreate=getString(R.string.create);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog=new ProgressDialog(login.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("รอสักครู่...");
                dialog.setIndeterminate(true);
                dialog.show();
                Ion.with(login.this)
                        .load(url+urllogin)
                        .setBodyParameter("phone",phone.getText().toString())
                        .setBodyParameter("pass",password.getText().toString())
                        .setBodyParameter("type","2")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                dialog.dismiss();
                                if (result!=null){
                                    String member_id=result.get("member_id").getAsString();
                                    Intent intent=new Intent(login.this, menuqcar.class);
                                    intent.putExtra("member_id",member_id);
                                    startActivity(intent);
                                    finish();
//                                    Toast.makeText(login.this,"เข้าสู่ระบบได้"+member_id,Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(login.this,"ไม่สามารถเข้าสู่ระบบได้",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        crecate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog=new ProgressDialog(login.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("รอสักครู่...");
                dialog.setIndeterminate(true);
                dialog.show();
                Ion.with(login.this)
                        .load(url+urlcreate)
                        .setBodyParameter("phone",phone.getText().toString())
                        .setBodyParameter("pass",password.getText().toString())
                        .setBodyParameter("type","2")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                dialog.dismiss();
                                if (result!=null){
                                    String member_id=result.get("member_id").getAsString();
                                    String member_phone=result.get("member_phone").getAsString();
                                    Intent intent=new Intent(login.this,AddnameActivity.class);
                                    intent.putExtra("member_id",member_id);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(login.this,"ไม่สามารถสมัครสมาชิกได้",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}