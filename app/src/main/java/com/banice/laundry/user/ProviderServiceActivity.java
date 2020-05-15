package com.banice.laundry.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.banice.laundry.R;

public class ProviderServiceActivity extends AppCompatActivity {

    Button next, back;

//    Intent intent = getIntent();
//    String providerId = intent.getStringExtra("provider_id");
        String pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_service);
        pd= getIntent().getStringExtra("provider_id");



        next = findViewById(R.id.nextbtn);
        back = findViewById(R.id.backbtn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderServiceActivity.this,ChooseServiceActivity.class);
                intent.putExtra("provider_id", pd);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderServiceActivity.this,The_user_profile.class);
                startActivity(intent);

            }
        });
    }
}
