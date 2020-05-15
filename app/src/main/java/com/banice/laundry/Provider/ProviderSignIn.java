package com.banice.laundry.Provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.banice.laundry.AdminActivity;
import com.banice.laundry.MainActivity;
import com.banice.laundry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;

public class ProviderSignIn extends AppCompatActivity {
    TextView newacc,forgotpassword;
    EditText mPassword, mEmail;
    Button button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_sign_in);
        mEmail = findViewById(R.id.mail);
        mPassword = findViewById(R.id.pass);
        button = findViewById(R.id.login);
        newacc = findViewById(R.id.newacc);
        forgotpassword=findViewById(R.id.forgotpassword);
        mAuth = FirebaseAuth.getInstance();
        newacc = findViewById(R.id.newacc);

        newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(ProviderSignIn.this, ProviderSignUp.class);
                startActivity(login);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    // Invalid emaill
                    mEmail.setError("Invalid Email");
                    mEmail.setFocusable(true);
                }
                else{
                    //Valid email

                    loginUser(email, password);
                }
            }
        });

    }
    private void loginUser(String email, String password) {

        final AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(ProviderSignIn.this)
                .setMessage("Processing")
                .setCancelable(false)
                .setTheme(R.style.Custom)
                .build();
        //Show progress
        alertDialog.show();

        if(email.equals("admin@admin.com")){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                alertDialog.show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                if(task.getResult().getAdditionalUserInfo().isNewUser()){

                                }
                                //user log in
                                startActivity(new Intent(ProviderSignIn.this, AdminActivity.class));
                                finish();
                            } else{
                                alertDialog.dismiss();

                                Toast.makeText(ProviderSignIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //error
                    alertDialog.dismiss();
                    Toast.makeText(ProviderSignIn.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                alertDialog.show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                if(task.getResult().getAdditionalUserInfo().isNewUser()){

                                }
                                //user log in
                                startActivity(new Intent(ProviderSignIn.this, ProviderActivity.class));
                                finish();
                            } else{
                                alertDialog.dismiss();

                                Toast.makeText(ProviderSignIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //error
                    alertDialog.dismiss();
                    Toast.makeText(ProviderSignIn.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
