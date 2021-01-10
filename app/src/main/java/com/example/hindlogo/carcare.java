package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.time.LocalTime;
import java.util.Calendar;

public class carcare extends AppCompatActivity {
    int t1H;
    int t1m;
    int t2H;
    int t2m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carcare);
        final String url = getString(R.string.url);
        final String urlupdatecarcare = getString(R.string.updatecarcare);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String member_id= bundle.getString("member_id");
            String carcare_id= bundle.getString("carcare_id");
            String carcare_name= bundle.getString("carcare_name");
            String carcare_opent= bundle.getString("carcare_opent");
            String carcare_close= bundle.getString("carcare_close");
            String carcare_status= bundle.getString("carcare_status");
            String carcare_lat= bundle.getString("carcare_lat");
            String carcare_long= bundle.getString("carcare_long");

            EditText carcare = (EditText)findViewById(R.id.carcare);
            TextView opent = (TextView)findViewById(R.id.opent);
            TextView close = (TextView)findViewById(R.id.close);
            EditText lat = (EditText)findViewById(R.id.lat);
            EditText lon = (EditText)findViewById(R.id.lon);
            Button save = (Button)findViewById(R.id.save);
            Switch status = (Switch) findViewById(R.id.status);
            Boolean statusname;


            LocalTime startTime = LocalTime.parse(carcare_opent);
            LocalTime endTime = LocalTime.parse(carcare_close);
            carcare_opent = startTime.toString();
            carcare_close = endTime.toString();

            carcare.setText(carcare_name);
            opent.setText(carcare_opent);
            close.setText(carcare_close);
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

            opent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TimePickerDialog timePickerDialog = new TimePickerDialog(com.example.hindlogo.carcare.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            t1H = hourOfDay;
                            t1m = minute;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0,0,0,t1H,t1m);
                            opent.setText(DateFormat.format("H:mm",calendar));
                        }
                    },24,0,false
                    );
                    timePickerDialog.updateTime(t1H,t1m);
                    timePickerDialog.show();
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timeendPickerDialog = new TimePickerDialog(com.example.hindlogo.carcare.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            t2H = hourOfDay;
                            t2m = minute;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0,0,0,t2H,t2m);
                            close.setText(DateFormat.format("H:mm",calendar));
                        }
                    },24,0,false
                    );
                    timeendPickerDialog.updateTime(t2H,t2m);
                    timeendPickerDialog.show();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String carcarename = carcare.getText().toString();
                    String carcareopent = opent.getText().toString();
                    String carcareclose = close.getText().toString();
                    String carcarelat = lat.getText().toString();
                    String carcarelong = lon.getText().toString();
//                    String  statuscarcare;

//                    Toast.makeText(com.example.hindlogo.carcare.this,carcarename,Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(com.example.hindlogo.carcare.this,""+result,Toast.LENGTH_LONG).show();
                                }
                            });

                }
            });

//            Toast.makeText(carcare.this,"ไอดีรถ"+carcare_id+"ชื่อรถ"+carcare_name+"เวลาเปิด"+carcare_opent+"เวลาปิด"+carcare_close+"สถานะ"+carcare_status+"lat"+carcare_lat+"long"+carcare_long, Toast.LENGTH_SHORT).show();
        }
    }
}