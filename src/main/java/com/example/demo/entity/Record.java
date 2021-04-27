package com.example.demo.entity;

public class Record {
    private int order_item_id = 0;
    private int order_item_quantity = 0;
    private int product_id = 0;
    private int orderlist_id = 0;
    private String pay_type = "";
    private String pay_status = "";
    private String orderlist_status = "";
    private int orderlist_payment = 0;
    private String buyer_id  = "";
    private String order_date = "";
    private int pickmoney_use = 0;

    public int getOrderListId(){
        return orderlist_id;
    }

    public void setOrderListId(int orderlist_id){
        this.orderlist_id = orderlist_id;
    }

    public String getPayType(){
        return pay_type;
    }

    public void setPayType(String pay_type){
        this.pay_type = pay_type;
    }

    public String getPayStatus(){
        return pay_status;
    }

    public void setPayStatus(String pay_status){
        this.pay_status = pay_status;
    }

    public String getOrderListStatus(){
        return orderlist_status;
    }

    public void setOrderListStatus(String orderlist_status){
        this.orderlist_status = orderlist_status;
    }

    public int getOrderListPayment(){
        return orderlist_payment;
    }

    public void setOrderListPayment(int orderlist_payment){
        this.orderlist_payment = orderlist_payment;
    }

    public String getOrderDate(){
        return order_date;
    }

    public void setOrderDate(String order_date){
        this.order_date = order_date;
    }

    public int getPickmoneyUse(){
        return pickmoney_use;
    }

    public void setPickmoneyUse(int pickmoney_use){
        this.pickmoney_use = pickmoney_use;
    }

    public String getBuyerId(){
        return buyer_id;
    }

    public void setBuyerId(String buyer_id){
        this.buyer_id = buyer_id;
    }

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

}
