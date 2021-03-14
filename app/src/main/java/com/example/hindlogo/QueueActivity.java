package com.example.hindlogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class QueueActivity extends AppCompatActivity {
    String member_id;
    String carcare_id;
    String url;
    String urlqueueing;
    String stringDate;
    ListView listView;
    ListView listView1;
    View layoutView;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;
    String queue_id;
    String member_name;
    String urlqueue_detail;
    String urlcallqueue;
    String member_phone;
    String urlchangstatus;
    SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        url = getString(R.string.url);
        urlqueueing = getString(R.string.queueing);
        urlqueue_detail = getString(R.string.queue_detail);
        urlcallqueue = getString(R.string.callqueue);
        urlchangstatus = getString(R.string.changstatus);



        Date date = new Date();
        stringDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);

        SharedPreferences check_login = getSharedPreferences("CHECK_LOGIN",MODE_PRIVATE);
        boolean check = check_login.getBoolean("login_status",false);
        member_id =check_login.getString("member_id",null);
        carcare_id =check_login.getString("carcare_id",null);
        Intent intent;
        
        if (check == true && member_id != null) {

            updatequeues();
            swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    updatequeues();
                }
            });


            // toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            // add back arrow to toolbar
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

        }else {
            intent = new Intent(QueueActivity.this, login.class);
            startActivity(intent);
            finish();
        }


    }

    private void updatequeues() {
        Ion.with(QueueActivity.this)
                .load(url+urlqueueing)
                .setBodyParameter("carcare_id",carcare_id)
                .setBodyParameter("datenow",stringDate)
                .setBodyParameter("status_id","2")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
//                        Toast.makeText(QueueActivity.this,"2 "+carcare_id+" "+stringDate,Toast.LENGTH_LONG).show();
                        ArrayList<QcustomItem> itemArray=new ArrayList<>();
                        if (result != null) {
                            int len = result.size();
                            for (int i=0;i<len;i++){
                                JsonObject item=(JsonObject)result.get(i);
                                String queue_id =item.get("queue_id").getAsString();
                                String car_member_number =item.get("car_member_number").getAsString();
                                String car_service_name =item.get("car_service_name").getAsString();
                                String color_name =item.get("color_name").getAsString();
                                itemArray.add(new QcustomItem(queue_id,car_member_number,car_service_name,color_name,"2"));
                            }
                        }

                        QcustomAdapter adapter = new QcustomAdapter(getBaseContext(),itemArray);
                        listView=(ListView)findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                QcustomItem item = itemArray.get(position);
                                queue_id = item.queue_id;
                                showAlertDialogSuccess(R.layout.fragment_detail_2);
                            }
                        });

                    }
                });

        Ion.with(QueueActivity.this)
                .load(url+urlqueueing)
                .setBodyParameter("carcare_id",carcare_id)
                .setBodyParameter("datenow",stringDate)
                .setBodyParameter("status_id","1")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
