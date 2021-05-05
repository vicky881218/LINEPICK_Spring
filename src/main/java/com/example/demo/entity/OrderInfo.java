package com.example.demo.entity;

public class OrderInfo {
    private String buyer_id = "";
    private String buyer_name = "";
    private String buyer_phone = "";
    private String buyer_mail = "";
    private String buyer_address = "";
    private int pickpoint = 0;
    private int pickmoney = 0;
    private int orderlist_id = 0;
    private String pay_type = "";
    private String pay_status = "";
    private String orderlist_status = "";
    private int orderlist_payment = 0;
    private String order_date = "";
    private int pickmoney_use = 0;
    private int order_item_id = 0;
    private int order_item_quantity = 0;
    private int product_id = 0;
    private String product_name = "";
    private String product_desc = "";
    private int product_price = 0;
    private int product_stock = 0;
    private String product_photo = "";
    private String product_style = "";
    private int discount = 0;


    public String getBuyerId(){
        return buyer_id;
    }

    public void setBuyerId(String buyer_id){
        this.buyer_id=buyer_id;
    }

    public String getBuyerName(){
        return buyer_name;
    }

    public void setBuyerName(String buyer_name){
        this.buyer_name=buyer_name;
    }

    public String getBuyerPhone(){
        return buyer_phone;
    }

    public void setBuyerPhone(String buyer_phone){
        this.buyer_phone=buyer_phone;
    }

    public String getBuyerMail(){
        return buyer_mail;
    }

    public void setBuyerMail(String buyer_mail){
        this.buyer_mail=buyer_mail;
    }

    public String getBuyerAddress(){
        return buyer_address;
    }

    public void setBuyerAddress(String buyer_address){
        this.buyer_address=buyer_address;
    }

    public int getPickmoney(){
        return pickmoney;
    }

    public void setPickmoney(int pickmoney){
        this.pickmoney=pickmoney;
    }

    public int getPickpoint(){
        return pickpoint;
    }

    public void setPickpoint(int pickpoint){
        this.pickpoint=pickpoint;
    }

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
    public String getProductName(){
        return product_name;
    }

    public void setProductName(String product_name){
        this.product_name=product_name;
    }

    public String getProductDesc(){
        return product_desc;
    }

    public void setProductDesc(String product_desc){
        this.product_desc=product_desc;
    }

    public int getProductPrice(){
        return product_price;
    }

    public void setProductPrice(int product_price){
        this.product_price=product_price;
    }

    public int getProductStock(){
        return product_stock;
    }

    public void setProductStock(int product_stock){
        this.product_stock=product_stock;
    }

    public String getProductPhoto(){
        return product_photo;
    }

    public void setProductPhoto(String product_photo){
        this.product_photo=product_photo;
    }

    public String getProductStyle(){
        return product_style;
    }

    public void setProductStyle(String product_style){
        this.product_style = product_style;
    }

    public int getDiscount(){
        return discount;
    }

    public void setDiscount(int discount){
        this.discount = discount;
    }

   



}

