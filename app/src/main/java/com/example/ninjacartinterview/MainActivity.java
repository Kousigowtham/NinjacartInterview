    package com.example.ninjacartinterview;

import static com.example.ninjacartinterview.ItemAdapter.ITEM_ADD_ACTION;
import static com.example.ninjacartinterview.ItemAdapter.ITEM_DIALOG_OPEN_ACTION;
import static com.example.ninjacartinterview.ItemAdapter.ITEM_REMOVE_ACTION;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

    public class MainActivity extends AppCompatActivity implements  ItemAdapter.onItemActionClickListener, DialogItemAdapter.onDialogItemClickListener{

    RecyclerView recyclerView;
    TextView maxCartValueLimit;
    CustomProgressBar progressBar;
    RelativeLayout layoutWrapper;
    Dialog dialog;
    Cart cart;

    final static String RUPEE_SYMBOL = "₹";
    final int DIALOG_VALUE_LIST_LIMIT = 30;
    final int DIALOG_GRID_VIEW_SPAN_COUNT =4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        recyclerView = findViewById(R.id.itemsRecyclerView);
        progressBar = findViewById(R.id.moneyMeterProgressBar);
        layoutWrapper = findViewById(R.id.LayoutWrapper);
        maxCartValueLimit = findViewById(R.id.maxLimitTextView);


        cart = new Cart(10000);
        maxCartValueLimit.setText(RUPEE_SYMBOL+ String.valueOf(cart.cartValueLimit));

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("itemA", 3,75));
        items.add(new Item("itemB", 1,50));
        items.add(new Item("itemC", 2,20));


        ItemAdapter adapter = new ItemAdapter(items, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        progressBar.setMax(cart.cartValueLimit);
        progressBar.setProgress((int) cart.totalCartValue);


        progressBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                addMidPointLists();
                progressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }

        @Override
        public void onItemActionClick(ItemAdapter.ViewHolder holder, Item item, String action) {
            if(action.equals(ITEM_ADD_ACTION)){
                if(cart.totalCartValue >= cart.cartValueLimit)
                    return;

                int newQty = cart.addToCart(item);
                holder.itemValue.setText(String.valueOf(newQty));
                progressBar.setProgress( (int) cart.totalCartValue);
            }
            if(action.equals(ITEM_REMOVE_ACTION)){
                int newQty = cart.removeFromCart(item);
                holder.itemValue.setText(String.valueOf(newQty));
                progressBar.setProgress( (int) cart.totalCartValue);
            }

            if(action.equals(ITEM_DIALOG_OPEN_ACTION)){
                dialog = new Dialog(this, R.style.DialogStyle);
                dialog.setContentView(R.layout.item_dialog);
                dialog.setCanceledOnTouchOutside(false);


                RecyclerView dialogRecyclerView = dialog.findViewById(R.id.dialogRecyclerView);

                ArrayList<Integer> itemValueList = getItemValueList(item.multiplier,DIALOG_VALUE_LIST_LIMIT);

                DialogItemAdapter dialogItemAdapter = new DialogItemAdapter(itemValueList,holder,item, this);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(this,DIALOG_GRID_VIEW_SPAN_COUNT);

                dialogRecyclerView.setLayoutManager(gridLayoutManager);
                dialogRecyclerView.setAdapter(dialogItemAdapter);
                dialog.show();
                dialog.findViewById(R.id.dialogCloseButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }


        @Override
        public void onDialogItemClick(DialogItemAdapter.ViewHolder holder, int value, ItemAdapter.ViewHolder sourceViewHolder, Item sourceItem) {
            sourceViewHolder.itemValue.setText(String.valueOf(value));
            cart.replaceItemCartValue(sourceItem, value);
            progressBar.setProgress( (int) cart.totalCartValue);
            dialog.dismiss();

        }

        public ArrayList<Integer> getItemValueList(int itemMultiplier, int listLength){
            ArrayList<Integer> valueList = new ArrayList<>();

            for(int i=1; i <= listLength; i++){
                valueList.add(i*itemMultiplier);
            }
            return valueList;
        }

        public void addMidPointLists(){
            List<Integer> midpoints = progressBar.getMidpoints();
            int progressBarWidth = progressBar.getWidth();
            int progressBarMax = cart.cartValueLimit;

            for (int i = 0; i < midpoints.size(); i++) {

                float progressFraction = (float) midpoints.get(i) / progressBarMax;
                int midpointPosition = (int) (progressBarWidth * progressFraction);

                TextView textView = new TextView(this);
                textView.setText(
                        RUPEE_SYMBOL + String.valueOf(midpoints.get(i)));
                textView.setTextColor(getResources().getColor(R.color.tertiary, getTheme()));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.addRule(RelativeLayout.BELOW, R.id.moneyMeterProgressBar);
                layoutParams.leftMargin = midpointPosition + 70;

                textView.setLayoutParams(layoutParams);
                layoutWrapper.addView(textView);
            }

        }
    }