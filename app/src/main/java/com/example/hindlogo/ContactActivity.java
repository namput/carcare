package com.example.hindlogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.installations.remote.TokenResult;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Button tokens = (Button)findViewById(R.id.singout);
        tokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toastToken();
//                String token = FirebaseInstanceId.getInstance().getToken();
//                TextView tv =(TextView)findViewById(R.id.abc);
//                tv.setText(token);

            }
        });

    }

    public void toastToken(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(ContactActivity.this,
                "TOKEN = "+token,
                Toast.LENGTH_LONG).show();
        Log.d("TOKEN = ",""+token);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}