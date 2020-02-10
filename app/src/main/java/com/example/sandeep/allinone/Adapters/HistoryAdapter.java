package com.example.sandeep.allinone.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sandeep.allinone.Models.HistoryModel;
import com.example.sandeep.allinone.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class HistoryAdapter extends  RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{

    List<HistoryModel> historyModels = new ArrayList<>();


@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;

}


public void setHistoryModels(List<HistoryModel> historyModels){
    this.historyModels = historyModels;

}


@Override
public void onBindViewHolder(@NonNull MyViewHolder holder,int position){
    if (position<historyModels.size()){

        holder.time.setText(historyModels.get(position).getTime());
        holder.date.setText(historyModels.get(position).getDate());
        holder.timer.setText(historyModels.get(position).getTimeLimit());
    }



        }


@Override
public int getItemCount(){

        return historyModels.size();

        }


public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView timer;
    TextView date;
    TextView time;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        timer  = itemView.findViewById(R.id.time_used_val);
        date = itemView.findViewById(R.id.date_val);
        time = itemView.findViewById(R.id.time_val);
    }
}


}
