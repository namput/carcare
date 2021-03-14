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

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        final EditText phone=(EditText)findViewById(R.id.phone);
        final EditText password=(EditText)findViewById(R.id.password);
        final Button crecate=(Button)findViewById(R.id.create);

        //ลิงค์เชื่อต่อ
        final String url=getString(R.string.url);
        final String urlcreate=getString(R.string.create);

        crecate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog=new ProgressDialog(CreateActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("รอสักครู่...");
                dialog.setIndeterminate(true);
                dialog.show();
                Ion.with(CreateActivity.this)
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
                                    Intent intent=new Intent(CreateActivity.this,AddnameActivity.class);
                                    intent.putExtra("member_id",member_id);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(CreateActivity.this,"ไม่สามารถสมัครสมาชิกได้",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}