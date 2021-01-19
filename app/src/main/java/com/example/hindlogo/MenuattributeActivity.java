package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MenuattributeActivity extends AppCompatActivity {

    String member_id;
    String carcare_id;
    EditText mname;
    EditText sizes;
    EditText sizem;
    EditText sizel;
    EditText sizexl;
    EditText sizexxl;
    String url;
    String urllistattribute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuattribute);

        url = getString(R.string.url);
        urllistattribute = getString(R.string.listattribute);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            member_id = bundle.getString("member_id");
            carcare_id = bundle.getString("carcare_id");
        }
        listattribute();

    }

    private void listattribute() {
        Ion.with(MenuattributeActivity.this)
                .load(url+urllistattribute)
                .setBodyParameter("carcare_id",carcare_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Toast.makeText(MenuattributeActivity.this,""+result,Toast.LENGTH_LONG).show();

                    }
                });
    }
}