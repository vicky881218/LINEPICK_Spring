package com.example.demo.entity;
import java.util.Date;

public class OrderList {
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
}
