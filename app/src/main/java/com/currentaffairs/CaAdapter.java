package com.currentaffairs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CaAdapter extends RecyclerView.Adapter<CaAdapter.ViewHold> {

    private Context context;
    private List<CaModel> list;

    CaAdapter(Context ctx, List<CaModel> list1){
        this.context = ctx;
        this.list = list1;
    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.ca_item_rcv,parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        return new ViewHold(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        CaModel uploadCurrent = list.get(position);
        holder.name.setText(uploadCurrent.getTitle());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to pdf viewer
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{

        ViewHold(View itemView){
            super(itemView);
        }

        TextView name = itemView.findViewById(R.id.ca_item_name);
        ConstraintLayout layout  = itemView.findViewById(R.id.ca_study_item_layout);
    }

}
