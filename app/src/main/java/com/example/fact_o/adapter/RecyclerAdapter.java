package com.example.fact_o.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fact_o.R;
import com.example.fact_o.model.Fact;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Fact> factList;
    private Context context;

    public RecyclerAdapter(List<Fact> factList, Context context) {
        this.factList = factList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.factView.setText(factList.get(position).getFact());

    }

    @Override
    public int getItemCount() {
        return factList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView factView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            factView = itemView.findViewById(R.id.fact_view);

        }
    }
}