//                        Toast.makeText(QueueActivity.this,"1 "+result,Toast.LENGTH_LONG).show();
                        ArrayList<QcustomItem> itemArray=new ArrayList<>();
                        if (result != null) {
                            int len = result.size();
                            for (int i=0;i<len;i++){
                                JsonObject item=(JsonObject)result.get(i);
                                String queue_id =item.get("queue_id").getAsString();
                                String car_member_number =item.get("car_member_number").getAsString();
                                String car_service_name =item.get("car_service_name").getAsString();
                                String color_name =item.get("color_name").getAsString();
                                itemArray.add(new QcustomItem(queue_id,car_member_number,car_service_name,color_name,"1"));
                            }
                        }

                        QcustomAdapter adapter = new QcustomAdapter(getBaseContext(),itemArray);
                        listView1=(ListView)findViewById(R.id.listView2);
                        listView1.setAdapter(adapter);
                        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                QcustomItem item = itemArray.get(position);
                                queue_id = item.queue_id;
                                showAlertDialog(R.layout.fragment_detail);

                            }
                        });
                        swipeContainer.setRefreshing(false);
                    }
                });

    }
    private void showAlertDialogSuccess(int layout){

        Ion.with(QueueActivity.this)
                .load(url+urlqueue_detail)
                .setBodyParameter("queue_id",queue_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ArrayList<String> itemArray=new ArrayList<String>();
                        if (result != null) {
                            JsonObject item=(JsonObject)result.get(0);
                            String queue_id =item.get("queue_id").getAsString();
                            String car_member_number =item.get("car_member_number").getAsString();
                            String car_service_name =item.get("car_service_name").getAsString();
                            String color_name =item.get("color_name").getAsString();
                            member_name =item.get("member_name").getAsString();
                            member_phone =item.get("member_phone").getAsString();
                            String car_member_brand =item.get("car_member_brand").getAsString();
                            JsonArray items=(JsonArray)result.get(1);
                            int len = items.size();
                            for (int i=0;i<len;i++){
                                JsonObject itemss=(JsonObject)items.get(i);
                                String attribute_name =itemss.get("attribute_name").getAsString();
                                itemArray.add(attribute_name);
                            }

                            dialogBuilder = new AlertDialog.Builder(QueueActivity.this);
                            layoutView = getLayoutInflater().inflate(layout, null);
                            TextView car =layoutView.findViewById(R.id.tvTitle);
                            TextView name =layoutView.findViewById(R.id.tvMessage);
                            TextView phone =layoutView.findViewById(R.id.tvMessag);
                            TextView colorandbrand =layoutView.findViewById(R.id.tvMessa);

                            car.setText(car_service_name);
                            name.setText("ชื่อเจ้าของรถ "+member_name);
                            phone.setText("เบอร์ติดต่อ "+member_phone);
                            colorandbrand.setText("แบรนด์ "+car_member_brand+" สีรถ "+color_name+" ทะเบียนรถ "+car_member_number);
                            phone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_DIAL); // Action for what intent called for
                                    intent.setData(Uri.parse("tel:+66" + member_phone)); // Data with intent respective action on intent
                                    startActivity(intent);
                                }
                            });
                            ArrayAdapter < String > dataAdapter = new ArrayAdapter< String >
                                    ( QueueActivity.this, android.R.layout.simple_list_item_1, itemArray );
                            ListView listview = layoutView.findViewById ( R.id.listview );
                            listview.setAdapter ( dataAdapter );

                            Button btnSuccess = layoutView.findViewById(R.id.btnSuccess);
                            Button btnCancel = layoutView.findViewById(R.id.btnCancel);
                            dialogBuilder.setView(layoutView);
                            alertDialog = dialogBuilder.create();
                            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            alertDialog.show();
                            btnSuccess.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                    changStatusQueue("3");
                                }

                            });
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                        }

                    }
                });
    }
    private void showAlertDialog(int layout){

        Ion.with(QueueActivity.this)
                .load(url+urlqueue_detail)
                .setBodyParameter("queue_id",queue_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ArrayList<String> itemArray=new ArrayList<String>();
                        if (result != null) {
                            JsonObject item=(JsonObject)result.get(0);
                            String queue_id =item.get("queue_id").getAsString();
                            String car_member_number =item.get("car_member_number").getAsString();
                            String car_service_name =item.get("car_service_name").getAsString();
                            String color_name =item.get("color_name").getAsString();
                            member_name =item.get("member_name").getAsString();
                            member_phone =item.get("member_phone").getAsString();
                            String car_member_brand =item.get("car_member_brand").getAsString();
                            JsonArray items=(JsonArray)result.get(1);
                            int len = items.size();
                            for (int i=0;i<len;i++){
                                JsonObject itemss=(JsonObject)items.get(i);
                                String attribute_name =itemss.get("attribute_name").getAsString();
                               itemArray.add(attribute_name);
                            }

                            dialogBuilder = new AlertDialog.Builder(QueueActivity.this);
                            layoutView = getLayoutInflater().inflate(layout, null);
                            TextView car =layoutView.findViewById(R.id.tvTitle);
                            TextView name =layoutView.findViewById(R.id.tvMessage);
                            TextView phone =layoutView.findViewById(R.id.tvMessag);
                            TextView colorandbrand =layoutView.findViewById(R.id.tvMessa);

                            car.setText(car_service_name);
                            name.setText("ชื่อเจ้าของรถ "+member_name);
                            phone.setText("เบอร์ติดต่อ "+member_phone);
                            colorandbrand.setText("แบรนด์ "+car_member_brand+" สีรถ "+color_name+" ทะเบียนรถ "+car_member_number);
                            phone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_DIAL); // Action for what intent called for
                                    intent.setData(Uri.parse("tel:+66" + member_phone)); // Data with intent respective action on intent
                                    startActivity(intent);
                                }
                            });
                            ArrayAdapter < String > dataAdapter = new ArrayAdapter< String >
                                    ( QueueActivity.this, android.R.layout.simple_list_item_1, itemArray );
                            ListView listview = layoutView.findViewById ( R.id.listview );
                            listview.setAdapter ( dataAdapter );

                            Button btnStart = layoutView.findViewById(R.id.btnStart);
                            Button btnCancel = layoutView.findViewById(R.id.btnCancel);
                            Button btnBack = layoutView.findViewById(R.id.btnBack);
                            Button btnHey = layoutView.findViewById(R.id.btnHey);
                            dialogBuilder.setView(layoutView);
                            alertDialog = dialogBuilder.create();
                            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            alertDialog.show();
                            btnStart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                    changStatusQueue("2");
                                }

                            });
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    changStatusQueue("4");
                                }
                            });
                            btnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                            btnHey.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    Ion.with(QueueActivity.this)
                                            .load(url+urlcallqueue)
                                            .setBodyParameter("queue_id",queue_id)
                                            .asString()
                                            .setCallback(new FutureCallback<String>() {
                                                @Override
                                                public void onCompleted(Exception e, String result) {
                                                    Toast.makeText(QueueActivity.this,"ส่งข้อความให้คุณ"+member_name+"ทราบแล้ว",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            });
                        }

                    }
                });
    }
    private void changStatusQueue(String status_id) {
        Ion.with(QueueActivity.this)
                .load(url+urlchangstatus)
                .setBodyParameter("queue_id",queue_id)
                .setBodyParameter("status_id",status_id)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        switch (result){
                            case "1":
                                Toast.makeText(QueueActivity.this,"ดำเนินการสำเร็จ",Toast.LENGTH_LONG).show();
                                break;
                            default:Toast.makeText(QueueActivity.this,"ไม่สำเร็จ",Toast.LENGTH_LONG).show();
                        }

                        updatequeues();
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(QueueActivity.this, menuqcar.class);
            startActivity(intent);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}