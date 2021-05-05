package com.example.demo.entity;

public class Seller {
    private int seller_id = 0;
    private String seller_account = "";
    private String seller_password = "";
    private String seller_mail = "";
    private String seller_phone = "";
    private String market_name = "";
    private String market_desc = "";

    public int getSellerId(){
        return seller_id;
    }

    public void setSellerId(int seller_id){
        this.seller_id=seller_id;
    }

    public String getSellerAccount(){
        return seller_account;
    }

    public void setSellerAccount(String seller_account){
        this.seller_account=seller_account;
    }

    public String getSellerPassword(){
        return seller_password;
    }

    public void setSellerPassword(String seller_password){
        this.seller_password=seller_password;
    }

    public String getSellerMail(){
        return seller_mail;
    }

    public void setSellerMail(String seller_mail){
        this.seller_mail=seller_mail;
    }

    public String getSellerPhone(){
        return seller_phone;
    }

    public void setSellerPhone(String seller_phone){
        this.seller_phone=seller_phone;
    }

    public String getMarketName(){
        return market_name;
    }

    public void setMarketName(String market_name){
        this.market_name=market_name;
    }

    public String getMarketDesc(){
        return market_desc;
    }

    public void setMarketDesc(String market_desc){
        this.market_desc=market_desc;
    }
}