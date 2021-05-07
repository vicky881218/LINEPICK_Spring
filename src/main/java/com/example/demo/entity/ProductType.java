package com.example.demo.entity;

public class ProductType {
    private int product_id = 0;
    private int type_id = 0;
    private int product_type_id = 0;


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

}