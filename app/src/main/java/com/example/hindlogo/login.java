package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

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
        final String urlgettoken = getString(R.string.gettoken);
        String token = FirebaseInstanceId.getInstance().getToken();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().length()!=10){
                    SimpleTooltip.Builder builder = new SimpleTooltip.Builder(getBaseContext())
                            .anchorView(phone)
                            .text("กรุณาป้อนเบอร์โทร 10 หลัก")
                            .gravity(Gravity.TOP)
                            .animated(false)
                            .backgroundColor(Color.parseColor("#8b2323"))
                            .arrowColor(Color.parseColor("#8b2323"))
                            .textColor(Color.parseColor("#ffffff"));
                    SimpleTooltip tooltip = builder.build();
                    tooltip.show();
                }else if (password.getText().length()<6){
                    SimpleTooltip.Builder builder = new SimpleTooltip.Builder(getBaseContext())
                            .anchorView(password)
                            .text("กรุณาป้อนรหัสผ่าน 6 ตัวขึ้นไป")
                            .gravity(Gravity.TOP)
                            .animated(false)
                            .backgroundColor(Color.parseColor("#8b2323"))
                            .arrowColor(Color.parseColor("#8b2323"))
                            .textColor(Color.parseColor("#ffffff"));
                    SimpleTooltip tooltip = builder.build();
                    tooltip.show();
                }
                else {
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
                                        String carcare_id=result.get("carcare_id").getAsString();
//                                    Toast.makeText(com.example.hindlogo.login.this,""+carcare_id,Toast.LENGTH_LONG).show();
                                        Ion.with(com.example.hindlogo.login.this)
                                                .load(url+urlgettoken)
                                                .setBodyParameter("token",token)
                                                .setBodyParameter("member_id",member_id)
                                                .asString()
                                                .setCallback(new FutureCallback<String>() {
                                                    @Override
                                                    public void onCompleted(Exception e, String result) {

                                                    }
                                                });

                                        SharedPreferences savelogin = getSharedPreferences("CHECK_LOGIN", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = savelogin.edit();
                                        editor.putBoolean("login_status",true);
                                        editor.putString("member_id",member_id);
                                        editor.putString("carcare_id",carcare_id);
                                        editor.commit();
                                        Intent intent=new Intent(login.this, menuqcar.class);
                                        intent.putExtra("member_id",member_id);
                                        intent.putExtra("carcare_id",carcare_id);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        Toast.makeText(login.this,"ไม่ได้รับอนุญาติ",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }


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