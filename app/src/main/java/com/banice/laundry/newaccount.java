package com.banice.laundry;

import android.app.AlertDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.banice.laundry.user.The_user_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.aescrypt.AESCrypt;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;


public class newaccount extends AppCompatActivity {
    private EditText fName,sName, password, contact, email, reenterpassword;
    private Button button;
    private DatabaseReference databaseReference;
    FirebaseAuth auth;
    private String mFname,mSname, mEmail, mPassword,mConfpassword, mPhonenumber, mCustomerId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);
        fName = findViewById(R.id.name);
        sName = findViewById(R.id.secondName);
        contact = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        reenterpassword = findViewById(R.id.reenterpassword);
        button = findViewById(R.id.createacc);
        email = findViewById(R.id.emailedt);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customers");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mFname =fName.getText().toString().trim();
                final String mSname = sName.getText().toString().trim();
                final String mEmail = email.getText().toString().trim();
                final String mPhoneNumber = contact.getText().toString().trim();
                final String mPassword = password.getText().toString().trim();

                final AlertDialog alertDialog = new SpotsDialog.Builder()
                        .setContext(newaccount.this)
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
                            String userID = user_id.toString();
                            mCustomerId = getID(10);
                            DatabaseReference current_user_db = databaseReference.child(user_id);
                            current_user_db.child("customer_id").setValue(userID);
                            current_user_db.child("customer_first_name").setValue(mFname);
                            current_user_db.child("Customer_second_name").setValue(mSname);
                            current_user_db.child("customer_email").setValue(mEmail);
                            current_user_db.child("customer_contact").setValue(mPhoneNumber);
                            String message = "hello world";
                            try {
                                String encryptedMsg = AESCrypt.encrypt(mPassword, message);
                                setLogin(mFname,encryptedMsg ,userID);
                            }catch (GeneralSecurityException e){
                                //handle error
                            }



                            Intent regIntent = new Intent(newaccount.this, The_user_profile.class);
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
    private void setLogin(String username, String pass, String id){
        String login_id = getID(10);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("login_customer_id", id);
        hashMap.put("login_id", login_id);
        hashMap.put("login_username", username);
        hashMap.put("login_provider_id", "not a provider" );
        hashMap.put("login_password",pass );

        databaseReference.child("Login").push().setValue(hashMap);

    }

}
