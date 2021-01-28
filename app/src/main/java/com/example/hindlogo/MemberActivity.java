package com.example.hindlogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MemberActivity extends AppCompatActivity {

    String urlmembercarcare;
    String idmember_carcare;
    String carcare_id;
    String member_id;
    String url;
    String urlupdatecarcare;
    Switch status;
    CheckBox permission1;
    CheckBox permission2;
    CheckBox permission3;
    CheckBox permission4;
    String urladdmember;
    Spinner spinner;

    String urllistmembercarcare;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        url = getString(R.string.url);
        urlupdatecarcare = getString(R.string.updatecarcare);
        urlmembercarcare = getString(R.string.membercarcare);
        urladdmember = getString(R.string.addmember);
        urllistmembercarcare = getString(R.string.listmembercarcare);

        permission1 = (CheckBox)findViewById(R.id.permission1);
        permission2 = (CheckBox)findViewById(R.id.permission2);
        permission3 = (CheckBox)findViewById(R.id.permission3);
        permission4 = (CheckBox)findViewById(R.id.permission4);
        spinner = (Spinner) findViewById(R.id.spinner);
        Button addmember = (Button) findViewById(R.id.addmember);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            member_id = bundle.getString("member_id");
            carcare_id = bundle.getString("carcare_id");
        }
        spinnermember();
        updatelistmember();
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Ion.with(MemberActivity.this)
                        .load(url+urladdmember)
                        .setBodyParameter("member_id",member_id)
                        .setBodyParameter("carcare_id",carcare_id)
                        .setBodyParameter("addmember_id",idmember_carcare)
                        .setBodyParameter("permission1", String.valueOf(permission1.isChecked()))
                        .setBodyParameter("permission2", String.valueOf(permission2.isChecked()))
                        .setBodyParameter("permission3", String.valueOf(permission3.isChecked()))
                        .setBodyParameter("permission4", String.valueOf(permission4.isChecked()))
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                switch (result){
                                    case "1":
                                        spinnermember();
                                        updatelistmember();
                                        Toast.makeText(MemberActivity.this,"เพิ่มข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
                                        break;
                                    case "0":
                                        Toast.makeText(MemberActivity.this,"ไม่ได้เพิ่มข้อมูล",Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(MemberActivity.this,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void updatelistmember() {
        Ion.with(MemberActivity.this)
                .load(url+urllistmembercarcare)
                .setBodyParameter("carcare_id",carcare_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ArrayList<CustomItem> itemArray=new ArrayList<>();
                        if (result != null) {
                            int len = result.size();
                            for (int i=0;i<len;i++){
                                JsonObject item=(JsonObject)result.get(i);
                                String permissions_id =item.get("permissions_id").getAsString();
                                String member_name =item.get("member_name").getAsString();
                                itemArray.add(new CustomItem(permissions_id,member_name));
                            }
                        }

                        CustomAdapter adapter = new CustomAdapter(getBaseContext(),itemArray);
                        listView=(ListView)findViewById(R.id.listView2);
                        listView.setAdapter(adapter);
                    }
                });
    }
    private void spinnermember() {
        Ion.with(MemberActivity.this)
                .load(url+urlmembercarcare)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ArrayList<Contact> itemArray=new ArrayList<>();
                        if (result!=null){
                            int len =result.size();
                            for (int i=0;i<len;i++){
                                JsonObject item = (JsonObject)result.get(i);
                                String id = item.get("member_id").getAsString();
                                String name = item.get("member_name").getAsString();
                                itemArray.add(new Contact(name,id));
                            }
                        }
                        ArrayAdapter<Contact> adapter =
                                new ArrayAdapter<Contact>(getApplicationContext(), R.layout.row, itemArray);
                        adapter.setDropDownViewResource(R.layout.row);

                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Contact item = itemArray.get(position);
                                idmember_carcare = item.getContact_id();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
    }
}