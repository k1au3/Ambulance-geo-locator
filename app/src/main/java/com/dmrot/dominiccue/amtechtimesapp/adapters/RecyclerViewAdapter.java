package com.dmrot.dominiccue.amtechtimesapp.adapters;

/**
 * Created by dominiccue on 10/16/2017.
 */

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.dmrot.dominiccue.amtechtimesapp.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    private SharedPreferences pref;

    List<DateAdapter> dataAdapters;

    public RecyclerViewAdapter(List<DateAdapter> getDataAdapter, Context context){

        super();

        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        DateAdapter dataAdapter =  dataAdapters.get(position);

        viewHolder.TextViewTdate.setText(dataAdapter.getTdate());

        viewHolder.TextViewPlace.setText(String.valueOf(dataAdapter.getPlace()));

        viewHolder.TextViewPatient.setText(dataAdapter.getPatient());

        viewHolder.TextViewAmbulance.setText(dataAdapter.getAmbulance());


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TextViewTdate;
        public TextView TextViewPlace;
        public TextView TextViewAmbulance;
        public TextView TextViewPatient;



        public ViewHolder(View itemView) {

            super(itemView);

            TextViewTdate = (TextView) itemView.findViewById(R.id.textViewdate) ;
            TextViewPlace = (TextView) itemView.findViewById(R.id.textViewplace) ;
            TextViewAmbulance = (TextView) itemView.findViewById(R.id.textViewInvoicable);
            TextViewPatient = (TextView) itemView.findViewById(R.id.textViewremarks);

        }
    }
}
