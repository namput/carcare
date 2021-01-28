package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences check_login = getSharedPreferences("CHECK_LOGIN",MODE_PRIVATE);
                boolean check = check_login.getBoolean("login_status",false);
                String member_id =check_login.getString("member_id",null);
                String carcare_id =check_login.getString("carcare_id",null);
                Intent intent;
                if (check == true && member_id != null) {
                    intent = new Intent(MainActivity.this, menuqcar.class);
                    intent.putExtra("member_id",member_id);
                    intent.putExtra("carcare_id",carcare_id);
                }else {
                    intent = new Intent(MainActivity.this, login.class);
                }
                startActivity(intent);
                finish();

            }
            },3000);
        }

}