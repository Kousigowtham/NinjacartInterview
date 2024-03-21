package com.example.ninjacartinterview;

import java.util.ArrayList;
import java.util.Optional;

public class Cart {
    int cartValueLimit;

    double totalCartValue;
    ArrayList<Item> items;



    public Cart(int cartValueLimit) {
        this.cartValueLimit = cartValueLimit;
        this.items = new ArrayList<>();
    }

    public int getCartValueLimit() {
        return cartValueLimit;
    }

    public void setCartValueLimit(int cartValueLimit) {
        this.cartValueLimit = cartValueLimit;
    }

    public double getTotalCartValue() {
        return totalCartValue;
    }

    public void setTotalCartValue(double totalCartValue) {
        this.totalCartValue = totalCartValue;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int addToCart(Item item){
        Optional<Item> filteredItem = this.items.stream().filter(obj -> obj.name.equals(item.name)).findFirst();
        int itemQuantity=0;
        if(filteredItem.isPresent()){

            for (Item obj : this.items) {
                if (obj.getName().equals(item.name)) {
                    obj.setCartQuantity(obj.cartQuantity + item.multiplier);
                    itemQuantity = obj.cartQuantity;
                    this.totalCartValue += (item.multiplier * item.value);
                    break;
                }
            }

        }else{
            item.setCartQuantity(item.multiplier);
            itemQuantity = item.multiplier;
            this.totalCartValue += (item.multiplier * item.value);
            this.items.add(item);
        }

        return itemQuantity;
    }
    public int removeFromCart(Item item){
        Optional<Item> filteredItem = this.items.stream().filter(obj -> obj.name.equals(item.name)).findFirst();
        int itemQuantity=0;
        if(filteredItem.isPresent()){

            for (Item obj : this.items) {
                if (obj.getName().equals(item.name)) {


                        if(obj.cartQuantity <= 0){
                            obj.setCartQuantity(0);
                        }else{
                            obj.setCartQuantity(obj.cartQuantity - item.multiplier);
                            itemQuantity = obj.cartQuantity;
                            this.totalCartValue -= (item.multiplier * item.value);
                        }
                    break; // If you only want to update the first occurrence
                }
            }

        }
        return itemQuantity;
    }

    public void replaceItemCartValue(Item item,int value){
        Optional<Item> filteredItem = this.items.stream().filter(obj -> obj.name.equals(item.name)).findFirst();
        if(filteredItem.isPresent()){
            for (Item obj : this.items) {
                if (obj.getName().equals(item.name)) {
                    this.totalCartValue -= (obj.getCartQuantity() * item.value);
                    obj.setCartQuantity(value);

                    this.totalCartValue += (value * item.value);

                    break;
                }
            }
        }else{
            item.setCartQuantity(value);
            this.totalCartValue += (value * item.value);
            this.items.add(item);
        }
    }
}
