package com.banice.laundry.Provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.banice.laundry.Provider.Model.ServiceDetails;
import com.banice.laundry.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

public class CreateServiceActivity extends AppCompatActivity {

    EditText mName, mServiceDesc, mPrice;

    Button submit;



    String nameHolder, descHolder, priceAmount, serviceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        mName = findViewById(R.id.serviceName);
        mServiceDesc= findViewById(R.id.serviceDesc);
        mPrice = findViewById(R.id.priceEt);
        FirebaseDatabase firebaseDatabase;
        submit = findViewById(R.id.submitbtn);
        final DatabaseReference ref;
        final DatabaseReference priceRef;




        ref = FirebaseDatabase.getInstance().getReference("Services");
        priceRef =FirebaseDatabase.getInstance().getReference("Price");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String customerid = firebaseUser.getUid();
                ServiceDetails serviceDetails = new ServiceDetails();
                getData();

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                String providerId = currentFirebaseUser.getUid();


                serviceDetails.setService_name(nameHolder);
                serviceDetails.setService_desc(descHolder);

                String service_Id = ref.push().getKey();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("service_desc",descHolder );
                hashMap2.put("service_id",providerId );
                hashMap2.put("service_name",nameHolder );

                databaseReference.child("Services").push().setValue(hashMap2);
                addPrice(service_Id);


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("provider_service_service_id",service_Id );
                hashMap.put("provider_service_provider_id",providerId );
                databaseReference.child("Provider_Services").push().setValue(hashMap);
//                Intent main = new Intent(this, .class);
//                startActivity(main);

                mName.setText("");
                mServiceDesc.setText("");
                mPrice.setText("");

            }
        });
    }

    public void getData(){
        nameHolder = mName.getText().toString().trim();
        descHolder = mServiceDesc.getText().toString().trim();
        priceAmount =mPrice.getText().toString().trim();
    }

    public void addPrice(String service_Id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String ServiceID = getID(10);
        priceAmount =mPrice.getText().toString().trim();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("price_provider_service_id",service_Id );
        hashMap.put("price_amount",priceAmount );
        hashMap.put("priceId",ServiceID );
        databaseReference.child("Price").push().setValue(hashMap);

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
