package com.banice.laundry.user.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.banice.laundry.R;
import com.banice.laundry.user.Models.Services;
import com.banice.laundry.user.ProviderActivity;

import java.util.List;

public class AdapterService extends RecyclerView.Adapter<AdapterService.MyHolder> {

    Context context;

    List<Services> servicesList;

    public AdapterService(Context context, List<Services> servicesList) {
        this.context = context;
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_service,parent, false);

        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
            String service_id = servicesList.get(i).getService_id();
            String serviceName = servicesList.get(i).getService_name();
            String serviceDerc =servicesList.get(i).getService_desc();

            holder.namecheck.setText(serviceName);
            holder.mderc.setText(serviceDerc);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent providerServive = new Intent(context, ProviderActivity.class);
                context.startActivity(providerServive);

            }
        });

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }


    //view holder class

    class MyHolder extends RecyclerView.ViewHolder{

        TextView  mderc;
        CheckBox namecheck;


        public MyHolder(@NonNull View itemView) {
            super(itemView);


            //init views
            namecheck = itemView.findViewById(R.id.checkBox);
            mderc = itemView.findViewById(R.id.desc);
        }
    }
}
