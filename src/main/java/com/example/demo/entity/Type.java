package com.example.demo.entity;

public class Type {
    private int type_id = 0;
    private String type_name = "";
    private int seller_id = 0;

    public int getTypeId(){
        return type_id;
    }

    public void setTypeId(int type_id){
        this.type_id=type_id;
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
