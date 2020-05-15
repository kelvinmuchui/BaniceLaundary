package com.banice.laundry.Provider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banice.laundry.Provider.Model.ServiceModel;
import com.banice.laundry.R;

import java.util.List;

public class AdapterServices extends RecyclerView.Adapter<AdapterServices.MyHolder> {

    Context context;
    List<ServiceModel> serviceList;

    public AdapterServices(Context context, List<ServiceModel> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_service,parent, false);
        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServices.MyHolder holder, int i) {
        String name =serviceList.get(i).getService_name();
        String desc = serviceList.get(i).getService_desc();

        holder.mDescriptionTv.setText(desc);
        holder.mNameTv.setText(name);

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    //view holder class

    class MyHolder extends RecyclerView.ViewHolder{

        TextView mNameTv, mDescriptionTv;



        public MyHolder(@NonNull View itemView) {
            super(itemView);


            //init views
            mNameTv = itemView.findViewById(R.id.serviceName);
            mDescriptionTv= itemView.findViewById(R.id.serviceDesc);

        }
    }
}

