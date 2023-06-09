package com.example.demo.entity;

public class ProductType {
    private int product_id = 0;
    private int type_id = 0;
    private int product_type_id = 0;
    private String product_name = "";
    private String product_desc = "";
    private int product_price = 0;
    private int product_stock = 0;
    private String product_photo = "";
    private String product_style = "";
    private String type_name = "";
    private int seller_id = 0;

    public int getProductId(){
        return product_id;
    }

    public void setProductId(int product_id){
        this.product_id=product_id;
    }

    public int getTypeId(){
        return type_id;
    }

    public void setTypeId(int type_id){
        this.type_id=type_id;
    }

    public int getProductTypeId(){
        return product_type_id;
    }

    public void setProductTypeId(int product_type_id){
        this.product_type_id=product_type_id;
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
    public String getTypeName(){
        return type_name;
    }

    public void setTypeName(String type_name){
        this.type_name=type_name;
    }

    public int getSellerId(){
        return seller_id;
    }

    public void setSellerId(int seller_id){
        this.seller_id=seller_id;
    }
}