package com.example.demo.entity;

public class OrderItem {
    private int order_item_id = 0;
    private int order_item_quantity = 0;
    private int product_id = 0;
    private int orderlist_id = 0;

    public int getOrderItemId(){
        return order_item_id;
    }

    public void setOrderItemId(int order_item_id){
        this.order_item_id = order_item_id;
    }

    public int getOrderItemQuantity(){
        return order_item_quantity;
    }

    public void setOrderItemQuantity(int order_item_quantity){
        this.order_item_quantity = order_item_quantity;
    }

    public int getProductId(){
        return product_id;
    }

    public void setProductId(int product_id){
        this.product_id = product_id;
    }

    public int getOrderListId(){
        return orderlist_id;
    }

    public void setOrderListId(int orderlist_id){
        this.orderlist_id = orderlist_id;
    }
}
