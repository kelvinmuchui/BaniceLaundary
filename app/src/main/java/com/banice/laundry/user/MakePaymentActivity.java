package com.banice.laundry.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.banice.laundry.R;
import com.banice.laundry.user.Fragments.providerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

public class MakePaymentActivity extends AppCompatActivity {

    RadioButton cash, mpesa;
    RadioGroup radioGroup;
    String paymode;
    EditText deliverydate, feedback;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference reference, feedbackRef,paymentRef;
    Button submit;
    String serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        cash = findViewById(R.id.cash);
        mpesa = findViewById(R.id.mpesa);
        deliverydate = findViewById(R.id.deliverdate);
        feedback = findViewById(R.id.feedBackedt);
        submit = findViewById(R.id.submit);

        //mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        serviceId = intent.getStringExtra("service_id");



        radioGroup = findViewById(R.id.radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(mpesa.isChecked()){
                    paymode = "Mpesa";
                }else

                    paymode = "Cash";

            }
        });
        feedbackRef = FirebaseDatabase.getInstance().getReference();
        paymentRef = FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = feedback.getText().toString().trim();
                String date = deliverydate.getText().toString().trim();
                String fid = getID(10);
                String pid =getID(10);
                String did = getID(10);
                String ppid = getID(20);
              FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
              String customerid = firebaseUser.getUid();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("feedback_customer_id",customerid);
                hashMap.put("feedback_comment",comment );
                hashMap.put("feedback_id",fid );
                feedbackRef.child("feedback").push().setValue(hashMap);

                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("payment_request_id",serviceId );
                hashMap2.put("payment_mode",paymode);
                hashMap2.put("payment_id", pid  );
                hashMap2.put("payment_price_id",ppid   );
                paymentRef.child("Payment").push().setValue(hashMap2);

                HashMap<String, Object> hashMap3 = new HashMap<>();
                hashMap3.put("delivery_date",date );
                hashMap3.put("delivery_id",did);
                hashMap3.put("delivery_request_id", serviceId  );
                paymentRef.child("Delivery").push().setValue(hashMap3);


            }
        });






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

}
