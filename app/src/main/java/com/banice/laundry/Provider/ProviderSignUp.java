package com.banice.laundry.Provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.banice.laundry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class ProviderSignUp extends AppCompatActivity {

    private EditText Name, password, contact, email, reenterpassword;
    private Button button;
    private StorageReference storageref;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ImageButton profile_image;
    private String mName, mEmail, mPassword,mConfpassword, mPhonenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_sign_up);

        Name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        contact = findViewById(R.id.username);
        email = findViewById(R.id.email);
        profile_image = findViewById(R.id.image);
        button = findViewById(R.id.createacc);
        reenterpassword = findViewById(R.id.reenterpassword);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Provider");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mName = Name.getText().toString().trim();
                final String mEmail = email.getText().toString().trim();
                final String mPhoneNumber = contact.getText().toString().trim();
                final String mPassword  = password.getText().toString().trim();



                final AlertDialog alertDialog = new SpotsDialog.Builder()
                        .setContext(ProviderSignUp.this)
                        .setMessage("Creating your account")
                        .setCancelable(false)
                        .setTheme(R.style.Custom)
                        .build();



                //Email and password validation
                if (!Validate_email(mEmail)) {
                    email.setError("Incorrect Email");
                    email.requestFocus();

                } else if (!Validate_password(mPassword)) {
                    password.setError("Password must be greater than 6 characters");
                    password.requestFocus();
                } else if (!password.getText().toString().equals(reenterpassword.getText().toString())) {
                    reenterpassword.setError("Password does not match");
                    reenterpassword.requestFocus();

                } else {
                    alertDialog.show();


                    auth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = auth.getCurrentUser().getUid();
                            String providerId = getID(10);
                            DatabaseReference current_user_db = databaseReference.child(user_id);
                            current_user_db.child("Provider_Id").setValue(user_id);
                            current_user_db.child("Provider_Name").setValue(mName);
                            current_user_db.child("Provider_email").setValue(mEmail);
                            current_user_db.child("Provider_contact").setValue(mPhoneNumber);
                            setLogin(user_id,mName,mPassword);
                            Intent regIntent = new Intent(ProviderSignUp.this,ProviderSignIn.class);
                            startActivity(regIntent);
                        }
                    });

                    alertDialog.dismiss();
                }
            }
        });




    }
    private boolean Validate_password(String s) {
        if (s != null && s.length() > 6)
            return true;
        else
            return false;
    }

    private boolean Validate_email(String s) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email.getText().toString());

        return matcher.matches();
    }

    private String getID(int n)
    {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

    private void setLogin(String providerid, String username, String pass){
        String login_id = getID(10);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("login_customer_id", "not a customer");
        hashMap.put("login_id", login_id);
        hashMap.put("login_username", username);
        hashMap.put("login_provider_id", providerid );
        hashMap.put("login_password",pass );

        databaseReference.child("Login").push().setValue(hashMap);

    }


}
