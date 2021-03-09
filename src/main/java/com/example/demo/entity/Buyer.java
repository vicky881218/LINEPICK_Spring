package com.example.demo.entity;

public class Buyer {
    private String buyer_id;
    private String buyer_name;
    private String buyer_phone;
    private String buyer_mail;
    private String buyer_address;
    private int pickpoint;
    private int pickmoney;

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
}
