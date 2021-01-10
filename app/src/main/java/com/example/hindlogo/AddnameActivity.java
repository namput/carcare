package com.example.hindlogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddnameActivity extends AppCompatActivity {

    String mid;
    String membername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addname);

        EditText getname =(EditText)findViewById(R.id.namemember);
        Button savename=(Button)findViewById(R.id.savenext);
        membername = getname.getText().toString();

//เช็คว่ามีการส่งค่ามาไหม
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            mid = bundle.getString("member_id");
                if (membername!="") {
                    savename.setBackgroundColor(Color.parseColor("#004F87"));
//                    savename.setTextColor(Color.parseColor("#004F87"));
                    savename.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            membername = getname.getText().toString();
                            Intent intent = new Intent(AddnameActivity.this, AddCarcareActivity.class);
                            intent.putExtra("member_id", mid);
                            intent.putExtra("member_name", membername);
                            startActivity(intent);

                        }
                    });
                }
        }
        else {
            Intent intent=new Intent(AddnameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}


