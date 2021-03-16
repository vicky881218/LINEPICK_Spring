package com.example.demo.entity;

public class Product {
    private int product_id = 1;
    private String product_name = "";
    private String product_desc = "";
    private int product_price = 0;
    private int product_stock = 0;
    private String product_photo = "";
    private String product_style = "";
    private String product_size = "";

    public int getProductId(){
        return product_id;
    }

    public void setProductId(int product_id){
        this.product_id=product_id;
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
        this.product_style=product_style;
    }

    public String getProductSize(){
        return product_size;
    }

    public void setProductSize(String product_size){
        this.product_size=product_size;
    }
}