package com.banice.laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.banice.laundry.Provider.ProviderSignIn;
import com.google.firebase.auth.FirebaseAuth;

public class Launcher extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Button b, p;

        b=findViewById(R.id.button11);
        p = findViewById(R.id.button12);
        auth=FirebaseAuth.getInstance();



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Launcher.this,MainActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Launcher.this, ProviderSignIn.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();

            }
        });
    }
}
