package com.banice.laundry.Provider.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banice.laundry.Provider.Adapter.AdapterServices;
import com.banice.laundry.Provider.Model.ServiceModel;
import com.banice.laundry.R;
import com.banice.laundry.user.Adapters.AdapterProviders;
import com.banice.laundry.user.Models.ModelUser;
import com.banice.laundry.user.Models.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyServicesFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterServices adapterServices;
    List<ServiceModel> serviceList;



    public MyServicesFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_services, container, false);
        //init recycleview
        recyclerView = view.findViewById(R.id.providerService_recyclerview);
        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        serviceList = new ArrayList<>();
        getMyserivices();
        return view;

    }

    private void getMyserivices() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final String providerId = currentFirebaseUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Services");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ServiceModel requestModel = ds.getValue(ServiceModel.class);

                    //get all user
//                    if (!modelUser.getUid().equals(fUser)){
//                        userList.add(modelUser);
//                    }

                  //  if (requestModel.getService_id().equals(providerId)) {
                        serviceList.add(requestModel);
                    //}
                    //adapter
                    adapterServices = new AdapterServices(getContext(), serviceList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterServices);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
