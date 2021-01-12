package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    String urlmembercarcare;
    String idmember_carcare;
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
    String urladdmember;
    Spinner spinner;
    Button savecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carcare);
        url = getString(R.string.url);
        urlupdatecarcare = getString(R.string.updatecarcare);
        urlmembercarcare = getString(R.string.membercarcare);
        urladdmember = getString(R.string.addmember);

        permission1 = (CheckBox)findViewById(R.id.permission1);
        permission2 = (CheckBox)findViewById(R.id.permission2);
        permission3 = (CheckBox)findViewById(R.id.permission3);

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
            spinner = (Spinner) findViewById(R.id.spinner);
            Button addmember = (Button) findViewById(R.id.addmember);

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

            spinnermember();
            addmember.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                    Toast.makeText(com.example.hindlogo.carcare.this,"อีก "+permission1.isChecked(),Toast.LENGTH_LONG).show();
                                    Ion.with(com.example.hindlogo.carcare.this)
                                            .load(url+urladdmember)
                                            .setBodyParameter("member_id",member_id)
                                            .setBodyParameter("carcare_id",carcare_id)
                                            .setBodyParameter("addmember_id",idmember_carcare)
                                            .setBodyParameter("permission1", String.valueOf(permission1.isChecked()))
                                            .setBodyParameter("permission2", String.valueOf(permission2.isChecked()))
                                            .setBodyParameter("permission3", String.valueOf(permission3.isChecked()))
                                            .asString()
                                            .setCallback(new FutureCallback<String>() {
                                                @Override
                                                public void onCompleted(Exception e, String result) {
                                                    switch (result){
                                                        case "1":
                                                            spinnermember();
                                                            Toast.makeText(com.example.hindlogo.carcare.this,"เพิ่มข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
                                                            break;
                                                        case "0":
                                                            Toast.makeText(com.example.hindlogo.carcare.this,"ไม่ได้เพิ่มข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
                                                            break;
                                                        default:
                                                            Toast.makeText(com.example.hindlogo.carcare.this,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                                                            break;
                                                    }
                                                }
                                            });
                                }
                            });


//            Toast.makeText(carcare.this,"ไอดีรถ"+carcare_id+"ชื่อรถ"+carcare_name+"เวลาเปิด"+carcare_opent+"เวลาปิด"+carcare_close+"สถานะ"+carcare_status+"lat"+carcare_lat+"long"+carcare_long, Toast.LENGTH_SHORT).show();
        }
    }

    private void spinnermember() {
        Ion.with(com.example.hindlogo.carcare.this)
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

                        savecar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                savecarnow();
                            }
                        });
                    }
                });
    }

    private void savecarnow() {
        String carcarename = carcare.getText().toString();
        String carcareopent = timeopent.getText().toString();
        String carcareclose = timeclose.getText().toString();
        String carcarelat = lat.getText().toString();
        String carcarelong = lon.getText().toString();
//                    String  statuscarcare;

//        Toast.makeText(com.example.hindlogo.carcare.this,"5555",Toast.LENGTH_LONG).show();
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
                                Toast.makeText(com.example.hindlogo.carcare.this,"ไม่ได้ปรับปรุงข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(com.example.hindlogo.carcare.this,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                                break;
                        }

                    }
                });
    }
}