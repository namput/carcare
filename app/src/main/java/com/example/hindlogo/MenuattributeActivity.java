package com.example.hindlogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MenuattributeActivity extends AppCompatActivity {

    String member_id;
    String carcare_id;
    TextInputEditText mname;
    TextInputEditText sizes;
    TextInputEditText sizem;
    TextInputEditText sizel;
    TextInputEditText sizexl;
    TextInputEditText sizexxl;
    String url;
    String urllistattribute;
    String urladdattribute;
    ListView listView;
    Button addattribute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuattribute);

        url = getString(R.string.url);
        urllistattribute = getString(R.string.listattribute);
        urladdattribute = getString(R.string.addattribute);
        mname = (TextInputEditText)findViewById(R.id.aname);
        sizes = (TextInputEditText)findViewById(R.id.sizes);
        sizem = (TextInputEditText)findViewById(R.id.sizem);
        sizel = (TextInputEditText)findViewById(R.id.sizel);
        sizexl = (TextInputEditText)findViewById(R.id.sizexl);
        sizexxl = (TextInputEditText)findViewById(R.id.sizexxl);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            member_id = bundle.getString("member_id");
            carcare_id = bundle.getString("carcare_id");
        }
        listattribute();
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        addattribute = (Button)findViewById(R.id.addattribute);
        addattribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aname = mname.getText().toString();
                String msizes = sizes.getText().toString();
                String msizem = sizem.getText().toString();
                String msizel = sizel.getText().toString();
                String msizexl = sizexl.getText().toString();
                String msizexxl = sizexxl.getText().toString();
                Ion.with(MenuattributeActivity.this)
                        .load(url+urladdattribute)
                        .setBodyParameter("carcare_id",carcare_id)
                        .setBodyParameter("name",aname)
                        .setBodyParameter("sizes",msizes)
                        .setBodyParameter("sizem",msizem)
                        .setBodyParameter("sizel",msizel)
                        .setBodyParameter("sizexl",msizexl)
                        .setBodyParameter("sizexxl",msizexxl)
                        .asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        switch (result){
                            case "1":Toast.makeText(MenuattributeActivity.this,"เพิ่มรายการสำเร็จ",Toast.LENGTH_LONG).show();
                                        listattribute();
                                        break;
                            default:Toast.makeText(MenuattributeActivity.this,"ไม่สำเร็จ",Toast.LENGTH_LONG).show();
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

    private void listattribute() {
        Ion.with(MenuattributeActivity.this)
                .load(url+urllistattribute)
                .setBodyParameter("carcare_id",carcare_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ArrayList<AcustomItem> itemArray=new ArrayList<>();
                        if (result != null) {
                            int len = result.size();
                            for (int i=0;i<len;i++){
                                JsonObject item=(JsonObject)result.get(i);
                                String id =item.get("attribute_id").getAsString();
                                String name =item.get("attribute_name").getAsString();
                                String s =item.get("attribute_s").getAsString();
                                String m =item.get("attribute_m").getAsString();
                                String l =item.get("attribute_l").getAsString();
                                String xl =item.get("attribute_xl").getAsString();
                                String xxl =item.get("attribute_xxl").getAsString();
                                itemArray.add(new AcustomItem(id,name,s,m,l,xl,xxl));
                            }
                            AcustomAdapter adapter = new AcustomAdapter(getBaseContext(),itemArray);
                            listView=(ListView)findViewById(R.id.listView);
                            listView.setAdapter(adapter);
                        }

                    }
                });
    }
}