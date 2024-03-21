package com.example.ninjacartinterview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DialogItemAdapter extends RecyclerView.Adapter<DialogItemAdapter.ViewHolder> {

    ArrayList<Integer> items;
    onDialogItemClickListener listener;

    ItemAdapter.ViewHolder sourceViewHolder;

    Item sourceItem;


    public DialogItemAdapter(ArrayList<Integer> items, ItemAdapter.ViewHolder sourceViewHolder, Item sourceItem, onDialogItemClickListener listener) {
        this.items = items;
        this.sourceViewHolder =sourceViewHolder;
        this.sourceItem = sourceItem;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dialog_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int value = items.get(position);
        holder.dialogItemButton.setText(String.valueOf(value));
        holder.dialogItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogItemClick(holder,value, sourceViewHolder, sourceItem);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        Button dialogItemButton;
        ImageButton dialogCloseButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dialogItemButton = itemView.findViewById(R.id.dialogItemButton);
            dialogCloseButton = itemView.findViewById(R.id.dialogCloseButton);
        }
    }

    public interface onDialogItemClickListener{
        void onDialogItemClick(ViewHolder holder, int value, ItemAdapter.ViewHolder sourceViewHolder, Item sourceItem);
    }
}
