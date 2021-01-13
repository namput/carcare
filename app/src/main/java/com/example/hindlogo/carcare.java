package com.example.hindlogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class carcare extends AppCompatActivity {
    int t1H;
    int t1m;
    int t2H;
    int t2m;
    String carcare_opent;
    String carcare_name;
    String carcare_id;
    String member_id;
    String carcare_close;
    String carcare_status;
    String carcare_lat;
    String carcare_long;
    EditText carcare;
    TextView timeopent;
    TextView timeclose;
    EditText lat;
    EditText lon;
    String url;
    String urlupdatecarcare;
    Switch status;
    CheckBox permission1;
    CheckBox permission2;
    CheckBox permission3;
    Button savecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carcare);
        url = getString(R.string.url);
        urlupdatecarcare = getString(R.string.updatecarcare);

        permission1 = (CheckBox)findViewById(R.id.permission1);
        permission2 = (CheckBox)findViewById(R.id.permission2);
        permission3 = (CheckBox)findViewById(R.id.permission3);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            member_id= bundle.getString("member_id");
            carcare_id= bundle.getString("carcare_id");
            carcare_name = bundle.getString("carcare_name");
            carcare_opent= bundle.getString("carcare_opent");
            carcare_close = bundle.getString("carcare_close");
            carcare_status = bundle.getString("carcare_status");
            carcare_lat = bundle.getString("carcare_lat");
            carcare_long = bundle.getString("carcare_long");

            carcare = (EditText)findViewById(R.id.carcare);
            timeopent = (TextView)findViewById(R.id.opent);
            timeclose = (TextView)findViewById(R.id.close);
            lat = (EditText)findViewById(R.id.lat);
            lon = (EditText)findViewById(R.id.lon);
            savecar = (Button)findViewById(R.id.savecar);
            status = (Switch) findViewById(R.id.status);

            Button managemember = (Button) findViewById(R.id.managemember);

            LocalTime startTime = LocalTime.parse(carcare_opent);
            LocalTime endTime = LocalTime.parse(carcare_close);
            carcare_opent = startTime.toString();
            carcare_close = endTime.toString();

            carcare.setText(carcare_name);
            timeopent.setText(carcare_opent);
            timeclose.setText(carcare_close);
            lat.setText(carcare_lat);
            lon.setText(carcare_long);

            switch (carcare_status){
                case "I":
                    status.setChecked(true);
                    status.setText("เปิดร้าน");
                    status.setTextOn("เปิดร้าน");
                    break;
                case "O":
                    status.setChecked(false);
                    status.setText("ปิดร้าน");
                    status.setTextOn("ปิดร้าน");
                    break;
            }
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status.isChecked()){
                        status.setText("เปิดร้าน");
                    }else {
                        status.setText("ปิดร้าน");
                    }
                }
            });
            managemember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.hindlogo.carcare.this,MemberActivity.class);
                    intent.putExtra("member_id",member_id);
                    intent.putExtra("carcare_id",carcare_id);
                    startActivity(intent);

                }
            });
            timeopent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TimePickerDialog timePickerDialog = new TimePickerDialog(com.example.hindlogo.carcare.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            t1H = hourOfDay;
                            t1m = minute;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0,0,0,t1H,t1m);
                            timeopent.setText(DateFormat.format("H:mm",calendar));
                        }
                    },24,0,false
                    );
                    timePickerDialog.updateTime(t1H,t1m);
                    timePickerDialog.show();
                }
            });
            timeclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timeendPickerDialog = new TimePickerDialog(com.example.hindlogo.carcare.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            t2H = hourOfDay;
                            t2m = minute;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0,0,0,t2H,t2m);
                            timeclose.setText(DateFormat.format("H:mm",calendar));
                        }
                    },24,0,false
                    );
                    timeendPickerDialog.updateTime(t2H,t2m);
                    timeendPickerDialog.show();
                }
            });
            savecar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savecarnow();
                }
            });
                    }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void savecarnow() {
        String carcarename = carcare.getText().toString();
        String carcareopent = timeopent.getText().toString();
        String carcareclose = timeclose.getText().toString();
        String carcarelat = lat.getText().toString();
        String carcarelong = lon.getText().toString();

        Ion.with(com.example.hindlogo.carcare.this)
                .load(url+ urlupdatecarcare)
                .setBodyParameter("member_id",member_id)
                .setBodyParameter("carcare_id",carcare_id)
                .setBodyParameter("carcare_name",carcarename)
                .setBodyParameter("carcare_opent",carcareopent)
                .setBodyParameter("carcare_close",carcareclose)
                .setBodyParameter("carcare_status", String.valueOf(status.isChecked()))
                .setBodyParameter("carcare_lat",carcarelat)
                .setBodyParameter("carcare_long",carcarelong)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        switch (result){
                            case "1":
                                Toast.makeText(com.example.hindlogo.carcare.this,"ปรับปรุงข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
                                break;
                            case "0":
                                Toast.makeText(com.example.hindlogo.carcare.this,"ไม่ได้ปรับปรุงข้อมูล",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(com.example.hindlogo.carcare.this,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                                break;
                        }

                    }
                });
    }
}