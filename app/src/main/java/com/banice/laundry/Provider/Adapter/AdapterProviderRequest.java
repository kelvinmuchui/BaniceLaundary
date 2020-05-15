package com.banice.laundry.Provider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banice.laundry.Provider.Model.ProviderRequest;
import com.banice.laundry.R;

import java.util.List;

public class AdapterProviderRequest  extends RecyclerView.Adapter<AdapterProviderRequest.MyHolder>{

    Context context;
    List<ProviderRequest> requestList;

    public AdapterProviderRequest (Context context, List<ProviderRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate layour(row_user)

        View view = LayoutInflater.from(context).inflate(R.layout.row_providerrequest,parent, false);


        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        final String service_id = requestList.get(i).getRequest_id();
        String location = requestList.get(i).getRequest_pick_up_location();
        String time = requestList.get(i).getRequest_time_surge();


        //set data
        holder.mRequestTv.setText(service_id);
        holder.mLoactionTv.setText(location);
        holder.mTimeTv.setText(time);



    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


    //view holder class

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView mRequestTv, mLoactionTv, mTimeTv;


        public MyHolder(@NonNull View itemView) {
            super(itemView);


            //init views
            mRequestTv = itemView.findViewById(R.id.customerId);
            mLoactionTv= itemView.findViewById(R.id.location);
            mTimeTv = itemView.findViewById(R.id.time);
        }
    }
}
