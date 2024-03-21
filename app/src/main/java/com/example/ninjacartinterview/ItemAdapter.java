package com.example.ninjacartinterview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Optional;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    ArrayList<Item> items;

    onItemActionClickListener listener;

    final static String ITEM_ADD_ACTION = "ADD";
    final static String ITEM_REMOVE_ACTION = "REMOVE";
    final static String ITEM_DIALOG_OPEN_ACTION = "DIALOG_OPEN";



    public ItemAdapter(ArrayList<Item> items, onItemActionClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.name);

        holder.itemValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemActionClick(holder, item, ITEM_DIALOG_OPEN_ACTION);
            }
        });
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    listener.onItemActionClick(holder, item, ITEM_ADD_ACTION);
            }

        });
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemActionClick(holder,item,ITEM_REMOVE_ACTION);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

     public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemValue;
        Button addButton, removeButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemValue = itemView.findViewById(R.id.itemValueTextView);
            addButton = itemView.findViewById(R.id.addButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

    public interface onItemActionClickListener{
        void onItemActionClick(ViewHolder holder, Item item, String action);
    }
}

